package CLI;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicketingSystem {
    static Scanner input = new Scanner(System.in);

    /**
     * Prompts the user to input a positive non-zero integer and validates the input.
     *
     * <p>The method repeatedly displays a custom message to the user, asking for a valid
     * integer input. If the user enters a non-integer value or a number that is not positive,
     * an error message is displayed, and the user is prompted again. Input validation
     * ensures the method returns only a positive, non-zero integer.</p>
     *
     * @param message the custom message displayed to prompt the user for input.
     * @return a positive non-zero integer entered by the user.
     */
    public static int checkInput(String message){
        int number = 0;
        boolean correct = true;
        while(correct){
            try{
                System.out.print(message);
                number = input.nextInt();
                if(number> 0){
                    correct = false;
                }else{
                    System.out.println("Enter a positive non zero integer");
                }
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid number");
                input.nextLine();
            }
        }
        return number;
    }

    /**
     * Validates and retrieves the total number of tickets a vendor can add, ensuring
     * it does not exceed the maximum capacity allowed.
     *
     * <p>The method repeatedly prompts the user to input the number of tickets
     * until a valid number (not exceeding the specified maximum capacity) is entered.
     * If the input exceeds the maximum capacity, an error message is displayed,
     * and the user is prompted to re-enter the value.</p>
     *
     * @param maximumCapacity the maximum number of tickets allowed.
     * @return the validated total number of tickets a vendor can add.
     * @throws IllegalArgumentException if the input is invalid or exceeds the allowed range.
     */
    public static int checkTotalTicketValidity(int maximumCapacity){
        //int totalTickets = checkInput("Enter the number of total tickets: ");
        int totalTickets;
        while(true){
            totalTickets = checkInput("Enter the number of total tickets one vendor can add: ");
            if(totalTickets > maximumCapacity){
                System.out.println("Total tickets can't exceed maximum number of tickets");
                input.nextLine();
                continue;
            } else{
                break;
            }
        }
        return totalTickets;
    }

    public static void main(String[] args) {
        int maximumCapacity = 0;
        int totalTickets = 0;
        int releaseRate = 0;
        int retrievalRate = 0;
        int quantity = 0;


        System.out.println("-------------------");
        System.out.println("Enter 1 for load previous log");
        System.out.println("Enter 2 for add new data set");
        int option = input.nextInt();



        switch (option) {
            case 1:
                Configuration config = FileManager.getConfiguration();
                System.out.println("Configuration loaded successfully!!!");
                assert config != null;
                System.out.println("Total tickets: " + config.getTotalTickets());
                int previousTotalTickets = config.getTotalTickets();
                System.out.println("Maximum capacity: " + config.getMaximumCapacity());
                int previousMaximumCapacity = config.getMaximumCapacity();
                System.out.println("Release rate: " + config.getReleaseRate());
                int previousReleaseRate = config.getReleaseRate();
                System.out.println("Retrieval rate: " + config.getRetrievalRate());
                int previousRetrievalRate = config.getRetrievalRate();
                System.out.println("Quantity: " + config.getQuantity());
                int previousQuantity = config.getQuantity();

                previousDataProcess(previousMaximumCapacity, previousTotalTickets, previousReleaseRate, previousRetrievalRate, previousQuantity);
                break;
            case 2:
                maximumCapacity = checkInput("Enter the maximum capacity of the ticket pool: ");
                totalTickets = checkTotalTicketValidity(maximumCapacity);
                releaseRate = checkInput("Enter the ticket release rate: ");
                retrievalRate = checkInput("Enter the ticket retrieval rate: ");
                quantity = checkInput("Enter the quantity of tickets that a customer can buy: ");

                Configuration configuration = new Configuration(totalTickets, maximumCapacity, releaseRate, retrievalRate, quantity);
                FileManager.saveConfiguration(configuration);

                multiThreading(maximumCapacity, totalTickets, releaseRate, retrievalRate, quantity);
                break;
        }
    }

    /**
     * Processes the previous configuration data by delegating to the {@code multiThreading} method.
     *
     * <p>This method simplifies the handling of previous configuration values by passing them
     * to the {@code multiThreading} method, which initializes and manages the simulation
     * of vendors and customers interacting with the ticket pool.</p>
     *
     * @param previousMaximumCapacity the maximum capacity of the ticket pool from the previous configuration.
     * @param previousTotalTickets the total number of tickets available from the previous configuration.
     * @param previousReleaseRate the ticket release rate from the previous configuration.
     * @param previousRetrievalRate the ticket retrieval rate from the previous configuration.
     * @param previousQuantity the quantity of tickets a customer can buy from the previous configuration.
     *
     * @see #multiThreading(int, int, int, int, int)
     */

    public static void previousDataProcess(int previousMaximumCapacity, int previousTotalTickets,int previousReleaseRate, int previousRetrievalRate, int previousQuantity) {

        multiThreading(previousMaximumCapacity, previousTotalTickets, previousReleaseRate, previousRetrievalRate, previousQuantity);
    }


    /**
     * Initializes and starts a multi-threaded simulation of vendors and customers interacting with a ticket pool.
     *
     * <p>This method creates a {@code TicketPool} with the specified maximum capacity and spawns threads
     * for 10 {@code Vendor} and 10 {@code Customer} instances. Vendors release tickets to the pool at
     * the specified rate, while customers retrieve tickets at their respective rate and quantity.
     * Each thread is named for clarity during execution.</p>
     *
     * <p>The simulation models concurrent interactions with a shared resource, showcasing the behavior
     * of vendors and customers in a ticketing system.</p>
     *
     * @param maximumCapacity the maximum capacity of the ticket pool.
     * @param totalTickets the total number of tickets each vendor can add to the pool.
     * @param releaseRate the rate at which vendors release tickets to the pool.
     * @param retrievalRate the rate at which customers retrieve tickets from the pool.
     * @param quantity the quantity of tickets a customer can buy in a single transaction.
     *
     * @see TicketPool
     * @see Vendor
     * @see Customer
     */

    public static void multiThreading(int maximumCapacity,int totalTickets, int releaseRate, int retrievalRate, int quantity) {
        TicketPool ticketpool = new TicketPool(maximumCapacity);

        Vendor[] vendors = new Vendor[10];
        for (int i = 1; i <= vendors.length; i++) {
            vendors[i - 1] = new Vendor(ticketpool, totalTickets, releaseRate);
            Thread vendorThread = new Thread(vendors[i - 1], "Vendor - " + i);
            vendorThread.start();
        }

        Customer[] customers = new Customer[10];
        for (int i = 1; i <= customers.length; i++) {
            customers[i - 1] = new Customer(ticketpool, retrievalRate, quantity);
            Thread customerThread = new Thread(customers[i - 1], "Customer - " + i);
            customerThread.start();
        }
    }
}