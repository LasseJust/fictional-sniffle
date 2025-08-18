# Counter
CLI app to count words.

## Building the app
To build the app run:
```shell script
./gradlew clean build
```

### Running tests
To run the tests:
```shell script
./gradlew test
```

## Running the app
Run the jar file, specify exactly one parameter, the input/output folder path

```shell script
java -jar ./build/libs/counter-1.0.jar <input-path> <output-path> <exclude-file-path>
```
Example run, with included repo data:
```shell script
java -jar ./build/libs/counter-1.0.jar data data/output data/exclude.txt
```
