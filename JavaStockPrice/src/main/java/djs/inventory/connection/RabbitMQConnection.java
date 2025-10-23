package djs.inventory.connection;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static djs.inventory.constant.RabbitMQConstant.*;

@Component
public class RabbitMQConnection {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConnection.class);
    private static final String EXCHANGE_NAME = "amq.direct";

    private final AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue getNewQueue(String queueName, Map<String, Object> args) {
        return new Queue(queueName, true, false, false, args);
    }

    private DirectExchange getNewDirectExchange(String name) {
        return new DirectExchange(name, true, false);
    }

    private Binding getNewBinding(Queue queue, DirectExchange exchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void addQueues() {
        // Creating Dead Letter Queue
        DirectExchange dlExchange = getNewDirectExchange(DLX_NAME);
        Queue dlQueue = getNewQueue(DLQ_NAME, null);
        Binding dlBinding = getNewBinding(dlQueue, dlExchange);

        this.amqpAdmin.declareExchange(dlExchange);
        this.amqpAdmin.declareQueue(dlQueue);
        this.amqpAdmin.declareBinding(dlBinding);
        log.info("Dead Letter Queue and exchange added!");

        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DLX_NAME);
        args.put("x-dead-letter-routing-key", DLQ_NAME);

        Queue stockQueue = this.getNewQueue(STOCK_QUEUE, args);
        DirectExchange exchange = this.getNewDirectExchange(EXCHANGE_NAME);
        Queue priceQueue = this.getNewQueue(PRICE_QUEUE, null);
        Binding binding = this.getNewBinding(stockQueue, exchange);

        this.amqpAdmin.declareExchange(exchange);
        this.amqpAdmin.declareQueue(stockQueue);
        this.amqpAdmin.declareQueue(priceQueue);
        this.amqpAdmin.declareBinding(binding);
        log.info("Stock queue and direct exchange are bound!");
    }

}
