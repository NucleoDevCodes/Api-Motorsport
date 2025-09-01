# API Motorsport

API desenvolvida em **Java + Spring Boot** para gerenciamento de dados do Motorsport.  
Banco de dados utilizado: **PostgreSQL**.  

---

##  Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Maven**
- **JUnit 5** (testes unitários)
- **Docker & Docker Compose** (opcional)

---

## ⚙️ Configuração do Projeto

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/NucleoDevCodes/Api-Motorsport.git
cd Api-Motorsport
```


### 2️⃣ Configurar o banco PostgreSQL

## Crie o banco no PostgreSQL:
```bash
CREATE DATABASE motorsport;
````
```bash
Edite o arquivo src/main/resources/application.properties com suas credenciais:

spring.datasource.url=jdbc:postgresql://localhost:5432/motorsport
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 3️⃣ Executar a aplicação
```bash
./mvnw spring-boot:run
```
## ou
```bash
mvn spring-boot:run
````

### Endpoints da API

## Carros

POST /carro → cadastrar carro

GET /carro → listar carros

GET /carro/{id} → buscar carro por ID

PUT /carro/{id} → atualizar carro

DELETE /carro/{id} → remover carro

## Conteúdo

POST /sobre  -> cadastra contéudo

GET /sobre/{id} → buscar contéudo por ID

PUT /sobre/{id} → atualizar contéudo

DELETE /sobre/{id} → remover contéudo

## Especificação Tecnica

POST /especificacao  -> cadastra especificacao

GET /especificacao/{id} → buscar especificacao por ID

PUT /especificacao/{id} → atualizar especificacao

DELETE /especificacao/{id} → remover especificacao

## Upload

POST /upload → cadastrar imagem

GET /upload → listar imagens

GET /upload/{id} → buscar imagem por ID

PUT /upload/{id} → atualizar imagem

DELETE /upload/{id} → remover imagem


###  🧪 Testes

Rodar os testes unitários:

````bash
mvn test
````

## 🐳 Rodando com Docker
### 1️⃣ Rodar apenas o banco 
````bash
docker run --name motorsport-db \
  -e POSTGRES_USER=motorsport \
  -e POSTGRES_PASSWORD=motorsport \
  -e POSTGRES_DB=motorsport \
  -p 5432:5432 \
  -d postgres:15
````

### 2️⃣ Criar imagem da API

Na raiz do projeto, crie um Dockerfile:

````bash
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/Api-Motorsport-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
````

Build da imagem:

````bash
mvn clean package -DskipTests
docker build -t api-motorsport .
````

Rodar a API conectando ao banco:
````bash
docker run --name motorsport-api \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://motorsport-db:5432/motorsport \
  -e SPRING_DATASOURCE_USERNAME=motorsport \
  -e SPRING_DATASOURCE_PASSWORD=motorsport \
  -p 8080:8080 \
  --link motorsport-db \
  api-motorsport
````
