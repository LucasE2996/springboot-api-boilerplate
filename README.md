# Spring boot boilerplate

This project was created following a course by Alura about Spring and Advanced Spring features

## Actuator

The Spring actuator is a Spring module made to help Software Engineers monitor a Spring application.

> For this project the actuator is allowed to be accessed by everyone. Remeber to change this to an admin user if this goest to production. 

It has the following endpoints:

#### `[GET] {host}/actuator`

Return a list of the other endpoint available.

#### `[GET] {host}/actuator/health`

Return some relevant information such as the application status, database, disk space, and so on. This information can be configured in the `application.properties` file.

Example:
```properties
management.endpoint.health.show-details=always
management.endpoints.web.exposure.include=*
```


#### `[GET] {host}/actuator/health/{*path}`

#### `[GET] {host}/actuator/info`

This endpoint if configurable to show up any relevant information you may want to see. This configuration can be changes in the `application.properties` file as follows:
```properties
info.app.name=@project.name@
info.app.version=@project.version@
```

## Spring boot admin

There is an open source project called spring-boot-admin that help us a lot on monitoring Spring Boot applications.
I have already implemented this solution in another project/repo so if you want to get it up and running easily with this boilerplate download [this project](https://github.com/LucasE2996/spring-boot-admin) and follow the instructions there.