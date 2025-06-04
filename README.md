# Home Budget App

This repository contains a .NET version and a Spring Boot version of the Home Budget application.

## Building the Spring Boot application

```bash
cd home-budget-app-java
mvn package
```

The resulting JAR will be in `target/homebudgetapp-0.0.1-SNAPSHOT.jar` and can be started with:

```bash
java -jar target/homebudgetapp-0.0.1-SNAPSHOT.jar
```

After starting the application you can open `http://localhost:8080/login` in a browser to see the login page.
The main dashboard is available at `http://localhost:8080/`. You can also visit
`/expenses` and `/incomes` for simple expense and income views.
