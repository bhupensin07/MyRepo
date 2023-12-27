FROM maven:3.8.3-openjdk-17-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -version
RUN java -version
RUN mvn -B package --file pom.xml -DskipTests

FROM openjdk:17-slim
COPY --from=build /workspace/target/department-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 6379
ENTRYPOINT ["java","-jar","app.jar"]
