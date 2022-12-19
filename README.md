# Inditex exercise 2022
Inditex challenge

The service provides an endpoint to retrieve pre-loaded data about product prices.

The model followed is similar to a hexagonal model.

Packages:
- Boot: contains the main class
- Api: contains the endpoint
- Domain: contains entities and use case
- Infrastructure: contains repository implementations

Api receives requests from external users and it calls uses cases through interfaces. Use cases contain the business logic and could communicate with other external microservices o could call repositories. The infrastructure layer contains repository implementations.
Since domain is decoupled from infrastructure implementations, databases and other repositories could be migrated to another technology such as mongoDB, couchBase..
Furthermore each piece (api, domain, infrastructure) could be tested just mocking the other interfaces.

Scalability issues:

- Exceptions are managed by @ControllerAdvice, this way it is avoided to use try-catch blocks to capture each new endpoint exceptions
- The paradigm used is blocking. Since it is been using an H2 database, if moving to a mongodb, a reactive framework such as webflux/reactor could be considered.
- Cache is not considered. That is because of the datetime as input parameter. It will result in a bunch of missed hits.

OpenApi url:

- http://localhost:9005/swagger-ui/index.html#/price-controller/getProductPrice

Some integration tests are provided.

Security has been simplified and has a "permit all" level access.

Framework: Spring boot

Build tool: Maven

Run tests (from parent folder): 

- mvn clean test

Run app (from parent folder):  

- mvn spring-boot:run
