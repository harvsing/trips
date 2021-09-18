# Getting Started
- java 8
- gradle 4.10.3 or higher

### build the project
As this project uses Gradle Wrapper you don't need to install gradle locally. Gradle wrapper (gradlew) download a proper version of gradle and build the project with it.
The build command runs the unit tests as well. If there is any test issue, the build fails.
The following command builds a "trips.jar" inside build/libs folder in the project.
`./gradlew clean build`

### Importing the project
This project can be imported into any IDE as a normal gradle project.
Once imported, you should be able to view the java class files in a proper java project structure.

### running the jar
once the jar file is created using gradle build, it can be executed as an executable jar file

The following command runs the application as an executable jar file
Open command prompt and navigate to the project root folder. run the following command :
`java -jar build/libs/trips.jar`

A pre-built jar file is also provided in the project's root folder. This jar can also be executed if you have not built the application yet.
The following command runs the pre-built jar.
Open command prompt and navigate to the project root folder. run the following command :
`java -jar trips.jar`
The executable jar needs an input CSV file named "taps.csv", this file should also be placed in the project's root folder. A sample "taps.csv" is also provided.
The output of the jar execution is another CSV file ("trips.csv"). This file is also located in the project's root folder.

### Assumptions
Following assumptions have been made during the development of the app:
 1) input CSV file is well formed and is not missing any data. This means that an OFF tapType will always have a higher ID value than the matching ON tapType.
 2) data inside the csv file is sorted based on ID column.
 3) Incomplete trips will always have TapType = ON . There can be no OFF tapType without a matching ON tapType.
 4) ON tap will always be followed by an OFF tap even on different dates.
 5) PAN is a unique identifier and is used to identify matching taps.
 6) Fare(chargeAmount) is provided and is constant between two different stops.