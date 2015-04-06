# wm-shortest-paths

// Motivaçao

## Technologies

- Java 8 - lambda and collections new features
- Gson - Google Java library to manipulate JSON
- Google Guice - lightweight dependency injection framework
- Spark Java - lightweight web framework to expose REST APIs (uses an embeded Jetty Server)
- MongoDB - Schemaless and document based database
- Junit - Unit tests
- Mockito - Mock tests
- REST-Assured - DSL framework to REST APIs integration tests

## Requirements

### JDK 8

Make sure that your maven is pointing to a Java 8 version:

```bash
mvn -version
```

## Running

### Clone workspace from Git

```bash
git clone https://github.com/nessauepa/wm-shortest-paths.git
```

### Start server

```bash
mvn clean install exec:java -Dexec.mainClass="com.wallmart.Bootstrap"
```

## Unit tests

```bash
mvn clean test
```

## Integration tests

```bash
mvn clean verify -P integration-test
```

## API DOC

// TODO