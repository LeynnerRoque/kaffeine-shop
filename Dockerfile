# Estágio 1: Build inteligente que acha o pom.xml
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY . .

# Localiza o pom.xml e executa o build no diretório correto
RUN find . -name "pom.xml" | head -n 1 | xargs -I {} dirname {} | xargs -I {} sh -c 'cd {} && mvn clean package -DskipTests'

# Estágio 2: Runtime (Quarkus Otimizado)
FROM eclipse-temurin:21-jre-alpine
ENV LANGUAGE='en_US:en'
WORKDIR /deployments

# Copia os diretórios do quarkus-app independentemente de onde o build os colocou
COPY --from=build /build/**/target/quarkus-app/lib/ ./lib/
COPY --from=build /build/**/target/quarkus-app/*.jar ./
COPY --from=build /build/**/target/quarkus-app/app/ ./app/
COPY --from=build /build/**/target/quarkus-app/quarkus/ ./quarkus/

EXPOSE 8080
USER 185

ENTRYPOINT ["java", "-Dquarkus.http.host=0.0.0.0", "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", "-jar", "quarkus-run.jar"]