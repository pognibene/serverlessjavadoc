# serverlessjavadoc
With this tool, you can generate easily open api 3 documentation from a serverless, java/REST/Json based API project, or a set of projects.
From the Open API 3 document, you can then generate documentation or use a tool like Swagger UI to share your APIs definitions.

## Why this tool?
Traditionally, people writing REST/Json based APIs with Java would use a framework like Spring Boot and a *code first* approach.
Then, adding annotations to classes, they can generate swagger or open api documentation.
This however does not work well with a serverless project:

* the paths to the endpoints are defined in a serverless.yaml file
* the lambda implementations are in Java files. They also use maps as parameters, which means that typical annotations
can't be inserted to document input/output parameters of functions. 
* There is no standard annotation that would offer
all the information required to document a lambda handler class.

In effect, we need a tool that can collect information both from the serverless yaml files, the java classes (source and compiled),
and finally generate the api definition.

## How to compile

```
git clone https://github.com/pognibene/serverlessjavadoc.git
cd serverlessjavadoc/tool
mvn clean package
```


## How to use
First, you need to compile all the Java classes for all your APIs, as they are going to be analysed by the tool together
with the sources. How to achieve this depends on your build tool, but we assume for now that you use maven. When this is done,
you need to launch the tool:
```
java -jar serverless-api-javadoc-1.0.jar input_path output_path
```
where **input_path** is an absolute path to the input folder where you want the tool to analyze the serverless APIs,
and **output_path** is the absolute path to the output folder where you want to store the resulting open api files.
For example:
```
java -jar serverless-api-javadoc-1.0.jar /home/one_user/dev/backend /tmp
```
The tool will then go through several phases:

1) recursively scan *all* projects under the input folder. This means that the tool can generate a single open api document for multiple projects.
With the serverless framework, you typically have one project and one serverless.yaml file per 'API'. Each API in turn contains multiple endpoints.
2) scan the global classpath for all your projects. Currently this is done by finding all maven pom.xml files and finding the associated classpath.
This phase can be a bit slow, depending on the complexity of your setup.
Please note that gradle based builds are not yet supported (but this will most likely be added soon).
3) load and parse the source files to extract lambda handlers javadoc.
4) load and parse the compiled class files to extract annotations.
5) merge information from multiple projects, and finally...
6) generate the output open api files (one schema.json and one schema.yaml)

This file can then imported in a variety of tools like swagger UI for visualisation.

## Examples
You can find a working example in the folder examples. (TODO)


## Supported functionality
Currently, the tool assumes that all your serverless java lambdas are built either using maven or gradle.

## How to document and annotate your handlers and models
### Documenting your models
Models or Views are Java classes that are going to be exchanged with consumers
of your APIs : they are the input and output of your lambdas.
You can document these classes at 2 levels:
* the class level
* the field level

#### Class level
At the class level, you can insert an annotation with documentation:
```
@JsonClassDescription("A user in the system.")
public class User {
}
```
#### Field level
At the field level within a class, you can also insert an annotation:
```
@JsonPropertyDescription("The login for the User. This is the email address.")
private String login;
```
### Documenting your handlers
Your handler classes implement the Java logic.
Their signature is:
```
public class YourHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        return new ApiGatewayResponse();
    }
}
```
Because there no existing jax-rs existing anotations for this kind of lambda functions,
you have to document the handler class with Javadoc style comment:
```
/**
 * Create a new User in the system.
 *
 * @ServerlessEndpoint
 * @ServerlessQueryParam param1 one query parameter
 * @ServerlessQueryParam param2 Another query parameter
 * @ServerlessPathParam param3 one path parameter
 * @ServerlessPathParam param4 Another path parameter
 * @ServerlessInput com.agileandmore.model.User The User to create.
 * @ServerlessOutput 201 com.agileandmore.model.User The created User, with
 * updated information.
 * {{
 * x-foo one return header
 * y-foo another returned header
 * }}
 * @ServerlessOutput 409 void An error if the User already exists.
 * @ServerlessInputHeader authorization A JWT token to protect access to the API.
 */
public class CreateUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        return null;
    }
}
```
#### Handler documentation
The first line of text after the beginning of the Javadoc (/**) until the first tag (starting with @)
will become the documentation for your handler. Empty lines are ignored.
#### @ServerlessEndpoint tag
This tag is mandatory for the class to be recognized as an API handler class and must be on a single line.
#### @ServerlessQueryParam tag
if your endpoint takes query parameters, you can document them using this tag.
First the tag, then one space, then the parameter name (with no spaces in it), then the text to document the parameter.
To document multiple query parameters, just use the tag multiple times.
#### @ServerlessPathParam tag
if your endpoint takes path parameters, you can document them using this tag.
First the tag, then one space, then the parameter name (with no spaces in it), then the text to document the parameter.
To document multiple path parameters, just use the tag multiple times. Note : the tag name must match
the parameter name in the serverless.yml file.
#### @ServerlessInput tag
The tag, then the fully scoped name of the input class, then the documentation text.
Note : the input class is *not* Map<String, Object>. Rather, it is the type you are going to extract
from the body part of the incoming message, as wrapped by API Gateway before calling Lambda. Basically,
the type actually used by the consumer.
If the input type is an array of objects, use the fully scoped type of the class followed by [].
Exemple : **com.agileandmore.User[]**
#### @ServerlessOutput tag
The tag, then the http code, then the fully scoped name of the output class, then the documentation text.
Note : the output class is *not* ApiGatewayResponse. Rather, it is the type you are going to wrap as
the body of the ApiGatewayResponse, fo example User. You can have multiple output tags, each with their
own http code, output class, and documentation.
If if have a response with an http code but no output, you can use the special type **void** for
the output message.
If the output type is an array of objects, use the fully scoped type of the class followed by [].
Exemple : **com.agileandmore.User[]**

The Serverless output tag can also contain nested headers for each output. return headers must
be enclosed between curly brackets, right after the ServerlessOutput tag itself:
```
* {{
* x-foo one return header
* y-foo another returned header
* }}
```
each return header must be on a single line. Each line is made of:
* the header name
* then the header documentation

Please note that all headers will have a simple String schema (for now)

#### @ServerlessInputHeader tag
The tag, then the header name, then the description.
Multiple input headers are managed by adding multiple tags for the different headers.

### Documenting the deployment URLs
By default, when one deploys APIs with AWS API Gateway and AWS Lambda, the API is
deployed on a random, generated URL like:
```
https://qhmwkki2ng.execute-api.ap-myregion-1.amazonaws.com/dev/api/v1/users/login
```

However, it is usually desirable to have APIs deployed on fixed URLs, 
depending on the target environment.

With Serverless, there is an existing plugin that can enable deployment on a custom URL :
the serverless-domain-manager plugin. We can use the configuration for this
plugin to generate URL information in the open api 3 document we generate.
```
plugins:
  - serverless-domain-manager
```
In this section of the serverless.yaml file, we must find the serverless-domain-manager plugin.
```
custom:
  domains:
    prod: myprodurl.com
    dev: mydevurl.com
    preprod: mypreprodurl.com
  customDomain:
    domainName: ${self:custom.domains.${self:provider.stage}}
    basePath: "mybasepath"
    stage: ${self:provider.stage}
    createRoute53Record: true
    endpointType: "regional"
```
In the custom section of the serverless file, we must have a domains sub section, then all the 
domains this api can be deployed on. For example, the 'prod' deployment will use
the 'myprodurl.com' domain.

We must also have a 'customDomain' sub section, which is used by the serverless-domain-manager
plugin. From this sub section, we just extract the optional 'basePath' value.
If basePath is set, the deployment URLs will be:
```
https:// + the domain + basePath (if it exists)
```
In the above case, we would generate 3 URLS:
```
https://myprodurl.com/mybasepath
https://mydevurl.com/mybasepath
https://mypreprodurl.com/mybasepath
```
Respectively for the prod, dev, preprod environments.

These URLs are stored as per Open Api 3.0 specs, in a servers section of a PathItem.
Here's an example:
```
"servers" : [ {
        "url" : "https://prod.company.com/payments",
        "description" : "The server for the prod environment.",
        "variables" : { }
      }, {
        "url" : "https://dev.company.com/payments",
        "description" : "The server for the dev environment.",
        "variables" : { }
      }, {
        "url" : "https://preprod.company.com/payments",
        "description" : "The server for the preprod environment.",
        "variables" : { }
      } ]
```
## Known limitations

* The java bean validation annotations are not yet handled (JSR 380), to get better schemas. Support will probably be added in the future.
* the security parts are not managed yet. This should come soon.
* The documentation for Handler/Lambda functions is currently using Javadoc. While this is the standard way
 to do in Java, it means we have to combine annotations and Javadoc to get a complete documentation.
 This may change in the future, with annotations used for handlers as well. (besides, making a maven plugin
 for the tool kind of requires this)
* no maven plugin yet. There are some specific limitations with maven, the first one
being that we can't easily analyse the source code outside of the current module. So we'll have to revert to
compiled class and annotation analysis for everything, including Handler documentation. Also, only one API can be documented at a time,
meaning that we can't produce a single open api document. Or we would have to merge
several documents in a post-processing phase.
* no gradle plugin yet. More or less for the same reasons than maven.
* path and query parameters only have the simple type string, complex schemas are not supported yet.
* For now, all headers are considered required. Need to add some semantic to make them optional if desired.
