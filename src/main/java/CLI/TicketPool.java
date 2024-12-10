package CLI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());
    private final int maxCapacity;
    //private boolean allTicketsSold = false;

    /**
     * Constructs a {@code TicketPool} with the specified maximum capacity.
     *
     * <p>This constructor initializes the ticket pool with a maximum capacity, which
     * determines the total number of tickets that the pool can hold.</p>
     *
     * @param maxCapacity the maximum capacity of the ticket pool.
     */
    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Adds a ticket to the ticket pool, ensuring that the pool does not exceed its maximum capacity.
     *
     * <p>This synchronized method adds a {@code Ticket} to the pool. If the pool is already at
     * maximum capacity, the thread will wait until space becomes available. Once a ticket is successfully added,
     * the method notifies other threads and logs the addition. If the pool reaches its maximum capacity after the addition,
     * the program will terminate.</p>
     *
     * @param ticket the {@code Ticket} to be added to the pool.
     *
     * @throws RuntimeException if the thread is interrupted while waiting to add the ticket.
     */
    public synchronized void addTicket(Ticket ticket) {
        while(tickets.size() >= maxCapacity) {
            try{
                wait();
                System.out.println("TicketPool is full, Waiting to add ticket!!!");
            }catch (InterruptedException e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }

        }
        this.tickets.add(ticket);
        notifyAll();
        System.out.println(Thread.currentThread().getName() + ": Ticket added to the pool. Current size is"+tickets.size() + "\n");
        FileManager.saveLogs("TICKET ADDED BY THE VENDOR", ticket, tickets);
        if(tickets.size() == maxCapacity){

            System.exit(0);
        }
    }


    /**
     * Allows a customer to buy a ticket from the ticket pool, waiting if the pool is empty.
     *
     * <p>This synchronized method removes and returns a ticket from the pool. If the pool is empty,
     * the thread will wait until a ticket becomes available. Once a ticket is successfully bought,
     * the method notifies other threads and logs the transaction. The method ensures thread-safe access
     * to the ticket pool when multiple customers attempt to purchase tickets concurrently.</p>
     *
     * @return the {@code Ticket} purchased by the customer.
     *
     * @throws RuntimeException if the thread is interrupted while waiting to buy a ticket.
     */
    public synchronized Ticket buyTicket() {
        while(tickets.isEmpty()){
            try{
                wait(20);
                System.out.println("Customer is waiting!!");
            }catch (InterruptedException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        Ticket ticket = tickets.removeFirst();
        notifyAll();
        System.out.println(Thread.currentThread().getName() + ": Ticket bought! Current size is"+tickets.size() + "\n");
        FileManager.saveLogs("TICKET PURCHASED BY THE CUSTOMER", ticket, tickets);

        return ticket;
    }


}
