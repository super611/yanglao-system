FROM openjdk:8-jre-alpine
WORKDIR /app
COPY app.jar .
COPY wait-for-it.sh .
RUN apk add bash
ENTRYPOINT ["./wait-for-it.sh", "user-mysql:30002", "--", "java", "-jar", "/app/app.jar"]
