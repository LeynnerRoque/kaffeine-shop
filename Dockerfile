# Estágio 1: Build inteligente que acha o pom.xml
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY . .

# Localiza o pom.xml e executa o build no diretório correto
RUN find . -name "pom.xml" | head -n 1 | xargs -I {} dirname {} | xargs -I {} sh -c 'cd {} && mvn clean package -DskipTests'

# Estágio 2: Runtime (Captura o jar gerado independentemente do nome)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /deployments

# Copia o arquivo .jar executável gerado na pasta target (excluindo sources/javadoc se houver)
COPY --from=build /build/**/target/*-runner.jar ./app.jar || COPY --from=build /build/**/target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]