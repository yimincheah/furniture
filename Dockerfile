FROM openjdk:11
EXPOSE 3000
ADD target\furniture-management-system-0.0.1-SNAPSHOT.jar furniture-management-system-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/furniture-management-system-0.0.1-SNAPSHOT.jar"]