FROM openjdk:8-jre-alpine
WORKDIR /app
COPY app.jar .
COPY wait-for-it.sh .
RUN apk add bash
ENTRYPOINT ["./wait-for-it.sh", "order-mysql:30001", "--", "java", "-jar", "/app/app.jar"]
