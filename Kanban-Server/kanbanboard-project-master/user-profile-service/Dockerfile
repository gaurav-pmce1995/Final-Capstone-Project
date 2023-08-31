FROM openjdk
WORKDIR usr/lib
ADD ./target/user-profile-service-0.0.1-SNAPSHOT.jar /usr/lib/user-profile-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "user-profile-service-0.0.1-SNAPSHOT.jar"]