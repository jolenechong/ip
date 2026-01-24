# Simon project template

This is a project template for a greenfield Java project. It's named after the Java mascot _Duke_. Given below are
instructions on how to use it.

## Running the Project
### Using Gradle Wrapper from the command line
1. Open a terminal and navigate to the project root folder (the folder containing this `README.md` file).
1. Run the following command to build and run the project:
   - On Windows:
     ```cmd
     .\gradlew run
     ```
   - On macOS/Linux:
     ```bash
     ./gradlew run
       ```

### Using an IDE (e.g., Intellij)

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project
   first)
1. Open the project into Intellij as follows:
    1. Click `Open`.
    1. Select the project directory, and click `OK`.
    1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 17** (not other versions) as explained
   in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/simon/Simon.java` file, right-click it, and choose `Run Simon.main()` (if the
   code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something
   like the below as the output:
   ```
   Hello from
    ____        _        
   |  _ \ _   _| | _____ 
   | | | | | | | |/ / _ \
   | |_| | |_| |   <  __/
   |____/ \__,_|_|\_\___|
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move
Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle)
expect to find Java files.

## Project Structure
The project has the following structure:

```
project-root/
├── README.md
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
├── gradle/
├── .gitignore
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── simon/
│   │   │       ├── other packages...
│   │   │       └── Simon.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   └── simon/
│   │   │       ├── other packages test...
│       │       └── SimonTest.java

```

## Running Tests
To run the unit tests, you can use the Gradle wrapper from the command line:
- On Windows:
- ```cmd
    .\gradlew test
    ```
- On macOS/Linux:
- ```bash
    ./gradlew test
    ```
This will execute all tests in the project and display the results in the terminal.
