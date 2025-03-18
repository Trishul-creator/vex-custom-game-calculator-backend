FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/vex-custom-game-calculator.jar vex-customg-game-calculator.jar
EXPOSE 8080
CMD ["nohup", "java", "-jar", "vex-custom-game-calculator.jar"]