# SDK generation #

An SDK for the Yapily API can be generated through [openapi-generator-cli](https://github.com/OpenAPITools/openapi-generator-cli)

```bash
npx @openapitools/openapi-generator-cli generate -i https://api.yapily.com/docs/v3/openapi.json -g java -o ./yapily-sdk
```

## Generation Options ##

There are various options available for Generating the sdk.

```bash
npx @openapitools/openapi-generator-cli config-help -g java
```

## Generate script ##

```bash
./generate.sh
```

### Compile generated sdk ###

```bash
cd yapily-sdk
mvn clean install
```