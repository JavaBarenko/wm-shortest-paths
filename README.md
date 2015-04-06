# wm-shortest-paths

## Intro

This project was designed with the main goals simplicity and efficiency. For this purpose, the frameworks were choosen for not to abstract more than necessary and by your lightweight configurations (like Spark Java and Google Guice).

To improve the quality of design and reliability, the implementation was done following the TDD technique, supported by test frameworks (JUnit, Mockito, Rest-Assured). The Rest-Assured was used for the implementation of integration tests and Mockito in unit testing.

## Technologies

- Java 8 - lambda and collections new features
- Gson - Google Java library to manipulate JSON
- Google Guice - lightweight dependency injection framework
- Spark Java - lightweight web framework to expose REST APIs (uses an embeded Jetty Server)
- MongoDB - schemaless and document based database
- Junit - unit tests
- Mockito - mock tests
- REST-Assured - DSL framework to REST APIs integration tests

## Requirements

### JDK 8

Make sure that your maven is pointing to a Java 8 version:

```bash
mvn -version
```

### MongoDB

## Running

### Clone workspace from Git

```bash
git clone https://github.com/nessauepa/wm-shortest-paths.git
```

### Unit tests

```bash
mvn clean test
```

### Integration tests

```bash
mvn clean verify -P integration-test
```

### Start server

```bash
mvn clean install exec:java -Dexec.mainClass="com.wallmart.Bootstrap"
```

### Test

http://localhost:4567


## API DOC

### POST /maps
Insert or update a map

#### Request payload
```json
{
        "name": "SP",
        "routes": [
            {
                "origin": "Indaiatuba",
                "destination": "Limeira",
              "distance": 100
            },
            {
                "origin": "Limeira",
                "destination": "Americana",
                 "distance": 30
            }
        ]
}
```
name - map name [required]
routes - a list of route objects [required at least one]
route.origin - origin of this route (string) [required]
route.destination - destination of this route (string) [required]
route.distance - distance from origin to destination (float) [required]

#### Return payload (same object from request)
```json
{
        "name": "SP",
        "routes": [
            {
                "origin": "Indaiatuba",
                "destination": "Limeira",
              "distance": 100
            },
            {
                "origin": "Limeira",
                "destination": "Americana",
                 "distance": 30
            }
        ]
}
```


#### Return HTTP Codes

HTTP  Code | Description
------------- | -------------
202 - Accepted  | Map was saved or updated
400 - Bad Request  | Illegal request, missing or invalid field

