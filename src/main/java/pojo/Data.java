package pojo;

import java.util.List;

public class Data {
    private String app;
    private List<Cart> cart;
    private String error;
    private String order_id;
    private String order_token;
    private String state;

    public Data() {
    }

    public String getApp() {
        return app;
    }


    public void setApp(String app) {
        this.app = app;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_token() {
        return order_token;
    }

    public void setOrder_token(String order_token) {
        this.order_token = order_token;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}