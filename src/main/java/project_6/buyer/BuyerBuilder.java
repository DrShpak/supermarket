package project_6.buyer;

import project_6.misc.Position;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class BuyerBuilder {

    private double capital;
    private final List<Position> shoppingList;
    private int age;

    public BuyerBuilder(List<Position> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public BuyerBuilder setCapital(double capital) {
        this.capital = capital;
        return this;
    }

    public BuyerBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    private BuyerBuilder addPosition(Position pos) {
        if (shoppingList.contains(pos)) {
            var index = getIndex(pos);
        } else {
            shoppingList.add(pos);
        }
        return this;
    }

    private int getIndex(Position pos) {
        for (int i = 0; i < shoppingList.size(); i++) {
            if (shoppingList.get(i).equals(pos)) return i;
        }
        return -1;
    }

    public Buyer build() {
        return new Buyer(capital, age, (ArrayList<Position>) shoppingList, 1000);
    }
}
