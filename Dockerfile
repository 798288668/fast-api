FROM openjdk:8-jre as builder
WORKDIR /app
ADD ./target/*.jar ./app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:8-jre
WORKDIR /app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./
CMD java $JAVA_OPTS org.springframework.boot.loader.JarLauncher --spring.profiles.active=$CONFIG_ENV
