import java.util.*;

/**
 * ==========================================
 * RESERVATION & DOMAIN MODELS
 * ==========================================
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomType;
    }
}

/**
 * ==========================================
 * ALLOCATION & INVENTORY SERVICE (Use Case 6)
 * ==========================================
 */
class BookingService {
    // Inventory: Room Type -> Count
    private Map<String, Integer> inventory = new HashMap<>();

    // Allocated Rooms: Room Type -> Set of unique Room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public BookingService() {
        // Initial setup
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);

        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    public void processQueue(Queue<Reservation> queue) {
        System.out.println("--- Starting Allocation Process ---\n");

        while (!queue.isEmpty()) {
            Reservation request = queue.poll(); // Dequeue (FIFO)
            String type = request.getRoomType();

            if (inventory.getOrDefault(type, 0) > 0) {
                // Generate Unique Room ID (e.g., Single-101)
                int currentCount = inventory.get(type);
                String roomId = type + "-" + (100 + currentCount);

                // Use Set to prevent Double Booking
                if (!allocatedRooms.get(type).contains(roomId)) {
                    allocatedRooms.get(type).add(roomId); // Add to Set
                    inventory.put(type, currentCount - 1); // Decrement Inventory

                    System.out.println("CONFIRMED: " + request.getGuestName() +
                            " assigned to Room " + roomId);
                }
            } else {
                System.out.println("REJECTED: No " + type + " rooms available for " + request.getGuestName());
            }
        }
        System.out.println("\n--- Allocation Complete ---");
    }

    public void displayFinalStatus() {
        System.out.println("\nFinal Inventory State:");
        inventory.forEach((k, v) -> System.out.println(k + ": " + v + " left"));
    }
}

/**
 * ==========================================
 * MAIN APPLICATION
 * ==========================================
 */
public class UseCase6RoomAllocation {
    public static void main(String[] args) {
        // 1. Setup Requests (Use Case 5)
        Queue<Reservation> requestQueue = new LinkedList<>();
        requestQueue.add(new Reservation("Alice", "Single"));
        requestQueue.add(new Reservation("Bob", "Suite"));
        requestQueue.add(new Reservation("Charlie", "Double"));
        requestQueue.add(new Reservation("Diana", "Single"));
        requestQueue.add(new Reservation("Eve", "Suite"));
        requestQueue.add(new Reservation("Frank", "Suite")); // Should be rejected (only 2 suites)

        // 2. Initialize Service & Allocate (Use Case 6)
        BookingService service = new BookingService();
        service.processQueue(requestQueue);

        // 3. Show Result
        service.displayFinalStatus();
    }
}