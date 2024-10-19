package example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TreasureHuntGame {
    private LinkedRoomList map;
    private Stack<Room> moveStack;
    private Stack<Room> treasureStack;
    private Set<Integer> visitedRooms;

    public TreasureHuntGame() {
        this.map = initGameRooms();
        this.moveStack = new Stack<>();
        this.treasureStack = new Stack<>();
        this.visitedRooms = new HashSet<>();
    }

    public static void main(String[] args) {
        TreasureHuntGame game = new TreasureHuntGame();
        game.startGame((new Random()).nextInt(10) + 1);
    }

    private void startGame(int startIndex) {
        Room currentRoom = map.getRoomByPosition(startIndex);
        moveStack.push(currentRoom);
        visitedRooms.add(currentRoom.getId());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("You are in " + currentRoom);
            List<Room> doors = currentRoom.getDoors();
            System.out.println("Room attribute: " + currentRoom.getRoomAttribute());

            if (currentRoom.getRoomAttribute() == RoomAttribute.TREASURE) {
                System.out.println("You found a treasure!");
                treasureStack.push(currentRoom);
                displayTreasures();
                currentRoom.setRoomAttribute(RoomAttribute.EMPTY);
            } else if (currentRoom.getRoomAttribute() == RoomAttribute.TRAP) {
                System.out.println("You hit a trap! Rolling dice...");
                if (!rollDice()) {
                    System.out.println("You failed to overcome the trap. Going back to the previous room.");
                    moveStack.pop();
                    if (moveStack.isEmpty()) {
                        System.out.println("No more moves left. Game over.");
                        break;
                    }
                    currentRoom = moveStack.peek();
                    continue;
                } else {
                    System.out.println("You overcame the trap!");
                    currentRoom.setRoomAttribute(RoomAttribute.EMPTY);
                }
            }

            if (doors.isEmpty() && visitedRooms.size() == 10) {
                System.out.println("No more doors to go through and all rooms visited. Game over.");
                break;
            }

            System.out.println("Doors lead to rooms: " + getDoorIds(doors));
            System.out.print("Enter the ID of the room you want to go to: ");
            int nextRoomId = scanner.nextInt();

            Room nextRoom = map.getRoomByPosition(nextRoomId);
            if (nextRoom == null || !doors.contains(nextRoom)) {
                System.out.println("Invalid room ID. Please choose a valid door.");
            } else {
                moveStack.push(nextRoom);
                visitedRooms.add(nextRoom.getId());
                currentRoom = nextRoom;
            }

            if (treasureStack.size() == 10 || visitedRooms.size() == 10) {
                System.out.println("All treasures collected or all rooms visited. Game over.");
                break;
            }
        }

        scanner.close();
    }

    private boolean rollDice() {
        Random random = new Random();
        int diceRoll = random.nextInt(6) + 1;
        System.out.println("Rolled a " + diceRoll);
        return diceRoll % 2 == 0;
    }

    private void displayTreasures() {
        System.out.println("Collected treasures:");
        treasureStack.display();
    }

    private String getDoorIds(List<Room> doors) {
        return doors.stream()
                .map(Room::getId)
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    private LinkedRoomList initGameRooms() {
        BufferedReader reader;
        LinkedRoomList map = getMap();
        try {
            reader = new BufferedReader(new FileReader("gamemap.txt"));
            int fileIndex = 0;
            String line = reader.readLine();

            while (line != null) {
                fileIndex++;
                Room roomToUpdateDoors = map.getRoomByPosition(fileIndex);
                int[] doorNumbers = Arrays.stream(line.split(","))
                                          .map(String::trim)
                                          .mapToInt(Integer::parseInt)
                                          .toArray();
                setDoorToRoom(map, roomToUpdateDoors, doorNumbers);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing file: " + e.getMessage());
        }
        return map;
    }

    private void setDoorToRoom(LinkedRoomList map, Room room, int[] doorNumbers) {
        for (int doorNumber : doorNumbers) {
            Room doorToRoom = map.getRoomByPosition(doorNumber);
            if (doorToRoom != null) {
                room.insertDoor(doorToRoom);
            }
        }
    }

    private LinkedRoomList getMap() {
        LinkedRoomList list = new LinkedRoomList();
        for (int i = 1; i <= 10; i++) {
            list.insert(i, RoomAttribute.getRandomAttribute());
        }
        return list;
    }
}
