package CLI;

import java.math.BigDecimal;

public class Ticket {
    private final int TicketID;
    private final String eventName;
    private final BigDecimal price;

    /**
     * Constructs a {@code Ticket} object with the specified ID, event name, and price.
     *
     * <p>This constructor initializes a ticket with a unique ticket ID, the name of the event
     * associated with the ticket, and the price of the ticket.</p>
     *
     * @param TicketID the unique identifier for the ticket.
     * @param eventName the name of the event for which the ticket is issued.
     * @param price the price of the ticket.
     */
    public Ticket(int TicketID, String eventName, BigDecimal price) {
        this.TicketID = TicketID;
        this.eventName = eventName;
        this.price = price;
    }

    /**
     * Retrieves the unique identifier of the ticket.
     *
     * @return the unique ticket ID.
     */
    public int getTicketID() {
        return TicketID;
    }

    /**
     * Retrieves the name of the event associated with the ticket.
     *
     * @return the name of the event.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Retrieves the price of the ticket.
     *
     * @return the price of the ticket as a {@code BigDecimal}.
     */
    public BigDecimal getPrice() {
        return price;
    }

}
