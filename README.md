# Device Status Application

Spring Boot application with a MySQL backend, containerized using Docker and Docker Compose for easy local development.

--------------------------------------------------------------------------------
Easy Run Setup Using Docker

1. Make sure Docker Engine is running on your local machine.
2. Ensure the following ports are free:
   - 3306 (MySQL)
   - 8080 (Application)
3. Unzip the file: device-status.zip
4. Navigate to the unzipped project directory:
   cd path/to/unzipped/device-status
5. Start the containers:
   docker compose up --build
6. Must use API Key (shared via email PDF)
7. Once containers are running, test the application with the following endpoints:

   POST http://localhost:8080/status
   JSON Body:
   {
     "deviceId": "sensor-abc-123",
     "timestamp": "2025-06-09T15:10:00Z",
     "batteryLevel": 82,
     "rssi": -59,
     "online": true
   }

   GET latest record by device ID:
   http://localhost:8080/status/deviceId

   GET latest status summary of all devices:
   http://localhost:8080/status/summary

   GET history of specific device:
   http://localhost:8080/status/history/deviceId

8. To stop Docker:
   docker compose down -v
   (Note: This will stop and delete the containers.)

--------------------------------------------------------------------------------
Run Without Docker

Requirements:
- Java 21 or later
- Maven
- MySQL

Steps:
1. Clone the repository:
   git clone https://github.com/mohinibudhale/device-status.git
   cd device-status

2. Open the project in Eclipse or any IDE of your choice.

3. Create the following files with the content below:

/device-status/src/main/resources/application.properties
----------------------------------------------------------------------
spring.application.name=device-status
spring.datasource.url=jdbc:mysql://localhost:3306/device_status_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=test
spring.datasource.password=test
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
app.api.key=XYZ
----------------------------------------------------------------------

/device-status/src/main/resources/application-test.properties
----------------------------------------------------------------------
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
----------------------------------------------------------------------

4. Build and run the Spring Boot app using Maven:
   mvn spring-boot:run

5. App will be available at:
   http://localhost:8080

--------------------------------------------------------------------------------
Verifying It Works

Use Postman or any other tool:
- Add API Key in custom HTTP header: x-api-key

Test the following endpoints:

POST http://localhost:8080/status
JSON Body:
{
  "deviceId": "sensor-abc-123",
  "timestamp": "2025-06-09T15:10:00Z",
  "batteryLevel": 82,
  "rssi": -59,
  "online": true
}

GET latest record by device ID:
http://localhost:8080/status/deviceId

GET latest status summary of all devices:
http://localhost:8080/status/summary

GET history of specific device:
http://localhost:8080/status/history/deviceId

--------------------------------------------------------------------------------
Running Test Cases

- Run tests using Maven:
  mvn test

- Or right-click on test files in your IDE and run them directly.
