import java.util.*;
class Reservation{
    private String guestName;
    private String roomType;
    public Reservation(String guestName,String roomType){
        this.guestName=guestName;
        this.roomType=roomType;
    }
    public String getGuestName(){return guestName;}
    public String getRoomType(){return roomType;}
}
class BookingRequestQueue{
    private Queue<Reservation>requestQueue;
    public BookingRequestQueue(){requestQueue=new LinkedList<>();}
    public void addRequest(Reservation reservation){requestQueue.offer(reservation);}
    public Reservation getNextRequest(){return requestQueue.poll();}
    public boolean hasPendingRequests(){return !requestQueue.isEmpty();}
}
class RoomInventory{
    private Map<String,Integer>inventory;
    public RoomInventory(){
        inventory=new HashMap<>();
        inventory.put("Single",2);
        inventory.put("Double",1);
        inventory.put("Suite",1);
    }
    public int getAvailability(String roomType){
        return inventory.getOrDefault(roomType,0);
    }
    public void decrement(String roomType){
        inventory.put(roomType,inventory.get(roomType)-1);
    }
}
class RoomAllocationService{
    private Set<String>allocatedRoomIds;
    private Map<String,Set<String>>assignedRoomsByType;
    public RoomAllocationService(){
        allocatedRoomIds=new HashSet<>();
        assignedRoomsByType=new HashMap<>();
    }
    public void allocateRoom(Reservation reservation,RoomInventory inventory){
        String roomType=reservation.getRoomType();
        if(inventory.getAvailability(roomType)<=0){
            System.out.println("No rooms available for "+roomType);
            return;
        }
        String roomId=generateRoomId(roomType);
        allocatedRoomIds.add(roomId);
        assignedRoomsByType
                .computeIfAbsent(roomType,k->new HashSet<>())
                .add(roomId);
        inventory.decrement(roomType);
        System.out.println("Booking confirmed for Guest: "+reservation.getGuestName()+", Room ID: "+roomId);
    }
    private String generateRoomId(String roomType){
        int nextId=assignedRoomsByType
                .getOrDefault(roomType,new HashSet<>())
                .size()+1;
        return roomType+"-"+nextId;
    }
}
public class BookMyStay{
    public static void main(String[]args){
        System.out.println("Room Allocation Processing");
        BookingRequestQueue bookingQueue=new BookingRequestQueue();
        bookingQueue.addRequest(new Reservation("Abhi","Single"));
        bookingQueue.addRequest(new Reservation("Subha","Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi","Suite"));
        RoomInventory inventory=new RoomInventory();
        RoomAllocationService allocationService=new RoomAllocationService();
        while(bookingQueue.hasPendingRequests()){
            Reservation reservation=bookingQueue.getNextRequest();
            allocationService.allocateRoom(reservation,inventory);
        }
    }
}