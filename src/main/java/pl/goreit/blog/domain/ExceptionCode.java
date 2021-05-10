package pl.goreit.blog.domain;

public enum ExceptionCode {

    PRODUCT_NOT_EXIST("GoreIT.01", "Product does not exist"),
    NON_AVAILABLE_PRODUCT("GoreIT.03", "Non available  product"),
    ORDER_NOT_FOUND("GoreIT.04", "Order not found {} "),
    IMPORT_IN_PROGRESS("GoreIT.05", "Some Import already in progress"),
    ORDER_NOT_HAVE_ORDERLINES("GoreIT.06", "Order must contains orderlines"),
    CAR_NOT_EXIST("GoreIT.07", "Car No {}  not exist  in cars");



    private final String message;
    private String code;

    private ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
