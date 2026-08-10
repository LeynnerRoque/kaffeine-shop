# Estágio 1: Build inteligente que acha o pom.xml
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY . .

# Localiza o pom.xml e executa o build no diretório correto
RUN find . -name "pom.xml" | head -n 1 | xargs -I {} dirname {} | xargs -I {} sh -c 'cd {} && mvn clean package -DskipTests'

# Estágio 2: Runtime (Copia o jar gerado usando um script sh seguro)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /deployments

--from=build
COPY --from=build /build /build

# Copia o jar gerado para app.jar de forma segura
RUN find /build -name "*.jar" ! -name "*sources*" ! -name "*javadoc*" -exec cp {} ./app.jar \;

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]