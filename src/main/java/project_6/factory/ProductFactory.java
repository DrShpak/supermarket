package project_6.factory;

import project_6.products.Alcohol;
import project_6.products.Cigarettes;
import project_6.products.Food;
import project_6.products.Product;

public class ProductFactory {

    public Product getProduct(ProductTypes type, String[] param) {
        return switch (type) {
            case FOOD -> new Food(param);
            case ALCOHOL -> new Alcohol(param);
            case CIGARETTES -> new Cigarettes(param);
            default -> null;
        };
    }
}
