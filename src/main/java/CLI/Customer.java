package CLI;

public class Customer implements Runnable{
    private TicketPool ticketPool;
    private int customerRetrievalRate;
    private int quantity;

    /**
     * Constructs a {@code Customer} object with the specified ticket pool, retrieval rate, and purchase quantity.
     *
     * <p>This constructor initializes a customer with the ticket pool they will interact with,
     * their ticket retrieval rate, and the quantity of tickets they can purchase in a single transaction.</p>
     *
     * @param ticketPool the {@code TicketPool} that the customer will retrieve tickets from.
     * @param customerRetrievalRate the rate at which the customer retrieves tickets from the pool.
     * @param quantity the quantity of tickets the customer can purchase in a single transaction.
     */
    public Customer(TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.quantity = quantity;
    }

    /**
     * Executes the process of a customer purchasing tickets from the ticket pool.
     *
     * <p>This method attempts to buy tickets from the {@code TicketPool} up to the specified quantity.
     * After each ticket purchase, it prints a message indicating the ticket details and the customer (thread)
     * who made the purchase. The method then pauses for a period determined by the customer's retrieval rate
     * before attempting to purchase the next ticket.</p>
     *
     * <p>The method runs in a separate thread and is responsible for simulating customer behavior in
     * the ticketing system, including buying tickets and respecting the defined retrieval rate.</p>
     *
     * @throws RuntimeException if the thread is interrupted during the sleep period.
     */
    @Override
    public void run() {
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = ticketPool.buyTicket();
            System.out.println("Ticket bought by " + Thread.currentThread().getName() + ". Ticket is " + ticket.toString());

            try{
                Thread.sleep(customerRetrievalRate * 1000);
            }catch(InterruptedException e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}

