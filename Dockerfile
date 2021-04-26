FROM amazoncorretto:11-alpine-jdk

COPY build/libs/magnumToLimelightXML.jar  /usr/local/bin/magnumToLimelightXML.jar

ENTRYPOINT ["java", "-jar", "/usr/local/bin/magnumToLimelightXML.jar"]