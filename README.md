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


## REST API

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

Request payload fields | Description
------------- | -------------
name | map name [required]
routes | a list of route objects [required at least one]
route.origin | origin of this route (string) [required]
route.destination | destination of this route (string) [required]
route.distance | distance from origin to destination (float) [required]

#### Example
```bash
curl -X POST 'http://localhost:4567/maps' -d '{"name": "SP","routes": [{"origin": "Indaiatuba","destination": "Limeira","distance": 100}]}'
```

#### Return payload (same object from request)


#### Return HTTP Codes

HTTP  Code | Description
------------- | -------------
202 - Accepted  | Map was saved or updated
400 - Bad Request  | Illegal request: missing or invalid field

### GET /maps/{mapName}/shortest-path-costs?origin={origin}&destination={destination}&autonomy={autonomy}&fuelPrice={fuelPrice}
Get the cost to the shortest path from the origin to destination using the previous persisted map


Path param | Description
------------- | -------------
mapName | map name of previous persisted map [required]

Query params | Description
------------- | -------------
origin | origin of the path (string) [required]
destination | destination of the path (string) [required]
autonomy | autonomy of the truck (float). Should be greather than 0. [required]
fuelPrice | price of fuel in km/l (float). Should be greather than 0. [required]

#### Example
```bash
curl -X GET 'http://localhost:4567/maps/SP/shortest-path-costs?origin=São+Paulo&destination=Americana&autonomy=11&fuelPrice=3.20'
```

#### Return payload (same object from request)
```json
{
    "shortestPath": {
        "mapName": "SP",
        "origin": "São Paulo",
        "destination": "Americana",
        "path": [
            "São Paulo",
            "Limeira",
            "Americana"
        ],
        "distance": 180
    },
    "autonomy": 11,
    "fuelPrice": 3.2,
    "cost": 0.2909091
}
```

Response payload fields | Description
------------- | -------------
shortestPath | object that describes the shortest path (object)
shortestPath.mapName | map name (string)
shortestPath.origin | origin of the shortest path (string)
shortestPath.destination | destination of the shortest path (string)
shortestPath.path | complete shortest path from the origin to the destination (array)
shortestPath.distance | distance from the origin to the destination of the shortest path (float)
autonomy | truck autonomy (km/l) (float)
fuelPrice | price of the fuel (l) (float)
cost | total fuel cost considering the parameters (float)


#### Return HTTP Codes

HTTP  Code | Description
------------- | -------------
200 - OK  | Calculation performed
400 - Bad Request  | Illegal request: missing or invalid field
404 - Not Found  | Map, origin or destination not found


