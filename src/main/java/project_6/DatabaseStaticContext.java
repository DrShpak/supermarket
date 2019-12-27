package project_6;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import project_6.buyer.Buyer;
import project_6.misc.Position;
import project_6.products.Alcohol;
import project_6.products.Cigarettes;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseStaticContext {
    private static final JdbcConnectionSource connectionSource;

    public static final Dao<Buyer, String> buyerDAO;

    static {
        JdbcConnectionSource connectionSourceTmp = null;
        Dao<Buyer, String> buyerDAOTmp = null;
        try {
            //noinspection SpellCheckingInspection
            connectionSourceTmp = new JdbcConnectionSource("jdbc:sqlite:sample.db");
            buyerDAOTmp =
                    DaoManager.createDao(connectionSourceTmp, Buyer.class);
            if (!buyerDAOTmp.isTableExists()) simpleExample(connectionSourceTmp, buyerDAOTmp);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        connectionSource = connectionSourceTmp;
        buyerDAO = buyerDAOTmp;
    }

    private static void simpleExample(ConnectionSource connectionSource, Dao<Buyer, String> buyerDAO) throws SQLException {
        TableUtils.createTable(connectionSource, Buyer.class);
        var buyer1 = new Buyer("Anton", 1000, 17, 1000);
        buyer1.addPosition(
                new Position(
                        new Cigarettes("LD", 100, 150.0, 20.0, 300.0, 20),
                        Main.getDate(30),
                        10
                )

        );
        buyer1.addPosition(
                new Position(
                        new Alcohol("beer", 200, 0.2, 105, 200, 180),
                        Main.getDate(30),
                        5
                )
        );
        buyerDAO.create(buyer1);
    }

    public static void close() {
        try {
            connectionSource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
