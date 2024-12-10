package CLI;

import java.math.BigDecimal;

public class Vendor implements Runnable{
    private TicketPool ticketPool;
    private int totalTickets;
    private int ticketReleaseRate;

    /**
     * Constructs a {@code Vendor} object with the specified ticket pool, total tickets, and ticket release rate.
     *
     * <p>This constructor initializes the vendor with the ticket pool from which tickets will be added,
     * the total number of tickets the vendor is responsible for releasing, and the rate at which the vendor
     * will release tickets into the pool.</p>
     *
     * @param ticketPool the {@code TicketPool} from which the vendor will release tickets.
     * @param totalTickets the total number of tickets the vendor will release into the pool.
     * @param ticketReleaseRate the rate at which the vendor will release tickets (in seconds).
     */
    public Vendor(TicketPool ticketPool, int totalTickets, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    /**
     * Simulates the vendor releasing tickets into the ticket pool at the specified release rate.
     *
     * <p>This method runs in a separate thread, where the vendor creates and adds tickets to the pool.
     * It creates a new ticket for each iteration, with a unique ID and a fixed event name and price. The vendor
     * waits for the specified release rate (in seconds) before adding the next ticket to the pool. The loop continues
     * until all the tickets have been released into the pool.</p>
     *
     * @throws RuntimeException if the thread is interrupted while waiting to release a ticket.
     */
    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            Ticket ticket = new Ticket(i, "Event", new BigDecimal(1000));
            ticketPool.addTicket(ticket);

            try{
                //System.out.println("Enter 'S' to stop the simulation: ");
                Thread.sleep(ticketReleaseRate * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
