package project_6.products;

public class Alcohol extends Product {
    @SuppressWarnings("FieldCanBeLocal")
    private final int age = 18;

    @SuppressWarnings("WeakerAccess")
    public Alcohol(String name, int skuNumber, double weight,
                double purchasePrice, double salePrice, int calories) {
        super(name, skuNumber, weight, purchasePrice, salePrice, calories);
    }

    public Alcohol(String[] params) {
        this(params[0], Integer.parseInt(params[1]), Double.parseDouble(params[2]),
            Double.parseDouble(params[3]), Double.parseDouble(params[4]), Integer.parseInt(params[5]));
    }

    @SuppressWarnings("unused")
    public boolean isAdult(int age) {
        return age >= this.age;
    }
}
