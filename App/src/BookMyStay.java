import java.util.HashMap;
abstract class Room {
    protected int beds;
    protected int size;
    protected double price;
    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }
    public int getBeds() {
        return beds;
    }
    public int getSize() {
        return size;
    }
    public double getPrice() {
        return price;
    }
}
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}
class RoomInventory {
    private HashMap<String, Integer> inventory;
    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}
class SearchService {
    public static void displayRoom(String type, Room room, int available) {
        if (available > 0) {
            System.out.println(type + ":");
            System.out.println("Beds: " + room.getBeds());
            System.out.println("Size: " + room.getSize() + " sqft");
            System.out.println("Price per night: " + room.getPrice());
            System.out.println("Available: " + available + "\n");
        }
    }
}
public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("Room Search\n");
        RoomInventory inventory = new RoomInventory();
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        SearchService.displayRoom(
                "Single Room",
                single,
                inventory.getAvailability("Single Room")
        );
        SearchService.displayRoom(
                "Double Room",
                doubleRoom,
                inventory.getAvailability("Double Room")
        );
        SearchService.displayRoom(
                "Suite Room",
                suite,
                inventory.getAvailability("Suite Room")
        );
    }
}