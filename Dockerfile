# Usa uma imagem base com Java 21 já instalado
FROM amazoncorretto:21-alpine-jdk AS build
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

# Dá permissão de execução ao gradlew
RUN chmod +x gradlew

# Executa o Gradle para construir a aplicação
RUN ./gradlew bootJar

# Cria uma imagem menor para a runtime
FROM amazoncorretto:21-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR construído da fase de build
COPY --from=build /app/build/libs/*.jar app.jar

# Expõe a porta que aplicação vai usar
EXPOSE 8080

# Comando para executar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-Dfile.encoding=UTF8", "-Duser.timezone=America/Sao_Paulo", "-jar", "app.jar"]