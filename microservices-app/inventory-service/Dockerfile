# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /project
COPY pom.xml /project/pom.xml
COPY common/pom.xml /project/common/pom.xml
COPY discovery-service/pom.xml /project/discovery-service/pom.xml
COPY api-gateway/pom.xml /project/api-gateway/pom.xml
COPY user-service/pom.xml /project/user-service/pom.xml
COPY inventory-service/pom.xml /project/inventory-service/pom.xml
COPY order-service/pom.xml /project/order-service/pom.xml
RUN mvn -q -e -DskipTests dependency:go-offline
COPY . /project
RUN mvn -q -pl inventory-service -am -DskipTests package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /project/inventory-service/target/inventory-service-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]