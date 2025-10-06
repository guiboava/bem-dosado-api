# Etapa 1: Build da aplicação
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar pom.xml e baixar dependências (cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código
COPY src ./src

# Build sem testes
RUN mvn clean package -DskipTests

# Etapa 2: Rodar a aplicação
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar JAR final da etapa de build
COPY --from=build /app/target/*.jar app.jar

# Copiar script wait-for-it
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8080

# Aguarda o DB estar pronto antes de iniciar o Spring Boot
# Adiciona timeout de 60s e intervalo de 2s entre tentativas
ENTRYPOINT ["/wait-for-it.sh", "db:5432", "--timeout=60", "--strict", "--", "java", "-jar", "app.jar"]