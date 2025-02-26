# Building Kotlin app
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# to have better caching
COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN chmod +x gradlew

# downloading dependencies before copying source code
RUN ./gradlew dependencies --no-daemon

# copy the rest and build
COPY . .
RUN ./gradlew bootJar --no-daemon

# Creating a minimal runtime container (only the JRE to reducing image size)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/pdf-builder-0.0.1.jar pdf-builder.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pdf-builder.jar"]
