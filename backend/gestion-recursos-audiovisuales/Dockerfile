FROM openjdk:17

WORKDIR /app

COPY . .

ENV DATABASE_URL jdbc:mysql://mysqldb:3306/db-recursos
ENV DATABASE_USERNAME root
ENV DATABASE_PASSWORD admin
ENV DATABASE_PLATFORM org.hibernate.dialect.MySQL57Dialect
ENV DATABASE_DRIVER com.mysql.cj.jdbc.Driver

COPY target/gestion-recursos-audiovisuales-0.0.1-SNAPSHOT.jar /app/gestion-recursos-audiovisuales.jar
#COPY src/main/resources/initial_data.json /app/initial_data.json

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "gestion-recursos-audiovisuales.jar"]
