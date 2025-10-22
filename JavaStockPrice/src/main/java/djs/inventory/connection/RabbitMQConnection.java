package djs.inventory.connection;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import static djs.inventory.constant.RabbitMQConstant.STOCK_QUEUE;
import static djs.inventory.constant.RabbitMQConstant.PRICE_QUEUE;

@Component
public class RabbitMQConnection {

    private static final String EXCHANGE_NAME = "amq.direct";

    private final AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue getNewQueue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange getNewDirectExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    private Binding getNewBinding(Queue queue, DirectExchange exchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void addQueues() {
        Queue stockQueue = this.getNewQueue(STOCK_QUEUE);
        Queue priceQueue = this.getNewQueue(PRICE_QUEUE);

        DirectExchange exchange = this.getNewDirectExchange();

        Binding binding = this.getNewBinding(stockQueue, exchange);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareQueue(stockQueue);
        this.amqpAdmin.declareQueue(priceQueue);

        this.amqpAdmin.declareBinding(binding);
    }

}
