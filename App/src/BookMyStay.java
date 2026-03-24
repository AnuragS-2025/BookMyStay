import java.io.*;
import java.util.*;

/**
 * InventoryData serves as a serializable container for the system state.
 * Implementing Serializable allows this object to be written to a file.
 */
class InventoryData implements Serializable {
    private static final long serialVersionUID = 1L;
    public Map<String, Integer> inventory = new HashMap<>();

    public InventoryData() {
        // Initial state if no file is found
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }
}

/**
 * PersistenceService handles the saving and loading of the InventoryData object.
 */
class PersistenceService {
    private static final String FILE_NAME = "inventory_state.ser";

    public void saveState(InventoryData data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(data);
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }

    public InventoryData loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return new InventoryData();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (InventoryData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading state, starting fresh: " + e.getMessage());
            return new InventoryData();
        }
    }
}

/**
 * Main application class for Use Case 12.
 * @author User
 * @version 12.0
 */
public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        System.out.println("System Recovery");

        PersistenceService persistence = new PersistenceService();

        // 1. Recover state from file or start fresh
        InventoryData currentData = persistence.loadState();

        // 2. Display current state (Recovered or Initial)
        System.out.println("\nCurrent Inventory:");
        currentData.inventory.forEach((type, count) ->
                System.out.println(type + ": " + count));

        // 3. Persist state for the next restart
        persistence.saveState(currentData);
    }
}