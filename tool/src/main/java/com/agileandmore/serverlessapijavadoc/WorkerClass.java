/*
    Copyright 2020 Pascal Ognibene (pognibene@gmail.com)

    This file is part of The serverless api javadoc api tool (Aka SAJ).

    SAJ is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SAJ is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SAJ.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.agileandmore.serverlessapijavadoc;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.agileandmore.serverlessapijavadoc.openapi.MediaTypeObject;
import com.agileandmore.serverlessapijavadoc.openapi.OpenApi;
import com.agileandmore.serverlessapijavadoc.openapi.OperationObject;
import com.agileandmore.serverlessapijavadoc.openapi.ParameterObject;
import com.agileandmore.serverlessapijavadoc.openapi.PathItem;
import com.agileandmore.serverlessapijavadoc.openapi.RequestBody;
import com.agileandmore.serverlessapijavadoc.openapi.ResponseObject;
import com.agileandmore.serverlessapijavadoc.openapi.StringSchema;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.yaml.snakeyaml.Yaml;

public class WorkerClass {

    private List<Handler> handlers = new ArrayList<>();
    private List<ViewModel> models = new ArrayList<>();
    private List<Api> apis = new ArrayList<>();

    public void start(String inputFolder, String outputFolder) {
        URLClassLoader cl2 = null;
        JacksonModule jacksonModule = null;
        try {
            List<Path> list = Files.walk(Paths.get(inputFolder))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            Set<String> classPaths = new HashSet<>();

            List<Path> javaFileList = new ArrayList<>();
            List<Path> classFileList = new ArrayList<>();
            List<Path> apiList = new ArrayList<>();
            List<String> classNames = new ArrayList<>();
            List<Path> pomList = new ArrayList<>();

            for (Path path : list) {
                String spath = path.toAbsolutePath().toString().toLowerCase();
                if (spath.endsWith(".java")) {
                    javaFileList.add(path);
                } else if (spath.endsWith(".yml") || spath.endsWith(".yaml")) {
                    apiList.add(path);
                } else if (spath.endsWith(".class")) {
                    classFileList.add(path);
                    String absolutePath = path.toAbsolutePath().toString();
                    byte[] bytes = Files.readAllBytes(Paths.get(absolutePath));

                    ClassReader cr = new ClassReader(bytes);
                    ClassNode classNode = new ClassNode();
                    cr.accept(classNode, 0);
                    String basePath = absolutePath.substring(0, absolutePath.length() - classNode.name.length() - ".class".length());
                    classPaths.add(basePath);

                    classNames.add(classNode.name.replace("/", "."));
                } else if (spath.endsWith("pom.xml")) {
                    pomList.add(path);
                }
            }

            List<URL> allUrls = new ArrayList<>();
            for (String oneClasspath : classPaths) {
                URL oneUrl = new URL("file:" + oneClasspath);
                allUrls.add(oneUrl);
            }

            // TODO should also inspect gradle file for the class path
            // I doubt it's doable for ant though
            //we may have an option to provide the classpath manually for exotic tools
            // using the list of pom files, invokve maven and add more dependencies to
            // the classpath. We need them to load classes and generate json schemas.
            Set<String> jarset = new HashSet<>();
            String tempDir = System.getProperty("java.io.tmpdir");
            String tmpFile = tempDir + File.separator + "cp.txt";
            for (Path onePath : pomList) {
                String spath = onePath.toAbsolutePath().toString();
                // TODO make maven command configurable
                ProcessBuilder builder = new ProcessBuilder("mvn", "-f", spath,
                        "dependency:build-classpath", "-Dmdep.outputFile="
                        + tmpFile);
                Process process = builder.start();

                StringBuilder out = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                        out.append("\n");
                    }
                    System.out.println(out);
                }

                try {
                    int result = process.waitFor();
                    if (result == 0) {
                        String jarPath = readAllBytesJava7(tmpFile);
                        String[] jarPathTab = jarPath.split(":");
                        for (String s : jarPathTab) {
                            s = s.replace("\\R", "").trim();
                            if (!isEmpty(s)) {
                                jarset.add(s);
                            }
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(WorkerClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            for (String s : jarset) {
                allUrls.add(new URL("file:" + s));
            }

            URL[] taburl = allUrls.stream().toArray(URL[]::new);

            // to recognize Jackson specific annotations
            cl2 = new URLClassLoader(taburl, WorkerClass.class.getClassLoader());
            jacksonModule = new JacksonModule();

            for (Path path : javaFileList) {
                CompilationUnit cu = StaticJavaParser.parse(path);
                cu.accept(new ClassVisitor(), null);
            }

            Yaml yaml = new Yaml();
            for (Path apiPath : apiList) {

                try {
                    InputStream ios = new FileInputStream(new File(apiPath.toAbsolutePath().toString()));
                    Map<String, Object> result = (Map<String, Object>) yaml.load(ios);

                    // check this is actually a serverless yaml file
                    String serviceName = (String) result.get("service");
                    if (isEmpty(serviceName)) {
                        continue;
                    }

                    // don't add the api to the list yet, as we are not sure it's really
                    // a serverless file. If we don't find any http endpoint in it, we will
                    // not add it to the list.
                    Api api = new Api();
                    api.setName(serviceName);

                    //TODO API global doc if it exists
                    Map<String, Object> functions = (Map<String, Object>) result.get("functions");
                    if (functions == null || functions.size() == 0) {
                        continue;
                    }

                    for (String s : functions.keySet()) {
                        Map<String, Object> attributes = (Map<String, Object>) functions.get(s);
                        if (attributes == null || attributes.size() == 0) {
                            continue;
                        }
                        String handler = (String) attributes.get("handler");
                        String method = null;
                        String path = null;
                        Boolean cors = null;
                        String handlerName = s;

                        List<Map<String, Object>> empty = new ArrayList<>();
                        List<Map<String, Object>> events = (List<Map<String, Object>>) attributes.getOrDefault("events", empty);

                        if (events.size() > 0) {
                            for (Map<String, Object> o : events) {
                                Map<String, Object> http = (Map<String, Object>) o.get("http");
                                if (http != null) {
                                    path = (String) http.get("path");
                                    if (path.endsWith("/")) {
                                        path = path.substring(0, path.length() - 1);
                                    }
                                    if (!path.startsWith("/")) {
                                        path = "/" + path;
                                    }
                                    method = (String) http.get("method");
                                    cors = (Boolean) http.get("cors");
                                }
                            }
                        }
                        if (method != null && path != null) {
                            for (Handler oneHandler : handlers) {
                                if (oneHandler.getQualifiedName().compareTo(handler) == 0) {
                                    api.getHandlers().add(oneHandler);
                                    oneHandler.setUriPath(path);
                                    oneHandler.setMethod(method);
                                    oneHandler.setCors(cors);
                                    oneHandler.setName(handlerName);
                                    break;
                                }
                            }
                        }
                    }
                    if (api.getHandlers().size() > 0) {
                        apis.add(api);
                    }
                } catch (FileNotFoundException ex) {
                    // the file should always exist...
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
        }

        OpenApi openApi = new OpenApi();
        Map<String, JsonNode> schemasMap = new HashMap<>();

        for (Api oneApi : apis) {
            List<Handler> handlers = oneApi.getHandlers();

            // for each API, I need to create a map path->handler
            // to group endpoints together
            Map<String, List<Handler>> handlerMap = new HashMap<>();
            for (Handler oneHandler : handlers) {
                if (!handlerMap.containsKey(oneHandler.getUriPath())) {
                    List<Handler> newList = new ArrayList<>();
                    newList.add(oneHandler);
                    handlerMap.put(oneHandler.getUriPath(), newList);
                } else {
                    handlerMap.get(oneHandler.getUriPath()).add(oneHandler);
                }
            }

            // then I can add the handlers grouped by URI
            Set<String> keys = handlerMap.keySet();
            for (String oneKey : keys) {
                PathItem pathItem = new PathItem();
                openApi.getPaths().put(oneKey, pathItem);

                // populate the path item with all relevant operations
                List<Handler> handlerList = handlerMap.get(oneKey);
                for (Handler oneHandler : handlerList) {

                    OperationObject operationObject = new OperationObject();
                    operationObject.setDescription(oneHandler.getDocumentation());
                    operationObject.setSummary(oneHandler.getName());
                    operationObject.getTags().add(oneApi.getName());

                    populateParams(oneHandler.getQueryParams(), "query", operationObject);
                    populateParams(oneHandler.getPathParams(), "path", operationObject);
                    populateParams(oneHandler.getHeaderInParams(), "header", operationObject);
                    //FIXME problem : output headers may be attached to specific responses
                    //rather than the handler?
                    //which means they should be nested in responses, but we have only one tag?
                    //how to express this?

                    // request body
                    if (oneHandler.getInputMessages().size() > 0) {
                        InputMessage inputMessage = oneHandler.getInputMessages().get(0);

                        RequestBody requestBody = new RequestBody();
                        requestBody.setDescription(inputMessage.getDocumentation());
                        requestBody.setRequired(Boolean.TRUE);
                        MediaTypeObject mediaTypeObject = new MediaTypeObject();

                        populateMessageSchema(inputMessage.getIsArray(), inputMessage.getQualifiedClassName(), mediaTypeObject);
                        requestBody.getContent().put("application/json", mediaTypeObject);

                        operationObject.setRequestBody(requestBody);

                        createSchema(inputMessage.getQualifiedClassName(), schemasMap, inputMessage.getIsArray(), cl2, jacksonModule);
                    }

                    // responses
                    for (OutputMessage outputMessage : oneHandler.getOutputMessages()) {
                        ResponseObject responseObject = new ResponseObject();
                        operationObject.getResponses().put(outputMessage.getHttpCode(), responseObject);

                        //populate response
                        responseObject.setDescription(outputMessage.getDocumentation());

                        // if the return type is void, no schema generation
                        if (!outputMessage.getQualifiedClassName().toLowerCase().equals("void")) {

                            MediaTypeObject mediaTypeObject = new MediaTypeObject();
                            populateMessageSchema(outputMessage.getIsArray(), outputMessage.getQualifiedClassName(), mediaTypeObject);
                            responseObject.getContent().put("application/json", mediaTypeObject);

                            createSchema(outputMessage.getQualifiedClassName(), schemasMap, outputMessage.getIsArray(), cl2, jacksonModule);
                        } else {
                            responseObject.setContent(null);
                        }
                    }

                    switch (oneHandler.getMethod()) {
                        case "get":
                            pathItem.setGet(operationObject);
                            break;
                        case "put":
                            pathItem.setPut(operationObject);
                            break;
                        case "post":
                            pathItem.setPost(operationObject);
                            break;
                        case "delete":
                            pathItem.setDelete(operationObject);
                            break;
                        case "options":
                            pathItem.setOptions(operationObject);
                            break;
                        case "head":
                            pathItem.setHead(operationObject);
                            break;
                        case "patch":
                            pathItem.setPatch(operationObject);
                            break;
                        case "trace":
                            pathItem.setTrace(operationObject);
                            break;
                        default:
                            break;
                    }

                }
            }
        }

        // now insert all input and output schemas in the api
        Set<String> schemaSet = schemasMap.keySet();
        for (String s : schemaSet) {
            JsonNode jn = schemasMap.get(s);
            openApi.getComponents().getSchemas().put(s, jn);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFolder + File.separator + "schema.json"), openApi);
        } catch (IOException ex) {
            Logger.getLogger(WorkerClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        mapper = new ObjectMapper(new YAMLFactory());
        mapper.setSerializationInclusion(Include.NON_NULL);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFolder + File.separator + "schema.yml"), openApi);
        } catch (IOException ex) {
            Logger.getLogger(WorkerClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createSchema(String qualifiedClassName, Map schemasMap, boolean isArray, URLClassLoader cl2, JacksonModule jacksonModule) {
        try {
            Class loadedClass = cl2.loadClass(qualifiedClassName);
            Object obj = loadedClass.newInstance();

            ObjectMapper objectMapper = new ObjectMapper();
            SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(objectMapper,
                    OptionPreset.PLAIN_JSON)
                    .without(Option.SCHEMA_VERSION_INDICATOR)
                    .with(jacksonModule);
            SchemaGeneratorConfig config = configBuilder.build();
            SchemaGenerator generator = new SchemaGenerator(config);

            JsonNode jsonSchema = generator.generateSchema(obj.getClass());

            if (!schemasMap.containsKey(qualifiedClassName)) {
                modifySchemaPrefix(jsonSchema, qualifiedClassName);
                schemasMap.put(qualifiedClassName, jsonSchema);
            }

            // create also the schema for the array type, if required
            if (isArray) {
                if (!schemasMap.containsKey(qualifiedClassName + "_Array")) {
                    String jsonString = "{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/"
                            + qualifiedClassName + "\"}}";
                    JsonNode actualObj = objectMapper.readTree(jsonString);
                    schemasMap.put(qualifiedClassName + "_Array", actualObj);
                }
            }
        } catch (Exception ex) {
            // can and will happen if constructor is private
        }
    }

    private String extractSimpleCommentFromJavadoc(String original) {
        String newString = original.substring(3, original.length() - 3);
        String outputString = "";

        String array[] = newString.split("\\R");
        for (String s : array) {

            // check if the string contains a marker tag, because in this case
            // we just ignore it
            if (!(s.contains("@ServerlessModel") || s.contains("@ServerlessEndpoint"))) {

                s = s.trim();

                if (s.startsWith("*")) {
                    s = s.substring(1).trim();
                }
                if (s.length() > 0) {
                    outputString += s + "\n";//unix style, will generate html anyway
                    //TODO check what EOL style I should use for HTML fragments.
                }
            }
        }

        if (outputString.endsWith("\n")) {
            outputString = outputString.substring(0, outputString.length() - 1);
        }
        return outputString;
    }

    private void modifySchemaPrefix(JsonNode jsonNode, String qualifiedClassName) {
        ObjectMapper mapper = new ObjectMapper();

        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> iter = jsonNode.fields();

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();

                if (entry.getKey().compareTo("$ref") == 0) {
                    String newRef = "#/components/schemas/" + qualifiedClassName + entry.getValue().asText().substring(1);
                    ((ObjectNode) jsonNode).remove("$ref");
                    ((ObjectNode) jsonNode).put("$ref", newRef);//FIXME careful : modifying the object in the iterator
                    //may have side effects (missing values)
                } else {
                    modifySchemaPrefix(entry.getValue(), qualifiedClassName);
                }
            }
        } else if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            for (int i = 0; i < arrayNode.size(); i++) {
                modifySchemaPrefix(arrayNode.get(i), qualifiedClassName);
            }
        }
    }

    private void populateParams(List<PathOrQueryParam> params, String paramType, OperationObject operationObject) {
        for (PathOrQueryParam oneParam : params) {
            ParameterObject parameterObject = new ParameterObject();
            operationObject.getParameters().add(parameterObject);
            parameterObject.setSchema(new StringSchema());
            parameterObject.setIn(paramType);
            parameterObject.setName(oneParam.getName());
            parameterObject.setDescription(oneParam.getDocumentation());
            parameterObject.setRequired(oneParam.getRequired());
        }
    }

    private void populateMessageSchema(Boolean isArray, String qualifiedClassName, MediaTypeObject mediaTypeObject) {
        if (isArray) {
            mediaTypeObject.getSchema().setRef("#/components/schemas/"
                    + qualifiedClassName + "_Array");
        } else {
            mediaTypeObject.getSchema().setRef("#/components/schemas/"
                    + qualifiedClassName);
        }
    }

    private class ClassVisitor extends VoidVisitorAdapter<Void> {

        public void visit(ClassOrInterfaceDeclaration n, Void arg) {

            Optional<JavadocComment> optional = n.getJavadocComment();
            if (optional.isPresent()) {
                String rawComment = optional.get().toString();

                // check if this class javadoc contains either a @ServerlessEndpoint tag
                // or a @ServerlessModel tag
                if (rawComment.contains("@ServerlessEndpoint")) {
                    Handler handler = new Handler();
                    handlers.add(handler);
                    handler.setQualifiedName(n.getFullyQualifiedName().get());
                    extractHandlerInformation(handler, rawComment);

                } else if (rawComment.contains("@ServerlessModel")) {

                    ViewModel viewModel = new ViewModel();
                    models.add(viewModel);
                    viewModel.setQualifiedName(n.getFullyQualifiedName().get());
                    viewModel.setDocumentation(extractSimpleCommentFromJavadoc(rawComment));

                    List<FieldDeclaration> fieldDeclarations = n.findAll(FieldDeclaration.class).stream()
                            .collect(Collectors.toList());

                    for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
                        // works only if only one field under the javadoc
                        // if multiple fields we are screwed - but this must be discouraged anyway
                        ViewAttribute viewAttribute = new ViewAttribute();
                        viewModel.getAttributes().add(viewAttribute);
                        viewAttribute.setName(fieldDeclaration.getVariable(0).getNameAsString());
                        viewAttribute.setType(fieldDeclaration.getVariable(0).getTypeAsString());

                        Optional<JavadocComment> optional1 = fieldDeclaration.getJavadocComment();
                        if (optional1.isPresent()) {
                            viewAttribute.setDocumentation(extractSimpleCommentFromJavadoc(optional1.get().asJavadocComment().toString()));
                        }
                    }
                }
            }
        }

    }

    /**
     * Extract information from a raw handler comment. As per javadoc, the first
     * part is the documentation, until the first tag @ is met or until the end
     * of the string is met. Please note that special tags like {@link URL} are
     * not recognized, because they make no sense in the context of an API
     * documentation. So this is pure text that should be written here.
     *
     * @param handler
     * @param rawComment
     */
    private void extractHandlerInformation(Handler handler, String rawComment) {

        String filteredComment = extractSimpleCommentFromJavadoc(rawComment);

        String documentation = filteredComment;
        for (int i = 0; i < filteredComment.length(); i++) {
            if (filteredComment.charAt(i) == '@') {
                documentation = filteredComment.substring(0, i - 1);
                handler.setDocumentation(documentation);

                String tags = filteredComment.substring(i, filteredComment.length());
                String[] tagArray = tags.split("@");
                for (String oneTag : tagArray) {

                    // This is the format of a query parameter tag:
                    // @ServerlessQueryParam class the occupation class. It's a number from 1 to 4.
                    // This parameter is mandatory.
                    if (oneTag.startsWith("ServerlessQueryParam")) {

                        oneTag = oneTag.substring("ServerlessQueryParam".length()).trim();
                        for (int j = 0; j < oneTag.length(); j++) {
                            if (oneTag.charAt(j) == ' ') {
                                String tagName = oneTag.substring(0, j);
                                String tagDocumentation = oneTag.substring(j).trim();
                                PathOrQueryParam pathOrQueryParam = new PathOrQueryParam();
                                pathOrQueryParam.setName(tagName);
                                pathOrQueryParam.setDocumentation(tagDocumentation);
                                handler.getQueryParams().add(pathOrQueryParam);
                                break;
                            }
                        }
                    } else if (oneTag.startsWith("ServerlessPathParam")) {
                        //TODO refactor, duplicated code (almost)
                        oneTag = oneTag.substring("ServerlessPathParam".length()).trim();
                        for (int j = 0; j < oneTag.length(); j++) {
                            if (oneTag.charAt(j) == ' ') {
                                String tagName = oneTag.substring(0, j);
                                String tagDocumentation = oneTag.substring(j).trim();
                                PathOrQueryParam pathOrQueryParam = new PathOrQueryParam();
                                pathOrQueryParam.setName(tagName);
                                pathOrQueryParam.setDocumentation(tagDocumentation);
                                handler.getPathParams().add(pathOrQueryParam);
                                break;
                            }
                        }
                    } else if (oneTag.startsWith("ServerlessInputHeader")) {
                        //TODO refactor, duplicated code (almost)
                        oneTag = oneTag.substring("ServerlessInputHeader".length()).trim();
                        for (int j = 0; j < oneTag.length(); j++) {
                            if (oneTag.charAt(j) == ' ') {
                                String tagName = oneTag.substring(0, j);
                                String tagDocumentation = oneTag.substring(j).trim();
                                PathOrQueryParam pathOrQueryParam = new PathOrQueryParam();
                                pathOrQueryParam.setName(tagName);
                                pathOrQueryParam.setDocumentation(tagDocumentation);
                                handler.getHeaderInParams().add(pathOrQueryParam);
                                break;
                            }
                        }
                    } else if (oneTag.startsWith("ServerlessOutputHeader")) {
                        //TODO refactor, duplicated code (almost)
                        oneTag = oneTag.substring("ServerlessOutputHeader".length()).trim();
                        for (int j = 0; j < oneTag.length(); j++) {
                            if (oneTag.charAt(j) == ' ') {
                                String tagName = oneTag.substring(0, j);
                                String tagDocumentation = oneTag.substring(j).trim();
                                PathOrQueryParam pathOrQueryParam = new PathOrQueryParam();
                                pathOrQueryParam.setName(tagName);
                                pathOrQueryParam.setDocumentation(tagDocumentation);
                                handler.getHeaderOutParams().add(pathOrQueryParam);
                                break;
                            }
                        }
                    } else if (oneTag.startsWith("ServerlessOutput")) {
                        oneTag = oneTag.substring("ServerlessOutput".length()).trim();

                        // find http code
                        int j = 0;
                        String tagCode = "";
                        while (j < oneTag.length()) {
                            if (oneTag.charAt(j) == ' ') {
                                tagCode = oneTag.substring(0, j);

                                // next tag
                                while (j < oneTag.length() && oneTag.charAt(j) == ' ') {
                                    j++;
                                }
                                break;
                            }
                            j++;
                        }

                        // find message Class
                        int j1 = j;
                        String tagClass = "";
                        Boolean isArray = false;
                        while (j < oneTag.length()) {
                            if (oneTag.charAt(j) == ' ') {
                                //TODO faire fonction qui prend index depart et retourne index final ds la string,
                                //sans la modifier (doit retourner les 2 index pour extraire la string)
                                tagClass = oneTag.substring(j1, j);
                                if (tagClass.endsWith("[]")) {
                                    isArray = true;
                                    tagClass = tagClass.substring(0, tagClass.length() - 2);
                                }
                                // next tag
                                while (j < oneTag.length() && oneTag.charAt(j) == ' ') {
                                    j++;
                                }
                                break;
                            }
                            j++;
                        }

                        // find documentation
                        String tagDocumentation = oneTag.substring(j).trim();
                        OutputMessage outputMessage = new OutputMessage();
                        outputMessage.setDocumentation(tagDocumentation);
                        outputMessage.setHttpCode(tagCode);
                        outputMessage.setIsArray(isArray);
                        outputMessage.setQualifiedClassName(tagClass);
                        handler.getOutputMessages().add(outputMessage);

                    } else if (oneTag.startsWith("ServerlessInput")) {
                        oneTag = oneTag.substring("ServerlessInput".length()).trim();

                        // find message Class
                        int j1 = 0;
                        int j = 0;
                        String tagClass = "";
                        Boolean isArray = false;
                        while (j < oneTag.length()) {
                            if (oneTag.charAt(j) == ' ') {
                                //TODO faire fonction qui prend index depart et retourne index final ds la string,
                                //sans la modifier (doit retourner les 2 index pour extraire la string)
                                tagClass = oneTag.substring(j1, j);
                                if (tagClass.endsWith("[]")) {
                                    isArray = true;
                                    tagClass = tagClass.substring(0, tagClass.length() - 2);
                                }
                                // next tag
                                while (j < oneTag.length() && oneTag.charAt(j) == ' ') {
                                    j++;
                                }
                                break;
                            }
                            j++;
                        }

                        // find documentation
                        String tagDocumentation = oneTag.substring(j).trim();
                        InputMessage inputMessage = new InputMessage();
                        inputMessage.setDocumentation(tagDocumentation);
                        inputMessage.setIsArray(isArray);
                        inputMessage.setQualifiedClassName(tagClass);
                        handler.getInputMessages().add(inputMessage);
                    }

                }

                break;
            }
        }
    }

    private String readAllBytesJava7(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
