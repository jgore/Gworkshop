package pl.goreit.blog.domain;

public enum ExceptionCode {

    GOREIT_01("GoreIT.01", "Product does not exist"),
    GOREIT_03("GoreIT.03", "Comment can be added only to available products"),
    GOREIT_04("GoreIT.04", "Order not found {} "),
    GOREIT_05("GoreIT.05", "Some Import already in progress"),
    GOREIT_06("GoreIT.06", "Order must contains orderlines"),
    GOREIT_07("GoreIT.07", "Car No {}  not exist  in cars");



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
