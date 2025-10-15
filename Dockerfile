FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Instala bash
RUN apk add --no-cache bash netcat-openbsd

# Copia jar e wait-for-it
COPY target/*.jar app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

CMD ["./wait-for-it.sh", "db:5432", "--timeout=60", "--strict", "--", "java", "-jar", "app.jar"]

EXPOSE 8080