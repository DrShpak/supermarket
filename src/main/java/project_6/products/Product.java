package project_6.products;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public abstract class Product implements Serializable {
    private final String name;
    private final int calories;
    private final Deque<Double> salePrices = new ArrayDeque<>();
    private final int skuNumber;
    private double salePrice;

    @SuppressWarnings("WeakerAccess")
    protected Product(String name, int skuNumber,
                      double salePrice, int calories) {
        this.name = name;
        this.skuNumber = skuNumber;
        this.salePrice = salePrice;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public ArrayDeque<Double> getSalePrices() {
        return (ArrayDeque<Double>) salePrices;
    }

    public int getSkuNumber() {
        return skuNumber;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public int getCalories() {
        return calories;
    }

    //продукты равны если равны их имена и класс (Food, Alcohol etc.)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                this.getClass().equals(product.getClass());
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
}
