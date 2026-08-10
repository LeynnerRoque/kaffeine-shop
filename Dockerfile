# 1. Imagem base com JDK 21 e Maven para buildar a aplicação
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY pom.xml .
COPY src ./src

# Compila o projeto e gera o arquivo .jar (pulando os testes para ser rápido)
RUN mvn clean package -DskipTests

# 2. Imagem final leve apenas com o JRE para rodar o app
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o JAR gerado do estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Porta padrão (o Render sobrescreve via variável $PORT se necessário)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]