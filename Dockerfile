# Estágio 1: Build inteligente que acha o pom.xml
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY . .

# Localiza o pom.xml e executa o build no diretório correto
RUN find . -name "pom.xml" | head -n 1 | xargs -I {} dirname {} | xargs -I {} sh -c 'cd {} && mvn clean package -DskipTests'

# Estágio 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /deployments

# Copia os arquivos do estágio anterior
COPY --from=build /build /build

# Localiza o .jar executável gerado e o move para app.jar
RUN find /build -name "*.jar" ! -name "*sources*" ! -name "*javadoc*" -exec cp {} ./app.jar \;

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]