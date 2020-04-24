# Simple REST service
## Build
mvn package
## Launch
java -jar target/restservice-0.0.1-SNAPSHOT.jar
## Test
Use command 'curl' on http://localhost:8080. 

For example:

curl -d '{ "seller":"123534251", "customer":"648563524", "products":[{"name":"milk","code":"2364758363546"},{"name":"water","code":"3656352437590"}] }' -H "Content-Type: application/json" -X POST http://localhost:8080
