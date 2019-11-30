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
//        if (shoppingList.containsKey(product.getSkuNumber())) {
//            addProduct(product.getSkuNumber());
//        } else {
//            shoppingList.put(product.getSkuNumber(), product);
//        }
//        return this;
    }

    private int getIndex(Position pos) {
        for (int i = 0; i < shoppingList.size(); i++) {
            if (shoppingList.get(i).equals(pos)) return i;
        }
        return -1;
    }

//    private void addProduct(int skuNumber) {
//        if (shoppingList.containsKey(skuNumber)) {
//            var pos = shoppingList.get(skuNumber);
//            pos.setAmount(pos.getAmount() + 1);
//        }
//    }

//    public BuyerBuilder addAll(Class<? extends Product> clazz, int size, Object... args) {
//        Product product; //объявление
//        try {
//            product = clazz.getConstructor //ищем в переданном классе конструктор с параметрами args
//                (
//                    //это стрим апи (stream API) апи для работы с последовтаельностями
//                    Arrays.stream(args).
//                        map(Object::getClass). //мэпим каждый аргумент в объект
//                        toArray(Class[]::new) // переводим в массив классов
//                ).
//                newInstance(args); //создаем сущность (экземпляр)
//        } catch (Exception ex) {
//            throw new IllegalArgumentException
//                (clazz + " has no public constructor with " + args.length + " args");
//        }
//
//        //создаем нужный продукт нужное количество раз
//        for (int i = 0; i < size; i++) {
//            addPosition(product);
//        }
//        return this;
//    }

    public Buyer build() {
        return new Buyer(capital, age, (ArrayList<Position>) shoppingList, 1000);
    }
}
