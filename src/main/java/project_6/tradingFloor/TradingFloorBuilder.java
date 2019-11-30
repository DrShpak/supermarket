package project_6.tradingFloor;

import project_6.buyer.Buyer;
import project_6.factory.ProductFactory;
import project_6.factory.ProductTypes;
import project_6.misc.Position;
import project_6.misc.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TradingFloorBuilder {
    private final ProductFactory factory = new ProductFactory();

    //билдер, сразу махом добалвяем кучу продуктов
    public TradingFloorBuilder setDefaultStuffInStorage() throws IOException {
        final var source = (ArrayList<String>) Files.readAllLines(Paths.get("/Users/test/Documents/Java programmes/supermarket/src/main/java/project_6/input/defaultProducts"));
        for (String line : source) {
            Storage.addPosition(createPosition(line));
        }
        return this;
    }

    private Position createPosition(String line) {
        final var strArr = line.split(",");
        return new Position(factory.getProduct(getType(line), getSubString(strArr)), getData(strArr), 10);
    }

    private ProductTypes getType(String line) {
        if (line.contains("food")) {
            return ProductTypes.FOOD;
        } else if (line.contains("alcohol")) {
            return ProductTypes.ALCOHOL;
        } else if (line.contains("cigarettes")) {
            return ProductTypes.CIGARETTES;
        } else if (line.contains("technique")) {
            return ProductTypes.TECHNIQUE;
        }
        return null;
    }

    private String[] getSubString(String[] line) {
        final var dictionary = new String[] {"name", "sku", "weight", "purchasePrice", "salePrice", "calories"};
        var temp = new String[dictionary.length];
        int index = 0;
        for (String dict : dictionary) {
            for (String element : line) {
                if (element.contains(dict)) {
                    temp[index] = element.substring(element.indexOf(": ") + 1, element.indexOf("}")).trim();
                    index++;
                    break;
                }
            }
        }
        return temp;
    }

    private Date getData(String[] arr) {
        var data = new Date();

        for (String element : arr) {
            if (element.contains("endData")) {
                Calendar instance = Calendar.getInstance();
                instance.setTime(data); //устанавливаем дату, с которой будет производить операции
                final var substring = element.substring(element.indexOf(": ") + 1, element.indexOf("}")).trim();
                instance.add(Calendar.DAY_OF_MONTH, Integer.parseInt(substring));// прибавляем 3 дня к установленной дате
                return instance.getTime(); // получаем измененную дату
            }
        }
        return new Date();
    }

    public TradingFloor build(Buyer buyer) {
        return new TradingFloor(buyer);
    }
}