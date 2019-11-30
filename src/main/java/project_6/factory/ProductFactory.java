package project_6.factory;

import project_6.products.Alcohol;
import project_6.products.Cigarettes;
import project_6.products.Food;
import project_6.products.Product;

public class ProductFactory {

    public Product getProduct(ProductTypes type, String[] param) {
        Product toReturn = null;
        switch (type) {
            case FOOD:
                toReturn = new Food(param);
                break;
            case ALCOHOL:
                toReturn = new Alcohol(param);
                break;
            case CIGARETTES:
                toReturn = new Cigarettes(param);
                break;
            default:
        }
        return toReturn;
    }
}
