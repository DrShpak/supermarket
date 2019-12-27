package project_6.tradingFloor;

import project_6.buyer.Buyer;
import project_6.misc.Position;
import project_6.misc.Storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TradingFloorConsole {
    @SuppressWarnings("FieldCanBeLocal")
    private final int diff = (int) 8.76e7; //сутки в мс
    private final Scanner input = new Scanner(System.in);

    private final Buyer buyer;

    TradingFloorConsole(Buyer buyer) {
        this.buyer = buyer;
    }

    public void start() {
        //noinspection StatementWithEmptyBody
        while (workPeriod()) ;
    }

    @SuppressWarnings("SameReturnValue")
    private boolean workPeriod() {
        Date currentDate = new Date(); //аргументом конструткора является кол-во мс прошедших с 1970г.
        Date endDate = new Date(currentDate.getTime() + 10 * diff);
        while (endDate.compareTo(currentDate) > 0) {
            System.out.println("\nТекущая дата: " + currentDate.toString());
            System.out.println("\nСделайте выбор: ");
            System.out.println("\n1. Показать список доступных товаров и сделать покупку.");
            System.out.println("\n2. Сделайте выбор: Сделать скидку на товар.");
            int code = input.nextInt();
            input.nextLine(); //skip line
            switch (code) {
                case 1:
                    makeBuy();
                    break;
                case 2:
                    makeSale();
                    break;
                default:
                    break;
            }
            currentDate.setTime(currentDate.getTime() + 2 * diff);
        }
        return false;
    }

    private void makeSale() {
        System.out.println("Список товаров с кратким описанием:");
        printProducts();
        System.out.println("Введите скидку на товар в процентах(!!): ");
        var sale = input.nextInt();
        input.nextLine(); //skip
        changePrice(sale, parseProducts());
    }

    private void changePrice(int sale, List<Position> positions) {
        Storage.getPositions().
            stream().
            filter(positions::contains).
            forEach(x -> setSale(x, sale));
    }

    private static void setSale(Position pos, int sale) {
//        var product = Storage.getProducts().get(skuNumber);
        pos.getProduct().getSalePrices().addLast(pos.getProduct().getSalePrice());
        pos.getProduct().setSalePrice(pos.getProduct().getSalePrice() * (100 - sale) / 100);
//        product.getPrices().addLast(product.getPrice());
//        product.setPrice(product.getPrice() * (100 - sale) / 100);
    }

    private void makeBuy() {
        System.out.println("Доступные товары на складе: ");
        //выводим список доступных товаров
        printProducts();
        System.out.println("\nТовары, которые есть в Вашем спсике покупок: ");
        int[] index = {0};
        buyer.getShoppingList().
            forEach(pos -> System.out.println(++index[0] + ". "
                + "название: " + pos.getProduct().getName()
                + "; количество: " + pos.getCount()));
        System.out.println();
        System.out.println("Ваш капитал: " + buyer.getCapital() + " rub.");
        System.out.println("Осталось употребить калорий в сутки: " + buyer.getCalories());
        buy(buyer, parseProducts()); //покупаем
    }

    private void buy(Buyer buyer, List<Position> positions) {
        for (Position pos : positions) {
            if (!buyer.getShoppingList().contains(pos)) {
                System.out.println("Товара " + pos.getProduct().getName() + " нет в Вашем списке покупок!");
            } else {
                if (buyer.isAdult(pos.getProduct()) && buyer.canBuy(pos.getProduct()) && Storage.getResidue(pos) > 0) {
                    if (buyer.getResidue(pos) - 1 > 0) {
                        buyer.delItem(pos);
                    } else {
                        buyer.getShoppingList().remove(pos);
                    }
                    buyer.setCalories(buyer.getCalories() - pos.getProduct().getCalories());
                    buyer.setCapital(buyer.getCapital() - pos.getProduct().getSalePrice());
                    Storage.deleteItem(pos);
                    if (Storage.getResidue(pos) <= 0) {
                        Storage.removeProduct(pos);
                    }
                    System.out.println("Продукт " + pos.getProduct().getName() + " успешно приобретен!\n");
                } else {
                    System.out.println("Данный покупатель не может позволить себе продукт " + pos.getProduct().getName());
//                    System.out.println("Ваш капитал = " + buyer.getCapital() + "; Цена продукта " + product.getName() + " = " + product.getPrice());
                }
            }
        }
    }

    /**
     * @return продукты, которые нужны покупателю
     */
    private ArrayList<Position> parseProducts() {
        System.out.println("Сделайте свой выбор (один или несколько через запятую): ");
        var choose = input.nextLine();

        //парсим продукты: удаляем пробелы и разделяем строку по запятой
        //и получаем строковый массив из артикулов продуктов
        var numbers = choose.replaceAll("\\s", "").split(",");
        var positions = new ArrayList<Position>();
        for (String num : numbers) {
            positions.add(Storage.getPositions().get(Integer.parseInt(num) - 1));
        }
        return positions;
    }

    private void printProducts() {
        System.out.println("Список товаров с кратким описанием:");
        int[] index = {0};
        Storage.getPositions().
            forEach(x -> System.out.println(++index[0] + ". "
                + "артикул: " + x.getProduct().getSkuNumber() + "; "
                + x.getProduct().getName() + "; "
                + x.getProduct().getSalePrice() + " rub.; "
                + "содержит калорий: " + x.getProduct().getCalories() + "; "
                + "срок годности: " + x.getEndDate() + "; "
                + "количество штук на складе: " + x.getCount()
            ));
    }
}
