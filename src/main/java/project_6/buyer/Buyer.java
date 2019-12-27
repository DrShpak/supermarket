package project_6.buyer;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import project_6.misc.Position;
import project_6.products.Alcohol;
import project_6.products.Cigarettes;
import project_6.products.Product;

import java.util.ArrayList;

@DatabaseTable(tableName = "buyer")
public class Buyer {
    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private double capital;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ArrayList<Position> shoppingList;
    @DatabaseField
    private int age;
    @DatabaseField
    private int calories;

    @SuppressWarnings({"unused", "SpellCheckingInspection"}) //ormlite requires this empty constructor
    public Buyer() {
    }

    public Buyer(String name, double capital, int age, int calories) {
        this.name = name;
        this.capital = capital;
        this.age = age;
        this.calories = calories;
        this.shoppingList = new ArrayList<>();
    }

    public <T extends Product> boolean isAdult(T product) {
        if (product.getClass().equals(Alcohol.class) || product.getClass().equals(Cigarettes.class)) {
            try {
                /*
                берем поле age и делаем его октрытым (по умолчанию он private)
                таким образом мы разрешаем работу с этим полем, на время проведения операции
                getDeclaredField вернет поле со всеми модификаторами (даже private and protected)
                 */
                var field = product.getClass().getDeclaredField("age");
                field.setAccessible(true); // открываем поле age
                var age = (int) field.get(product);
                if (this.age >= age)
                    return capital >= product.getSalePrice();
                else {
                    System.out.println("К сожалению, Вы не достигли нужного возраста!");
                    return false;
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                return false;
            }
        } else {
            return capital >= product.getSalePrice();
        }
    }

    public boolean canBuy(Product product) {
        if (this.calories >= product.getCalories())
            return true;
        System.out.println("Продукт " + product.getName() + " содержит слишком много калорий для Вас!");
        return false;
    }

    public void delItem(Position pos) {
        var currCount = shoppingList.get(getIndexPosition(pos)).getCount();
        shoppingList.get(getIndexPosition(pos)).setCount(currCount - 1);
    }

    public int getResidue(Position pos) {
        return shoppingList.get(getIndexPosition(pos)).getCount();
    }

    private int getIndexPosition(Position pos) {
        for (int i = 0; i < shoppingList.size(); i++) {
            if (pos.equals(shoppingList.get(i)))
                return i;
        }
        return -1;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public ArrayList<Position> getShoppingList() {
        return shoppingList;
    }

    public void addPosition(Position position) {
        shoppingList.add(position);
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}