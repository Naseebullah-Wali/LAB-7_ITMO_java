package Server.utility.models;

import common.Data.Product;
import common.Interaction.User;

public class ProductModel  {
    private Product product;
    private User user;
//    public ProductModel(Product product, User user) {
//    }

    public ProductModel(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }
}
