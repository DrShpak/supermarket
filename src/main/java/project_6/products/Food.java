package project_6.products;

public class Food extends Product {

    @SuppressWarnings({"WeakerAccess", "unused"})
    public Food(String name, int skuNumber, double weight,
                double purchasePrice, double salePrice, int calories) {
        super(name, skuNumber, salePrice, calories);
    }

    //для фабрики конструктор, он вызывает основной конструктор (он выше)
    public Food(String[] params) {
        this(params[0], Integer.parseInt(params[1]), Double.parseDouble(params[2]),
            Double.parseDouble(params[3]), Double.parseDouble(params[4]), Integer.parseInt(params[5]));
    }
}