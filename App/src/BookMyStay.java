import java.util.*;
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {

    private Set<String> availableRoomTypes;

    public RoomInventory() {
        availableRoomTypes = new HashSet<>();
        availableRoomTypes.add("Single");
        availableRoomTypes.add("Double");
        availableRoomTypes.add("Suite");
    }

    public boolean isValidRoomType(String roomType) {
        return availableRoomTypes.contains(roomType);
    }
}
class ReservationValidator {

    public void validate(
            String guestName,
            String roomType,
            RoomInventory inventory
    ) throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}

class BookingRequestQueue {

    public void addRequest(String guestName, String roomType) {
        System.out.println("Booking request accepted for " + guestName +
                " (" + roomType + ")");
    }
}
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // User input
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Validate input
            validator.validate(guestName, roomType, inventory);

            // If valid → process request
            bookingQueue.addRequest(guestName, roomType);

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}