# URLChecker

URLChecker is a Java program designed to read a list of URLs from a text file, check if they are working, and store the working URLs in another text file. It provides options for specifying the input and output file paths, enabling verbose output for displaying status codes in the command line, and displaying help information.

## Features

- Reads URLs from a text file and checks if they are working.
- Saves the working URLs to another text file.
- Displays status codes in the command line (with verbose option).
- Provides a help option for usage instructions.

## Installation For Linux

Make sure you have Java Development Kit (JDK) installed.

```bash
sudo apt update
sudo apt install default-jdk
git clone https://github.com/mr-kasim-mehar/URL-Checker.git
```

### For Windows:

1. Download and install the latest version of Java Development Kit (JDK) from the [official website](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html).
2. Download the source code of URLChecker from the [GitHub repository](https://github.com/mr-kasim-mehar/URL-Checker.git).
3. Extract the downloaded ZIP file to your desired location.

Once JDK is installed and the source code is downloaded, you're ready to compile and run the program using the Command Prompt (CMD) or PowerShell in Windows.

```cmd
cd path\to\URLChecker
javac URLChecker.java
```


## Usage

Compile the Java program:

```bash
javac URLChecker.java
```

Run the program with desired options:

```bash
java URLChecker -list input.txt -o output.txt -v
```

### Options

- `-list <input_file_path>`: Specify the input file containing URLs to check.
- `-o <output_file_path>`: Specify the output file to store the working URLs.
- `-v`: Enable verbose output to display status codes in the command line.
- `-h`: Display help message.

## Contributing

Contributions are welcome. Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/new-feature`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature/new-feature`).
6. Create a new Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
