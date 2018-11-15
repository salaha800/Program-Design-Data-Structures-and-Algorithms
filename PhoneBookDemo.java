import java.io.*;
import java.util.Scanner;
import java.text.DecimalFormat;

/**
 * The demo class that we will run for testing
 * @author Christine Zarges
 * @version 1.1, 1st November 2017
 */
public class PhoneBookDemo {

    // the phone book object
    private PhoneBook phoneBook;

    // for measuring time
    private long startTime;

    // for reading input
    private Scanner inputScanner;

    // for formatting number output
    private DecimalFormat numberFormat;

    /**
     * A default constructor. Creates a PhoneBook object.
     */
    public PhoneBookDemo(){
        inputScanner = new Scanner(System.in);
        numberFormat = new DecimalFormat("#0.000000");

        /**
         * Instantiate phone book.
         */
        System.out.print("Select (h)ashing or (l)ist: ");
        String input = inputScanner.nextLine().toLowerCase();
        while ( ! ( ( input.startsWith("h") ) || ( input.startsWith("l") ) ) ){
            System.out.print("Invalid input. Please answer h/l. ");
            input = inputScanner.nextLine().toLowerCase();
        }

        /**
         * Instantiates the phone book.
         */
        if (input.startsWith("h"))
            phoneBook = new HashPhoneBook();
        else
            phoneBook = new ListPhoneBook();
    }

    /**
     * Start a timer for measuring elapsed time.
     */
    private void startTimer(){
        startTime = System.nanoTime();
    }

    /**
     * Stops the timer for measuring elapsed time and prints the result.
     */
    private void endTimer(String prefix){

        long endTime = System.nanoTime();
        double elapsedTime = (double)(endTime-startTime)/1000000.0;
        System.out.println(prefix+": "+numberFormat.format(elapsedTime)+" milliseconds ("
                +numberFormat.format(elapsedTime/1000.0)+" seconds).");
    }

    /**
     * Initialises the phone book. Can read existing phone book from text file (small, medium, large).
     */
    private void readPhoneBook(){
        /**
         * Decide if you want to start with an empty or sample phone book.
         */
        System.out.print("Start with sample phone book? [y/n] ");
        String input = inputScanner.nextLine().toLowerCase();
        while ( ! ( ( input.startsWith("y") ) || ( input.startsWith("n") ) ) ){
            System.out.print("Invalid input. Please answer y/n. ");
            input = inputScanner.nextLine().toLowerCase();
        }

        /**
         * Read sample phone book if selected.
         */
        if(input.startsWith("y")){
            // Select size of sample phone book.
            System.out.print("Select (s)mall, (m)edium, or (l)arge: ");
            String inputFile = inputScanner.nextLine().toLowerCase();
            while ( ! ( ( inputFile.startsWith("s") ) || ( inputFile.startsWith("m") ) || ( inputFile.startsWith("l") ) ) ){
                System.out.print("Invalid input. Please answer s/m/l. ");
                inputFile = inputScanner.nextLine().toLowerCase();
            }

            System.out.println("Reading phone book from file.");

            try {
                // open selected file
                File file = null;
                switch (inputFile){
                    case "s":
                        file = new File("phoneBook-small.txt");
                        break;
                    case "m":
                        file = new File("phoneBook-medium.txt");
                        break;
                    case "l":
                        file = new File("phoneBook-large.txt");
                        break;
                    default:
                        System.out.println("Input '"+inputFile+"' not recognised. Quit program.");
                        System.exit(1);
                }

                BufferedReader buffer = new BufferedReader(new FileReader(file));

                // read file line by line
                String readLine;
                int numEntries = 0;
                // for measuring time
                startTimer();
                while ((readLine = buffer.readLine()) != null) {
                    String[] entry = readLine.split("\\s");
                    int phoneNumber = Integer.parseInt(entry[0]);
                    //add to phone book

                    phoneBook.add(phoneNumber,entry[1]);
                    numEntries++;
                }
                // for measuring time
                endTimer("Adding "+numEntries+" entries");
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Starting with empty phone book.");
            } catch (IOException | NumberFormatException e) {
                System.out.println("Unable to read file. Starting with empty phone book.");
            }
        }
        else {
            System.out.println("Starting with empty phone book.");
        }
    }

    /**
     * Print information about how to use the program.
     */
    private void printHelp(){
        System.out.println("Usage: 1 (search), 2 (insert), 3 (update), 4 (delete) or 5 (quit).");
    }

    /**
     * Runs the demonstration.
     */
    private void run(){

        readPhoneBook();
        printHelp();

        /**
         * Main program loop that processes the queries and prints out results/error messages.
         */
        boolean run = true;
        while(run) {
            System.out.print("Chose query type (or press ENTER for help): ");
            String query = inputScanner.nextLine();

            switch (query) {
                case "1":
                    System.out.println("Search for contact.");
                    System.out.print("Enter phone number: ");
                    try {
                        int phoneNumber = Integer.parseInt(inputScanner.nextLine());
                        startTimer();
                        String name = phoneBook.search(phoneNumber);
                        endTimer("Search");
                        if (name == null)
                            System.out.println("Phone number " + phoneNumber + " does not exist.");
                        else
                            System.out.println("Search successful: Number " + phoneNumber + " belongs to " + name + ".");

                    } catch (NumberFormatException e) {
                        System.out.println("Not a legal phone number.");
                    }
                    break;
                case "2":
                    System.out.println("New contact.");
                    System.out.print("Enter phone number: ");
                    try {
                        int phoneNumber = Integer.parseInt(inputScanner.nextLine());
                        System.out.print("Enter name: ");
                        String name = inputScanner.nextLine();
                        startTimer();
                        if (phoneBook.add(phoneNumber, name))
                            System.out.println("Added contact: " + phoneNumber + ", " + name + ".");
                        else
                            System.out.println("Contact with phone number " + phoneNumber
                                    + " already exists (" + phoneBook.search(phoneNumber) + ").");
                        endTimer("Insert");
                    } catch (NumberFormatException e) {
                        System.out.println("Not a legal phone number.");
                    }
                    break;
                case "3":
                    System.out.println("Update contact.");
                    System.out.print("Enter phone number: ");
                    try {
                        int phoneNumber = Integer.parseInt(inputScanner.nextLine());
                        System.out.print("Enter new name: ");
                        String name = inputScanner.nextLine();
                        startTimer();
                        String result = phoneBook.update(phoneNumber, name);
                        endTimer("Update");
                        if (result == null)
                            System.out.println("Phone number " + phoneNumber + " does not exist.");
                        else
                            System.out.println("Contact with phone number " + phoneNumber + " updated from '"
                                    + result + "' to '" + name + "'.");
                    } catch (NumberFormatException e) {
                        System.out.println("Not a legal phone number.");
                    }
                    break;
                case "4":
                    System.out.println("Remove contact.");
                    System.out.print("Enter phone number: ");
                    try {
                        int phoneNumber = Integer.parseInt(inputScanner.nextLine());
                        startTimer();
                        String name = phoneBook.remove(phoneNumber);
                        endTimer("Remove");
                        if (name == null)
                            System.out.println("Phone number " + phoneNumber + " does not exist.");
                        else
                            System.out.println("Removed contact: " + phoneNumber + ", " + name + ".");

                    } catch (NumberFormatException e) {
                        System.out.println("Not a legal phone number.");
                    }
                    break;
                case "5":
                    run = false;
                    break;
                default:
                    printHelp();
            }
        }
    }

    public static void main(String args[]) {
        PhoneBookDemo demo = new PhoneBookDemo();
        demo.run();
    }

}
