
# Running WireMock Standalone

Wiremock standalone server is used for external API calls in this project.

#### To start the Wiremock server, go to wiremock directory and simply run the following command from the terminal


Go to wiremock directory in the project

```bash
  cd src/main/java/com/softavail/wiremock/
```

Run the command below in terminal

```bash
  java -jar wiremock-jre8-standalone-2.34.0.jar --port=8889 --verbose
```

With this way wiremock will be ready to receive request from port 8889

  
