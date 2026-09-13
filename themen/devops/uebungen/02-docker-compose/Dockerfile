# ---- Stufe 1: bauen -------------------------------------------------------------
# Volles JDK, weil hier kompiliert wird. Dieses Image landet NICHT in der Auslieferung.
FROM docker.io/library/eclipse-temurin:21-jdk AS build
WORKDIR /quelle

# Reihenfolge = Cache-Reihenfolge: Was sich selten ändert, kommt zuerst.
# 1. Wrapper – lädt die Gradle-Distribution; ändert sich fast nie.
COPY gradlew gradlew
COPY gradle gradle
RUN chmod +x gradlew && ./gradlew --version --no-daemon

# 2. Build-Dateien – lädt die Abhängigkeiten; ändert sich selten.
COPY settings.gradle build.gradle ./
RUN ./gradlew dependencies --no-daemon --quiet

# 3. Quellcode – ändert sich ständig; nur diese Schicht wird nach einer Code-Änderung neu gebaut.
COPY src src
RUN ./gradlew bootJar --no-daemon --quiet

# ---- Stufe 2: ausführen ---------------------------------------------------------
# Nur die JRE: kein Compiler, keine Quellen, kein Gradle – kleiner und weniger Angriffsfläche.
FROM docker.io/library/eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /quelle/build/libs/kundenapi.jar app.jar
EXPOSE 8080
USER 1000
ENTRYPOINT ["java", "-jar", "app.jar"]
