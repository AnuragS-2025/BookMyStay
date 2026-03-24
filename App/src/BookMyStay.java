import java.util.*;
import java.util.concurrent.*;

/**
 * Represents a booking request from a guest.
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
}

/**
 * Thread-safe Booking Processor using Synchronized blocks.
 */
class ConcurrentBookingProcessor {
    private final Map<String, Integer> inventory = new HashMap<>();
    private final Map<String, Integer> initialInventory = new HashMap<>();
    private final Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public void addInventory(String type, int count) {
        inventory.put(type, count);
        initialInventory.put(type, count);
        allocatedRooms.put(type, new HashSet<>());
    }

    /**
     * Synchronized method ensures that only one thread can process a booking at a time,
     * preventing race conditions and double-booking.
     */
    public synchronized void processBooking(Reservation request) {
        String type = request.getRoomType();
        int currentCount = inventory.getOrDefault(type, 0);

        if (currentCount > 0) {
            int roomNumber = initialInventory.get(type) - currentCount + 1;
            String roomID = type + "-" + roomNumber;

            if (allocatedRooms.get(type).add(roomID)) {
                inventory.put(type, currentCount - 1);
                System.out.println("Booking confirmed for Guest: " + request.getGuestName() +
                        ", Room ID: " + roomID);
            }
        }
    }

    public void displayRemainingInventory() {
        System.out.println("\nRemaining Inventory:");
        inventory.forEach((type, count) -> System.out.println(type + ": " + count));
    }
}

/**
 * Main application class for Use Case 11.
 * @author User
 * @version 11.0
 */
public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();
        processor.addInventory("Single", 5);
        processor.addInventory("Double", 3);
        processor.addInventory("Suite", 2);

        System.out.println("Concurrent Booking Simulation");

        // Use a ThreadPool to simulate multiple concurrent guests
        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<Reservation> requests = Arrays.asList(
                new Reservation("Abhi", "Single"),
                new Reservation("Vanmathi", "Double"),
                new Reservation("Kural", "Suite"),
                new Reservation("Subha", "Single")
        );

        // Submit each booking request as a separate task
        for (Reservation req : requests) {
            executor.execute(() -> processor.processBooking(req));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        processor.displayRemainingInventory();
    }
}