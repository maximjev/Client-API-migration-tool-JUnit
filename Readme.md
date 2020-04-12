This migration tool is designed to migrate from JUnit 4 version to JUnit 5 version.

Before applying migration tool, add JUnit 5 dependency to your pom or gradle file.

This api covers most of the JUnit 4 API changes which are:
* Imports
* Annotations
* Assert -> Assertions
* Assume -> Assumptions
* Exception handling

Please, view full migration guide here: https://www.codingrevolution.com/junit-5-migration/

JUnit API changes (migration package) is provided in `JUnitMigrationPackage` class.
 
The migration tool can be used for other types of migrations, right now it includes current types of refactorings:
* annotation find/replace
* import find/replace
* method call find/replace
* method argument position recalculation

The tool supports adding custom migration implementation, by extending `MigrationService` and adding it to
MigrationTool by calling `addMigrationService()`. 


**HOW TO USE?**

Pass absolute path of files you want to migrate (ex. `/home/username/IdeaProjects/project/src/test/java`). 
The api does not handle <b>Categories</b>, and <b>Runners</b>, so those require manual edits.

When started, the api will recursively traverse files, process them one by one and notify if it was modified or not.

You can also run the api as jar. 

Firstly, compile jar file using `gradle fatJar` command.
Secondly, execute the compiled jar and pass path to JUnit 4 files as a parameter (ex `java -jar /home/username/IdeaProjects/project/src/test/java`)
The api will recursively traverse files and migrate them one by one. 

Commit your files before using the api, and review the code after using it.