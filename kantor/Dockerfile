# 1. Buduj projekt za pomocą Mavena (obraz z JDK i Mavenem)
FROM maven:3.9.6-eclipse-temurin-21 as build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Uruchom gotowy JAR przez lekki obraz Javy (tylko JRE)
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]