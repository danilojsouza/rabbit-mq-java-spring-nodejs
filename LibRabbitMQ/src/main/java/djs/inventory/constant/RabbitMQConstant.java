package djs.inventory.constant;

public class RabbitMQConstant {
    public static final String STOCK_QUEUE = "STOCK";
    public static final String PRICE_QUEUE = "PRICE";

    public static final String DLQ_NAME = STOCK_QUEUE + ".dlq";
    public static final String DLX_NAME = STOCK_QUEUE + ".dlx";

}
