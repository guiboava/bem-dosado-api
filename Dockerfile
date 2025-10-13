FROM postgres:17.5

# Opcional: definir variáveis de ambiente padrão
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=bem_dosado_db

# Expor porta do container
EXPOSE 5432

FROM dpage/pgadmin4:7

# Variáveis de ambiente
ENV PGADMIN_DEFAULT_EMAIL=admin@admin.com
ENV PGADMIN_DEFAULT_PASSWORD=admin
EXPOSE 80

# Usando uma imagem JDK para rodar Spring Boot
# Usando JDK 21
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copia o JAR do Spring Boot
COPY target/bem-dosado-0.0.1-SNAPSHOT.jar app.jar

# Instala dockerize
RUN apt-get update && apt-get install -y wget tar \
    && wget https://github.com/jwilder/dockerize/releases/download/v0.9.2/dockerize-linux-amd64-v0.9.2.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-v0.9.2.tar.gz

# Espera o banco estar pronto antes de iniciar a aplicação
ENTRYPOINT ["dockerize", "-wait", "tcp://db:5432", "-timeout", "60s", "java", "-jar", "app.jar"]

EXPOSE 8080
