#!/bin/sh

# an example on how to run the tool on a small sample project to demonstrate
# the use of annotations and javadocs
# also includes the generated schema.json in a ReactJs app with swagger ui
# and runs it on localhost.

# you will need nodejs, npm and the yarn tool to be installed to generate
# the reactjs documentation page.

mvn clean install
cd gradle-api
gradle clean build
cd ../..

ROOT=$(pwd)
cd tool
mvn clean install
java -jar target/serverless-api-javadoc-1.0.jar ${ROOT}/examples ${ROOT}/example-swagger-ui/public
cd ../example-swagger-ui
rm -rf build
PUBLIC_URL=http://localhost:3000
yarn
##PUBLIC_URL=${PUBLIC_URL} yarn build
yarn start

