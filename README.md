***<h2>Fitness Tracking API</h2>***

This is the backend service for a personal fitness and health tracking application. It is built using Spring Boot and Spring Data JPA to manage complex, related entities including polymorphic workouts, user-defined goals, and health metrics (nutrition, sleep and heart rate), as well as billing management.

***<h3>Setup and Running the Application</h3>***

**1. Clone the repository:**

git clone <YOUR_REPOSITORY_URL>

cd fitness-tracking-application

**2. Configure the Database**:

Update the src/main/resources/application.properties (or application.yml) file with your database credentials and connection details:

spring.datasource.url=jdbc:postgresql://**localhost:5432/fitness**

spring.datasource.username=dbuser

spring.datasource.password=dbpassword

spring.jpa.hibernate.ddl-auto=update

**3. Using an IDE (e.g., IntelliJ IDEA):**

**Build and Resolve Dependencies:**

Open the Maven Tool Window (usually on the right side of the IDE).

Click the "Reload All Maven Projects" button (a circular arrow icon) to download dependencies and refresh the project structure.

**Start the application:**

Locate the main class of your Spring Boot application (the class annotated with @SpringBootApplication).

Click the green run arrow next to the main class declaration or next to the main method signature.

The application will start on **http://localhost:8080**.

**4. SpringDoc OpenAPI:**

This project uses SpringDoc OpenAPI to generate interactive documentation.

Ensure the application is running (by clicking the green run arrow in IntelliJ).

Open your web browser and navigate to the following URL:

**http://localhost:8080/swagger-ui.html**
