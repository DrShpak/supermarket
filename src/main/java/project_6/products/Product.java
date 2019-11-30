package project_6.products;

import project_6.facades.IProductProvider;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public abstract class Product implements IProductProvider {
    private final String name;
    private final double weight;
    private final int calories;
    private final Deque<Double> salePrices = new ArrayDeque<>();
    private int skuNumber;
    private final double purchasePrice;
    private double salePrice;

    @SuppressWarnings("WeakerAccess")
    protected Product(String name, int skuNumber, double weight,
                      double purchasePrice, double salePrice, int calories) {
        this.name = name;
        this.skuNumber = skuNumber;
        this.weight = weight;
        this.purchasePrice = purchasePrice;
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

    public void setSkuNumber(int skuNumber) {
        this.skuNumber = skuNumber;
    }

    public double getWeight() {
        return weight;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public int getCalories() {
        return calories;
    }

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
