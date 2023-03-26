package url;

public class Constants {
    public final static String URL_ORDER = "http://vmapp1-1.vdc1.tipay.ru:32001/";
    public final static String GET_ORDERS = "/orders";
    public final static String GET_ORDER = "/order/";
    public final static String CANCEL_ORDER = "internal/api/v1/cancel/";
    private final static String API_V_1_APPS = "api/v1/apps/";
    private final static String APP_NAME = "app.name";
    public final static String GET_ALL_ORDERS = API_V_1_APPS + APP_NAME + GET_ORDERS;

    public final static String GET_VERIFY = API_V_1_APPS + APP_NAME + "/verify";
    // ====================================================================== //
    public final static String GET_ORDER_INFO = API_V_1_APPS + APP_NAME + GET_ORDER;
    public final static String POST_ORDER_ACK = API_V_1_APPS + APP_NAME + GET_ORDER;
    public final static String GET_APP_PRODUCTS = API_V_1_APPS + APP_NAME + "/products";
    private final static String ORDER_CREATE_PRODUCT = "/order/create/product/";

    // ======================================================================= //
    public final static String SKU = "the.biggest.rocket.in.a.game";
    public final static String POST_CREATE_ORDER = API_V_1_APPS + APP_NAME + ORDER_CREATE_PRODUCT + SKU;
    public final static String GET_APP_PRODUCTS_SKU = API_V_1_APPS + APP_NAME + "/products/" + SKU;

}