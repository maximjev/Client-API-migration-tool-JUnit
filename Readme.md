This api is designed to migrate from JUnit 4 version to JUnit 5 version.

Before applying migration api, add JUnit 5 dependency to your pom or gradle file.

This api covers most of the JUnit 4 API changes which are:
* Imports
* Annotations
* Assert -> Assertions
* Assume -> Assumptions
* Exception handling

Please, view full migration guide here: https://www.codingrevolution.com/junit-5-migration/

**HOW TO USE?**

Pass absolute path of files you want to migrate (ex. `/home/username/IdeaProjects/project/src/test/java`). 
The api does not handle <b>Categories</b>, and <b>Runners</b>, so those require manual edits.

When started, the api will recursively traverse files, process them one by one and notify if it was modified or not.

You can also run the api as jar. 

Firstly, compile jar file using `gradle fatJar` command.
Secondly, execute the compiled jar and pass path to JUnit 4 files as a parameter (ex `java -jar /home/username/IdeaProjects/project/src/test/java`)
The api will recursively traverse files and migrate them one by one. 

Commit your files before using the api, and review the code after using it.