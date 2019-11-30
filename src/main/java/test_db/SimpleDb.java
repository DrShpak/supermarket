package test_db;

import java.sql.*;

public class SimpleDb {

    public static void main(String[] args) {
        SimpleDb m = new SimpleDb();
        m.testDatabase();
    }

    private void testDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/contactdb";
            String login = "postgres";
            String password = "2000diman";
            try (Connection con = DriverManager.getConnection(url, login, password)) {
                con.setAutoCommit(false);
                // В цикле вставлем несколько записей
//                for (int i = 0; i < 5; i++) {
//                    long contactId = insert(con, "FirstName_" + i, "LastName_" + i, "phone", "email");
//                    System.out.println("CONTACT_ID:" + contactId);
//                }
                // Завершаем транзакцию - подтверждаем
//                con.commit();
                // Вызов rollback отменит все внесенные изменения
                con.rollback();

                // Возвращаем свойство AutoCommit в true
                con.setAutoCommit(true);

                // Можно проверить результат
                select(con);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void select(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM JC_CONTACT");
        while (rs.next()) {
            String str = rs.getString("contact_id") + ":" + rs.getString(2);
            System.out.println("Contact:" + str);
        }
        insert(con, "Антон", "Чайка", "890", "bla@mail.ru");
        rs.close();
        stmt.close();
    }

    private long insert(Connection con, String firstName, String lastName, String phone, String email) throws SQLException {
        // Объявили переменную для хранения ИД
        long contactId = -1;

        // Вторым параметром передаем массив полей, значниея которых нам нужны
        PreparedStatement stmt = con.prepareStatement("INSERT INTO jc_contact (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)", new String[] {"contact_id"});
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, phone);
        stmt.setString(4, email);
        stmt.executeUpdate();


        // Получаем список данных дял сгенерированных ключей
        ResultSet gk = stmt.getGeneratedKeys();
        if(gk.next()) {
            // Получаем поле contact_id
            contactId = gk.getLong("contact_id");
        }
        stmt.close();

        return contactId;
    }
}
