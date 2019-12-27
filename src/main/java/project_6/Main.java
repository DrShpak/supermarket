package project_6;

import project_6.tradingFloor.TradingFloorConsole;
import project_6.tradingFloor.TradingFloorBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

class Main {
    public static void main(String[] args) throws IOException, SQLException {
        var buyer1 = DatabaseStaticContext.buyerDAO.queryForId("Anton");
        TradingFloorConsole floor = new TradingFloorBuilder().setDefaultStuffInStorage().build(buyer1);
        floor.start();
        DatabaseStaticContext.close();
    }

    static Date getDate(@SuppressWarnings("SameParameterValue") int days) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date()); //устанавливаем дату, с которой будет производить операции
        instance.add(Calendar.DAY_OF_MONTH, days);// прибавляем 3 дня к установленной дате
        return instance.getTime(); // получаем измененную дату
    }
}
