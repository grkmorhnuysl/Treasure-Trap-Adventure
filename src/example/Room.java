package example;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private Integer id;
    private RoomAttribute roomAttribute;
    private List<Room> doors;
    private Room next;

    public Room(Integer id, RoomAttribute attribute) {
        this.id = id;
        this.roomAttribute = attribute;
        this.doors = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public RoomAttribute getRoomAttribute() {
        return roomAttribute;
    }

    public void setRoomAttribute(RoomAttribute roomAttribute) {
        this.roomAttribute = roomAttribute;
    }

    public List<Room> getDoors() {
        return doors;
    }

    public void insertDoor(Room door) {
        doors.add(door);
    }

    public Room getNext() {
        return next;
    }

    public void setNext(Room next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Room{id=" + id + ", attribute=" + roomAttribute + "}";
    }
}
