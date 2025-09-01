FROM eclipse-temurin:17-jdk AS builder

WORKDIR /application

RUN apt-get update && apt-get install -y bash wget tar

COPY mvnw ./
COPY .mvn/ .mvn/
COPY pom.xml .

COPY src src

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /application

RUN apt-get update && apt-get install -y wget tar && \
    wget https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz && \
    tar -C /usr/local/bin -xzvf dockerize-linux-amd64-v0.6.1.tar.gz && \
    rm dockerize-linux-amd64-v0.6.1.tar.gz

COPY --from=builder /application/target/*.jar application.jar

ENTRYPOINT ["sh", "-c", "dockerize -wait tcp://${DB_HOST}:${DB_PORT} -timeout 60s java -jar application.jar"]
