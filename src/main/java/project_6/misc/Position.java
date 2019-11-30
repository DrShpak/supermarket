package project_6.misc;

import project_6.products.Product;

import java.util.Date;
import java.util.Objects;

public class Position {
    private final Product product;
    private final Date endDate;
    private int count;

    public Position(Product product, Date endData, int count) {
        this.product = product;
        this.endDate = endData;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return
            Objects.equals(product, position.product) &&
            endDate.after(position.endDate);
    }
}
