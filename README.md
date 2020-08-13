# Spring Microservice Tags Application

## Run Tests
`./mvnw clean test`

This command will execute both unit tests & integrations tests 
sub-modules

## Run Application

#### Build Application
`./mvnw clean package` 

#### Service Registry (Eureka)
In order to make the whole stack UP, it is required to have Eureka discovery server up & running

[Run] Command : `java -jar service-registry/target/service-registry-0.0.1-SNAPSHOT.jar`

URL : http://localhost:8761

#### Api Gateway (Spring Cloud)
Api gateway is responsible to provide routing within various services, i.e. In a proper environment on Gateway will be exposed to 
outer network and services will be accessible via gateway only.

[Run] Command : `java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar`

URL : http://localhost:8080
#### TagsService
Separate service responsible for Crud operations for Tags.

[Run] Command : `java -jar tags-service/target/tags-service-0.0.1-SNAPSHOT.jar`

URL : http://localhost:8081
via Gateway : http://localhost:8080/api/v1/tags/

#### Verify via Postman/Browser
URL : http://localhost:8080
#####Endpoints 

- **_GET /api/v1/tags_** -> will return list of tags
- **_POST /api/v1/tags_** -> will create a new tag 

    Request Body
    ````
    {
        "key" : "someKey",
        "value" : "someValue"
    }
    ````
- **_GET /api/v1/tags/{id}_** -> will return one tag based on ID, else 404 error if not tag is available via this id
- **_PUT /api/v1/tags/{id}_** -> will update the tag based on ID, else 404 error if not tag is available via this id

    Request Body
    ````
    {
        "key" : "someKey",
        "value" : "someValue"
    }
    ````
- **_DELETE /api/v1/tags/{id}_** -> will delete the tag based on ID, This will return HTTP Code 202 (Accepted), If will not return any error if Id is not available
