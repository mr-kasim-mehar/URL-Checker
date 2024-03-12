import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class URLChecker {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        printBanner();

        if (args.length == 0 || args[0].equals("-h")) {
            printHelp();
            return;
        }

        try {
            String inputFilePath = "";
            String outputFilePath = "output.txt";
            boolean verboseOutput = false;
            String singleURL = null;

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-list") && i < args.length - 1) {
                    inputFilePath = args[i + 1];
                } else if (args[i].equals("-o") && i < args.length - 1) {
                    outputFilePath = args[i + 1];
                } else if (args[i].equals("-v")) {
                    verboseOutput = true;
                } else if (args[i].equals("-u") && i < args.length - 1) {
                    singleURL = args[i + 1];
                }
            }

            if (singleURL != null) {
                checkSingleURL(singleURL, outputFilePath, verboseOutput);
            } else {
                checkMultipleURLs(inputFilePath, outputFilePath, verboseOutput);
            }
        } catch (IOException e) {
            System.out.print("Invalid Url! The Url is not Exist");
        }
    }

    private static void printBanner() {

                System.out.println("                  __  __   ____     __       ______ __                 __              ");
                System.out.println("                / / / /  / __ \\   / /      / ____// /_   ___   _____ / /__ ___   _____");
                System.out.println("               / / / /  / /_/ /  / /      / /    / __ \\ / _ \\ / ___// //_// _ \\ / ___/");
                System.out.println("              / /_/ /  / _, _/  / /___   / /___ / / / //  __// /__ / ,<  /  __// /    ");
                System.out.println("              \\____/  /_/ |_|  /_____/   \\____//_/ /_/ \\___/ \\___//_/|_| \\___//_/     ");
                System.out.println(                  ANSI_GREEN +    "\t\t\t\t\t\t\t\t\tVersion 1.0"    +   ANSI_RESET               );
                System.out.println(                ANSI_GREEN +      "\t\t\t\t\tCoded By Muhammad Qasim"         +   ANSI_RESET             );
                System.out.println();
            
        
        
    }

    private static void printHelp() {
        System.out.println(ANSI_GREEN +"\tUsage:\n"+ANSI_RESET + ANSI_GREEN +"\t\tjava URLChecker [-list <input_file_path>] [-o <output_file_path>] [-v] [-u <single_url>] [-h]" +ANSI_RESET);
        System.out.println(ANSI_GREEN +"\t\t-h:"+ANSI_RESET+" Display this help message.");
        System.out.println(ANSI_GREEN +"\t\t-list:"+ANSI_RESET+" <input_file_path>: Specify the input file containing URLs to check.");
        System.out.println(ANSI_GREEN +"\t\t-o:"+ANSI_RESET+" <output_file_path>: Specify the output file to store the working URLs.");
        System.out.println(ANSI_GREEN +"\t\t-v:"+ANSI_RESET+" Enable verbose output to display status codes in the command line.");
        System.out.println(ANSI_GREEN +"\t\t-u:"+ANSI_RESET+" <single_url>: Specify a single URL to check.");
        
    }

    @SuppressWarnings("deprecation")
    private static void checkSingleURL(String singleURL, String outputFilePath, boolean verboseOutput) throws IOException {
        singleURL = addHttpsIfMissing(singleURL);

        URL url = new URL(singleURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            System.out.println(ANSI_GREEN +"[+]"+ ANSI_RESET+" URL is working: " + singleURL);
            if (verboseOutput) {
                System.out.println("Status Code: " + statusCode);
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                writer.write(singleURL);
            }
            System.out.println(ANSI_GREEN +"[+]"+ ANSI_RESET+" Working URL has been saved to " + outputFilePath);
        } 
        else {
           System.out.println(ANSI_RED +"[+]"+ ANSI_RESET+" URL is not working: " + singleURL);
            if (verboseOutput) {
                System.out.println(ANSI_RED +"[+]"+ ANSI_RESET+" URL is not working: " + singleURL + ANSI_RED +"Status Code:" + ANSI_RESET  + statusCode);
            }
        }
        connection.disconnect();
    }

    @SuppressWarnings("deprecation")
    private static void checkMultipleURLs(String inputFilePath, String outputFilePath, boolean verboseOutput) throws IOException {
        ArrayList<String> workingURLs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String urlStr;
            while ((urlStr = reader.readLine()) != null) {
                urlStr = addHttpsIfMissing(urlStr);
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int statusCode = connection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    workingURLs.add(urlStr);
                    if (verboseOutput) {
                        System.out.println(ANSI_GREEN +"[+]"+ ANSI_RESET+ urlStr + ANSI_GREEN + " - Status Code: " + ANSI_RESET + statusCode);
                    }
                } 
                else {
                    if (verboseOutput) {
                        System.out.println(ANSI_RED +"[+]"+ ANSI_RESET + urlStr + ANSI_RED + " - Status Code: " + ANSI_RESET + statusCode);
                    }
                }
                connection.disconnect();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String url : workingURLs) {
                writer.write(url + "\n");
            }
        }
        System.out.println(ANSI_GREEN +"[+]"+ ANSI_RESET + "Working URLs have been saved to " + outputFilePath);
    }

    private static String addHttpsIfMissing(String urlStr) {
        if (!urlStr.startsWith("http://") && !urlStr.startsWith("https://")) {
            urlStr = "https://" + urlStr;
            
        }
      
        return urlStr;
    }
}
