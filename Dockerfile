# Usa uma imagem base com Java 21 já instalado
FROM openjdk:21-jdk-slim AS build
LABEL authors="Tarcisio de Lima"

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de build do Gradle
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# Copia o código fonte
COPY src ./src

# Executa o Gradle para construir a aplicação
RUN ./gradlew bootJar

# Cria uma imagem menor para a runtime
FROM openjdk:21-jre-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR construído da fase de build
COPY --from=build /app/build/libs/*.jar app.jar

# Expõe a porta que aplicação vai usar
EXPOSE 8080

# Comando para executar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]