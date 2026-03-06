import java.util.*;

/**
 * ==========================================
 * RESERVATION MODEL (Use Case 5)
 * ==========================================
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room Type: " + roomType;
    }
}

/**
 * ==========================================
 * DOMAIN MODEL (From Use Case 2)
 * ==========================================
 */
abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds + " | Size: " + squareFeet + " sqft | Price: " + pricePerNight);
    }
}

class SingleRoom extends Room { public SingleRoom() { super(1, 250, 1500.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super(2, 400, 2500.0); } }
class SuiteRoom extends Room { public SuiteRoom() { super(3, 750, 5000.0); } }

/**
 * ==========================================
 * MAIN APPLICATION - BOOKING QUEUE
 * ==========================================
 */
public class UseCase5BookingQueue {
    public static void main(String[] args) {
        System.out.println("Hotel Booking System - Request Intake\n");

        // 1. Initialize the Booking Queue (FIFO)
        // LinkedList implements the Queue interface in Java
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // 2. Guest submits booking requests (Arrival Order)
        System.out.println("Receiving Booking Requests...");
        bookingQueue.add(new Reservation("Alice", "Single"));
        bookingQueue.add(new Reservation("Bob", "Suite"));
        bookingQueue.add(new Reservation("Charlie", "Double"));
        bookingQueue.add(new Reservation("Diana", "Single"));

        // 3. Display the Queue Status
        // Key Requirement: Preserve order without modifying inventory yet
        System.out.println("\nCurrent Booking Request Queue (Total: " + bookingQueue.size() + "):");

        int position = 1;
        for (Reservation request : bookingQueue) {
            System.out.println(position + ". " + request);
            position++;
        }

        System.out.println("\nStatus: Requests queued and waiting for processing.");
        System.out.println("Fairness: First-Come-First-Served (FIFO) order preserved.");
    }
}