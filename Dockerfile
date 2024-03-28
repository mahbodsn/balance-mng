FROM openjdk:23-slim
WORKDIR /app
COPY target/balancemng-1.0.0-SNAPSHOT.jar .
EXPOSE 9000
ENTRYPOINT ["java","-jar","balancemng-1.0.0-SNAPSHOT.jar"]