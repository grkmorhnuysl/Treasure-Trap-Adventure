package example;

import java.util.Random;

public enum RoomAttribute {
    EMPTY, TREASURE, TRAP;

    private static final Random randomAttributeGetter = new Random();

    public static RoomAttribute getRandomAttribute() {
        RoomAttribute[] roomAttributes = values();
        return roomAttributes[randomAttributeGetter.nextInt(roomAttributes.length)];
    }
}
