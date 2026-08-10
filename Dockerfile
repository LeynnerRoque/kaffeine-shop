# Estágio 1: Build tolerante a subdiretórios do Render
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia tudo o que vier no contexto de build de forma genérica
COPY . .

# Se os arquivos estiverem em uma subpasta oculta, este comando joga tudo para a raiz do /app
RUN if [ -d "$(ls -A | head -n 1)/src" ]; then cp -r $(ls -A | head -n 1)/* . ; fi

# Executa o Maven na raiz atualizada
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /deployments

# Copia o JAR gerado dinamicamente para app.jar
RUN find /app -name "*.jar" ! -name "*sources*" ! -name "*javadoc*" -exec cp {} ./app.jar \;

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]