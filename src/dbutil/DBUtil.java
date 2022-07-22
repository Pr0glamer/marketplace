package dbutil;
import java.sql.*;

public class DBUtil {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketplace", "root", "root");

            } catch (Exception e) {
                e.printStackTrace();
            }

        return connection;
    }

    public static void createTables()  {
        Connection conn = null;
        try {
            conn = getConnection();

        Statement stmt = conn.createStatement();

            String sql =
                    "CREATE TABLE IF NOT EXISTS `marketplace`.`product` ("+
                    "`id` INT NOT NULL AUTO_INCREMENT,"+
                    "`name` VARCHAR(45) NOT NULL,"+
                    "`price` INT NOT NULL,"+
                    "PRIMARY KEY (`id`));";
            stmt.executeUpdate(sql);

        sql =
                "CREATE TABLE IF NOT EXISTS `marketplace`.`user` ("+
              "`id` INT NOT NULL AUTO_INCREMENT,"+
              "`firstname` VARCHAR(45) NOT NULL,"+
              "`lastname` VARCHAR(45) NOT NULL,"+
              "`amountofmoney` INT NOT NULL,"+
                            "PRIMARY KEY (`id`));";
        stmt.executeUpdate(sql);

        sql =
        "CREATE TABLE IF NOT EXISTS `marketplace`.`user` ("+
                "`id` INT NOT NULL AUTO_INCREMENT,"+
                "`firstname` VARCHAR(45) NOT NULL,"+
                "`lastname` VARCHAR(45) NOT NULL,"+
                "`amountofmoney` INT NOT NULL,"+
                "PRIMARY KEY (`id`));";
        stmt.executeUpdate(sql);

        sql =
                "CREATE TABLE IF NOT EXISTS `purchase` ("+
                  "`id` int NOT NULL AUTO_INCREMENT,"+
                  "`product_id` int NOT NULL,"+
                  "`user_id` int NOT NULL,"+
                  "`sum` int NOT NULL,"+
                "PRIMARY KEY (`id`),"+
                "KEY `product_idx` (`product_id`),"+
                    "KEY `user_fk_idx` (`user_id`),"+
                    "CONSTRAINT `product_fk` FOREIGN KEY (`product_id`) REFERENCES `product`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,"+
                    "CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE"+
                    ") ENGINE=InnoDB";

        stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //close connection
    public static void closeConnection(Connection conn)
    {
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
