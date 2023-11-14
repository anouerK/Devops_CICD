FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8085
COPY target/Devops_Final.jar Devops_Final.jar
ENTRYPOINT ["java","-jar","/Devops_Final.jar"]