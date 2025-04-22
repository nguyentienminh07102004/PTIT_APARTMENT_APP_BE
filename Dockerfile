FROM maven:3.9.6-amazoncorretto-17-debian AS build
# mkdir directory
WORKDIR /app
# copy <source> <dest>
COPY pom.xml .
# copy src
COPY src ./src
RUN mvn clean package

FROM openjdk:17-ea-oracle

WORKDIR /app
COPY --from=build /app/target/*.war app.war
COPY smart-condo-e6e54-firebase-adminsdk-fbsvc-e10dd1c129.json smart-condo-e6e54-firebase-adminsdk-fbsvc-e10dd1c129.json
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.war"]