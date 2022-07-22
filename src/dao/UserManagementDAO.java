package dao;

import dbutil.DBUtil;
import pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManagementDAO {

    public List<User> getAllUsers()
    {
        List<User> userList = new ArrayList<>();
        Connection conn = null;
        try
        {
            conn = DBUtil.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM user");
            while(rs.next())
            {
                User user = new User(rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getInt("amountofmoney"));
                userList.add(user);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }

        return userList;
    }

    //different query
    public User getUserByid(int userId)
    {
        User user = null;
        Connection conn = null;
        try
        {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                user = new User(rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getInt("amountofmoney"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        return user;
    }

    public int addUser(User user)
    {
        int auto_id = 0;
        Connection conn = null;
        try
        {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user (firstname,lastname,amountofmoney) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            //set parameters of query here but using the values for the product object
            //ps.setString(1, product.getProductid());
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3,    user.getAmountOfMoney());
            int status = ps.executeUpdate();  //if successful status should return 1

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                auto_id = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);  //close connection
        }

        return auto_id;
    }

    public int updateUser(Connection conn, User user)
    {
        int status = 0;
        try
        {
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET amountofmoney=? WHERE id=?");
            ps.setInt(1, user.getAmountOfMoney());
            ps.setInt(2, user.getId());
            status = ps.executeUpdate();  //if successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    public int deleteUser(int user_id)
    {
        int status = 0;
        Connection conn = null;
        try
        {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM user where id = ?");
            ps.setInt(1, user_id);
            status = ps.executeUpdate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
         }
        return status;
    }

}
