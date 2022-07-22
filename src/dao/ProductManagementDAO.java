package dao;

import java.sql.*;
import java.util.*;
import dbutil.DBUtil;
import pojo.Product;

public class ProductManagementDAO {


    public List<Product> getAllProducts()
    {
        List<Product> productList = new ArrayList<Product>();
        Connection conn = null;
        try
        {
            conn = DBUtil.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM product");
            while(rs.next())
            {
                Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price"));
                productList.add(product);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);  //close connection
        }

        return productList;
    }

    public Product getProductByid(int productId)
    {
        Product product = null;
        Connection conn = null;
        try
        {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id,name,price FROM product WHERE id = ?");
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            //iterate through result
            while(rs.next())
            {
                product = new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }


        return product;
    }

    public int addProduct(Product product)
    {
        int auto_id = 0;
        Connection conn = null;
        try
        {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO product (name,price) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            int status = ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                auto_id = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }

        return auto_id;
    }


    public int updateProduct(Product product)
    {
        int status = 0;
        Connection conn = null;
        try
        {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE product SET name=?, price=? WHERE id=?");
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setInt(3, product.getId());
            status = ps.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }  finally {
            DBUtil.closeConnection(conn);  //close connection
        }
        return status;
    }

    public int deleteProduct(int productId)
    {
        int status = 0;
        Connection conn = null;
        try
        {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM product where id = ?");
            ps.setInt(1, productId);
            status = ps.executeUpdate();  //if successful status should return 1

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
          finally {
            DBUtil.closeConnection(conn);  //close connection
        }

        return status;
    }

}
