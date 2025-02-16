# Project Title
Test Task for CBR by Matveev Artur

## Description
This project is a Java-based application that includes both UI and API tests. The tests are structured to ensure the functionality and reliability of the applications.

## Installation
To set up the project, ensure you have [Maven](https://maven.apache.org/install.html) installed. Clone the repository and navigate to the project directory:

```bash
git clone <repository-url>
cd <project-directory>
```

Run the following command to install the necessary dependencies:

```bash
mvn install
```

## Usage
To run the tests, use the following command:

```bash
mvn test
```

This will execute all the tests located in the `src/test/java` directory.

## Testing
The project includes both UI and API tests. The UI tests can be found in the `src/test/java/ui` directory, while the API tests are located in the `src/test/java/api` directory. 

To run a specific test class, use:

```bash
mvn -Dtest=<TestClassName> test
```

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
