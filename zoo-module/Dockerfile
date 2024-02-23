FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /zoo-module
COPY . /zoo-module/.
RUN mvn -f /zoo-module/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
WORKDIR /zoo-module
COPY --from=builder /zoo-module/target/*.jar /zoo-module/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/zoo-module/*.jar"]