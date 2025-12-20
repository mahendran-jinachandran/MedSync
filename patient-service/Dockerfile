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
# 1. Use official Java runtime
FROM eclipse-temurin:22-jdk

# 2. Workdir inside the container
WORKDIR /app

# 3. Copy the built Spring Boot fat JAR into the container
COPY target/patient-service-*.jar app.jar

# 4. Document the internal port (patient-service uses 8082)
EXPOSE 8082

# 5. Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
