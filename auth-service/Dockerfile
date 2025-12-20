# ---------- Build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom and download dependencies first (better caching)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Now copy the source and build
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Run stage ----------
# 1. Use an official Java runtime image (Temurin is the OpenJDK build)
FROM eclipse-temurin:22-jdk

# 2. Set working directory inside the container
WORKDIR /app

# 3. Copy the built JAR from your machine into the container
#    This matches any file like auth-service-0.0.1-SNAPSHOT.jar
COPY target/auth-service-*.jar app.jar

# 4. (Optional) Document the port the app listens on (server.port=8081)
EXPOSE 8081

# 5. Command to start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
