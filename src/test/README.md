# Testing using JUnit

This project uses JUnit for testing. Below are some common commands to run and manage tests using Gradle.

```bash
./gradlew test         # Run all tests
./gradlew test --tests "simon.ui.UITest"  # Run a single test
```

In IntelliJ, you can right-click on a test class or method and select "Run" to execute tests directly from the IDE or double press Ctrl to run the commands with gradle.

### Using the .jar file

To use the compiled .jar file for testing, you can run the following commands:

```bash
./gradlew clean shadowJar
java -jar build/libs/simon-1.0.0.jar
```

### Note to self
```aiignore
gh release create v1.0.0 ./build/libs/simon-1.0.0.jar --repo jolenechong/ip --title "v1.0.0" --notes "Release up to A-jar increments"
# to create a new release
```