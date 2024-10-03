FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=build /app/target/vendor-0.0.1-SNAPSHOT.jar vendor.jar
EXPOSE 9600
ENTRYPOINT ["java", "-jar", "vendor.jar"]
