package pojo;

public class Purchase {
    private User user;
    private Product product;
    private int sum;

    public Purchase(User user, Product product, int sum) {
        this.user = user;
        this.product = product;
        this.sum = sum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

}
