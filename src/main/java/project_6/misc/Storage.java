package project_6.misc;

import java.util.*;

public class Storage {
    private static final List<Position> positions = new ArrayList<>();

    public static void addPosition(Position pos) {
        if (!positions.contains(pos)) {
            positions.add(pos);
        }
    }

    public static void deleteItem(Position pos) {
        if (pos.getCount() > 1) {
            pos.setCount(pos.getCount() - 1);
        }
    }

    public static int getResidue(Position pos) {
        return pos.getCount();
    }

    public static void removeProduct(Position pos) {
        positions.remove(pos);
    }

    public static List<Position> getPositions() {
        return positions;
    }

    @SuppressWarnings("unused")
    public boolean isEmpty() {
        return positions.isEmpty();
    }
}