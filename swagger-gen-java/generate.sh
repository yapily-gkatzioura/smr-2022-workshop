#!/bin/bash

npx @openapitools/openapi-generator-cli config-help -g java

npx @openapitools/openapi-generator-cli generate -i https://api.yapily.com/docs/v3/openapi.json -g java -c ./config.json -o ./yapily-sdk

cd ./yapily-sdk

mvn clean install

cd ../