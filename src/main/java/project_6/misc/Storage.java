package project_6.misc;

import java.util.*;

public class Storage {
    private static final List<Position> positions = new ArrayList<>();

    public static void addPosition(Position pos) {
        if (!positions.contains(pos)) {
            positions.add(pos);
        }
    }

    private static Position getSurePos(Position pos) {
        var surePosOpt = positions.stream().filter(x -> x.equals(pos)).findFirst();
        if (surePosOpt.isEmpty()) {
            return null;
        }
        return surePosOpt.get();
    }

    public static void deleteItem(Position pos) {
        var surePos = getSurePos(pos);
        if (surePos != null && surePos.getCount() > 0) {
            surePos.setCount(surePos.getCount() - 1);
        }
    }

    public static int getResidue(Position pos) {
        var surePos = getSurePos(pos);
        return surePos != null ? surePos.getCount() : -1;
    }

    public static void removeProduct(Position pos) {
        positions.remove(getSurePos(pos));
    }

    public static List<Position> getPositions() {
        return positions;
    }

    @SuppressWarnings("unused")
    public boolean isEmpty() {
        return positions.isEmpty();
    }
}