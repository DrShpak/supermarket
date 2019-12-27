package project_6.products;

public class Cigarettes extends Product {
    @SuppressWarnings("unused")
    private int age = 18;

    //конструктор
    @SuppressWarnings("unused")
    public Cigarettes(String name, int skuNumber, double weight,
                      double purchasePrice, double salePrice, int calories) {
        super(name, skuNumber, salePrice, calories);
    }

    public Cigarettes(String[] param) {
        this(param[0], Integer.parseInt(param[1]), Double.parseDouble(param[2]),
            Double.parseDouble(param[3]), Double.parseDouble(param[4]), Integer.parseInt(param[5]));
    }
}
