package project_6;

import project_6.buyer.Buyer;
//import project_6.tradingFloor.TradingFloor;
import project_6.misc.Position;
import project_6.products.Cigarettes;
import project_6.tradingFloor.TradingFloor;
import project_6.tradingFloor.TradingFloorBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

class Main {

    public static void main(String[] args) throws IOException {
        Buyer buyer1 = new Buyer(1000, 20, new ArrayList<>(), 1000);
        buyer1.addPosition(new Position(new Cigarettes("LD", 100, 150.0, 20.0, 300.0, 20),
                getDate(10), 10));
//        buyer1.addProductToList(new Food(30d, "bread", 200, new Date(), 20d, 1));
//        buyer1.addProductToList(new Food(130d, "milk", 101, new Date(), 20d, 1));

        TradingFloor floor = new TradingFloorBuilder().setDefaultStuffInStorage().build(buyer1);
        floor.start();
    }

    private static Date getDate(int days) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date()); //устанавливаем дату, с которой будет производить операции
        instance.add(Calendar.DAY_OF_MONTH, days);// прибавляем 3 дня к установленной дате
        return instance.getTime(); // получаем измененную дату
    }
}
