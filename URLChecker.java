
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class URLChecker {
    public static void main(String[] args) {
        String inputFilePath = ""; // Default input file path
        String outputFilePath = "output.txt"; // Default output file path
        boolean verboseOutput = false;

        if (args.length == 0 || args[0].equals("-h")) {
            printHelp();
            return;
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-list") && i < args.length - 1) {
                inputFilePath = args[i + 1];
            } else if (args[i].equals("-o") && i < args.length - 1) {
                outputFilePath = args[i + 1];
            } else if (args[i].equals("-v")) {
                verboseOutput = true;
            }
        }

        try {
            ArrayList<String> workingURLs = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

            String urlStr;
            while ((urlStr = reader.readLine()) != null) {
                try {
                    @SuppressWarnings("deprecation")
                    URL url = new URL(urlStr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    int statusCode = connection.getResponseCode();
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        workingURLs.add(urlStr);
                        if (verboseOutput) {
                            System.out.println(urlStr + " - Status Code: " + statusCode);
                        }
                        writer.write(urlStr + "\n");
                    } else {
                        if (verboseOutput) {
                            System.out.println(urlStr + " - Status Code: " + statusCode);
                        }
                    }
                    connection.disconnect();
                } catch (UnknownHostException e) {
                    System.out.println("Unknown host for URL: " + urlStr);
                }
            }

            reader.close();
            writer.close();

            System.out.println("Working URLs have been saved to " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printHelp() {
        System.out.println("Usage: java URLChecker [-list <input_file_path>] [-o <output_file_path>] [-v] [-h]");
        System.out.println("-list <input_file_path>: Specify the input file containing URLs to check.");
        System.out.println("-o <output_file_path>: Specify the output file to store the working URLs.");
        System.out.println("-v: Enable verbose output to display status codes in the command line.");
        System.out.println("-h: Display this help message.");
    }
}
