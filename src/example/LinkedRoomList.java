package example;

public class LinkedRoomList {
    private Room head;

    public LinkedRoomList insert(Integer id, RoomAttribute attribute) {
        Room room = new Room(id, attribute);

        if (this.head == null) {
            this.head = room;
        } else {
            Room last = this.head;
            while (last.getNext() != null) {
                last = last.getNext();
            }
            last.setNext(room);
        }
        return this;
    }

    public Room getRoomByPosition(int key) {
        Room currentRoom = this.head;
        while (currentRoom != null && !currentRoom.getId().equals(key)) {
            currentRoom = currentRoom.getNext();
        }
        return currentRoom;
    }

    public Room getHead() {
        return head;
    }

    public void setHead(Room head) {
        this.head = head;
    }
}
