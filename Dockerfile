FROM openjdk
COPY target/*.jar /
EXPOSE 8080
ENTRYPOINT ["java","-jar","/furniture-management-system-SNAPSHOT.jar"]
