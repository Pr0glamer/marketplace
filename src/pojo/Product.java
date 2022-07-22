package pojo;

public class Product {

    private int id;
    private String name;
    private int price;


    public Product(int productid, String productName, int productPrice) {
        this.id = productid;
        this.name = productName;
        this.price = productPrice;
    }

    public Product(String productName, int productPrice) {
        this.name = productName;
        this.price = productPrice;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return "Product [productid=" + id + ", productName=" + name + ", productPrice=" + price + "]";
    }

}
