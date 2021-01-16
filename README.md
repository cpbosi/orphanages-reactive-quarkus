# learning-reactive-quarkus project

Project was developed during study of reactive quarkus, being composed by a simple CRUD of orphanages which is made available via swagger-ui. This project was deployed to Heroku and is available at the link https://quarkus-reactive-orphanage.herokuapp.com/

If you wanted to see a more complete project (just a CRUD, but using flyway, panache, healthCheck, among others https://github.com/cpbosi/orphanages-quarkus).

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev -Dquarkus.profile=dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `learning-reactive-quarkus-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/learning-reactive-quarkus-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/learning-reactive-quarkus-1.0.0-SNAPSHOT-runner`