FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/Devops_Final.jar Devops_Final.jar
ENTRYPOINT ["java","-jar","/Devops_Final.jar"]