FROM openjdk
WORKDIR usr/lib
ADD ./target/api-gateway-service-0.0.1-SNAPSHOT.jar /usr/lib/api-gateway-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "api-gateway-service-0.0.1-SNAPSHOT.jar"]