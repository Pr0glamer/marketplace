package dao;

import dbutil.DBUtil;
import excp.NotEnoughMoneyException;
import pojo.Product;
import pojo.Purchase;
import pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseManagementDAO {

    public List<Product> getAllProductsByUser(int userId, ProductManagementDAO pmd) {
        List<Product> productList = new ArrayList<>();
        Connection conn = null;
        try {
            //typical jdbc coding
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT product_id FROM purchase WHERE user_id=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = pmd.getProductByid(rs.getInt("product_id"));
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);  //close connection
        }

        return productList;
    }

    public List<User> getAllUsersByProductId(int product_id, UserManagementDAO umd) {
        List<User> userList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT user_id FROM purchase WHERE product_id=?");
            ps.setInt(1, product_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = umd.getUserByid(rs.getInt("user_id"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }

        return userList;
    }

    public Purchase makePurchase(User user, Product product, UserManagementDAO umd) throws NotEnoughMoneyException {
        if(user == null || product == null) {
            return null;
        }
        if(user.getAmountOfMoney() >= product.getPrice()) {
            int newAmount = user.getAmountOfMoney() - product.getPrice();
            int statusPrh = 0;
            int statusUsr = 0;
            Purchase purchase = new Purchase(user, product, product.getPrice());
            Connection conn = null;
            try
            {
                conn = DBUtil.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement ps = conn.prepareStatement("INSERT INTO purchase (user_id, product_id, sum) VALUES(?,?,?)");

                ps.setInt(1, user.getId());
                ps.setInt(2, product.getId());
                ps.setInt(3, product.getPrice());

                statusPrh = ps.executeUpdate();
                if(statusPrh == 0) {
                    throw new SQLException("Can't update purchase " + purchase);
                }

                user.setAmountOfMoney(newAmount);
                statusUsr = umd.updateUser(conn, user);
                if(statusUsr == 0) {
                    throw new SQLException("Can't update user " + user);
                }

                conn.commit();

            } catch (SQLException throwables) {
                try {
                    conn.rollback();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                throwables.printStackTrace();
                return null;
            } finally {
                try {
                    conn.setAutoCommit(true);
                    DBUtil.closeConnection(conn);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            return purchase;

        } else {
            throw new NotEnoughMoneyException("Not enough money for purchase");
        }


    }

}
