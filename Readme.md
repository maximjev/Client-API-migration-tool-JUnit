This tool is designed to migrate from JUnit 4 version to JUnit 5 version.

Before applying migration tool, add JUnit 5 dependency to your pom or gradle file.

This tool covers most of the JUnit 4 API changes which are:
* Imports
* Annotations
* Assert -> Assertions
* Assume -> Assumptions
* Exception handling

Please, view full migration guide here: https://www.codingrevolution.com/junit-5-migration/

**HOW TO USE?**

Firstly, compile jar file using `gradle fatJar` command.
Secondly, execute the compiled jar and pass path to JUnit 4 files as a parameter.
The tool will recursively traverse files and migrate them one by one. 

Commit your files before using the tool, and review the code after using it.