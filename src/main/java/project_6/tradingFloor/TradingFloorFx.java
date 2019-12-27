package project_6.tradingFloor;

import project_6.buyer.Buyer;
import project_6.misc.Position;
import project_6.misc.Storage;

import java.util.List;

public class TradingFloorFx {
    TradingFloorFx() {
    }

    public void changePrice(int sale, int code) {
        Storage.getPositions().
            stream().
            filter(x -> x.getProduct().getSkuNumber() == code).
            forEach(x -> setSale(x, sale));
    }

    private static void setSale(Position pos, int sale) {
        pos.getProduct().getSalePrices().addLast(pos.getProduct().getSalePrice());
        pos.getProduct().setSalePrice(pos.getProduct().getSalePrice() * (100 - sale) / 100);
    }

    public void buy(Buyer buyer, List<Position> positions) {
        for (Position pos : positions) {
            if (!buyer.getShoppingList().contains(pos) || buyer.getResidue(pos) < 1) {
                System.out.println("Товара " + pos.getProduct().getName() + " нет в Вашем списке покупок!");
            } else {
                if (buyer.isAdult(pos.getProduct()) && buyer.canBuy(pos.getProduct()) && Storage.getResidue(pos) > 0) {
                    if (buyer.getResidue(pos)> 0) {
                        buyer.delItem(pos);
                    }
                    buyer.setCalories(buyer.getCalories() - pos.getProduct().getCalories());
                    buyer.setCapital(buyer.getCapital() - pos.getProduct().getSalePrice());
                    Storage.deleteItem(pos);
                    System.out.println("Продукт " + pos.getProduct().getName() + " успешно приобретен!\n");
                } else {
                    System.out.println("Данный покупатель не может позволить себе продукт " + pos.getProduct().getName());
                }
            }
        }
    }

}
