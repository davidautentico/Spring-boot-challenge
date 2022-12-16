# Inditex_exercise_2022
Inditex challenge

The service provides an endpoint to retrieve pre-loaded data about product prices.

The model followed is similar to and hexagonal model.

Packages:
- Boot: contains main class
- Api: contains endpoint
- Domain: contains entities and use case
- Infrastructure: contains repository implementations

Api receives requests from external users and it call uses cases through interfaces. Use cases contain the business logic and could communicate with other external microservices o call repositories. The infrastructure layer contains repository implementations.

Scalability issues:

- Exceptions are managed by @ControllerAdvice, this way it is avoided to use try-catch blocks to capture each new endpoint exceptions
- The paradigm used is blocking. Since it is been using an H2 database, if moving to a mongodb a reactive framework such as webflux/reactor could be used.

Some integration tests are provided.

Security has been simply and has a "permit all" level access.

Framework: Spring boot

Build tool: Maven

Run tests (from parent folder): mvn clean test
