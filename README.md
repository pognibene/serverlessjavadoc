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
cd serverlessjavadoc
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
2) scan the global claspath for all your projects. Currently this is done by finding all maven pom.xml files and finding the associated classpath.
This phase can be a bit slow, depending on the complexity of your setup.
Please note that gradle based builds are not yet supported (but this will most likely be added soon).
3) load and parse the source files to extract lambda handlers javadoc.
4) load and parse the compiled class files to extract annotations.
5) merge information from multiple projects, and finally...
6) generate the output open api files (one schema.json and one schema.yaml)

This file can then imported in a variety of tools like swagger UI for visualisation.

## Examples
You can find a working example in the folder examples.


## Supported functionnalities
Currently, the tool assumes that all your serverless java lambdas are built using maven.
Gradle support may come in the future, and potentially support for arbitrary build systems
if there's demand for it.
The tool honors the following specific Jackson annotations:
```
@JsonClassDescription("A class annotation")
```
To document your model classes, that is, classes used in the input and output messages of your REST/Json APIs.
```
@JsonPropertyDescription("A property annotation")
```
To document your model classes attributes.

The tool also collects Javadoc on your Java handler classes only, and correlate their content
with information extracted from the serverless.yml files.

## How to document and annotate your handlers and models

## Known limitations

* The java bean validation annotations are not yet handled (JSR 380), to get better schemas. Support will probably be added in the future.
* Gradle is not yet supported as a build system. The consequence is we can't build properly the java classpath
for Gradle based project. Support may be added in the future.
* The documentation for Handler/Lambda functions is currently using Javadoc. While this is the standard way
 to do in Java, it means we have to combine annotations and Javadoc to get a complete documentation.
 This may change in the future, with annotations used for handlers as well. (besides, making a maven plugin
 for the tool kind of requires this)
* The classpath building is a bit slow. This is actually a limitation of maven.
* The generated open api document has no servers definitions. This is a limitation of the serverless.yaml files,
as there's no standard way to declare the URL where an API is to be deployed. We may add some support
for the serverless-domain-manager though to make this better. Or use a specific
documentation tag in the serverless file for ease of use.
* no maven plugin yet. There are some specific limitations with maven, the first one
being that we can't easily analyse the source code outside of the current module. So we'll have to revert to
compiled class and annotation analysis for everything, including Handler documentation. Also, only one API can be documented at a time,
meaning that we can't produce a single open api document. Or we would have to merge
several documents in a post-processing phase.
* no gradle plugin yet. More or less for the same reasons than maven.