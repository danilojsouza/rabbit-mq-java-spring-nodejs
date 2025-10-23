package djs.inventory.consumer;

import djs.inventory.constant.RabbitMQConstant;
import djs.inventory.dto.StockDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockConsumer {

    @RabbitListener(queues = RabbitMQConstant.STOCK_QUEUE)
    private void consumer(StockDTO stockDTO) {
        System.out.println("Received Stock Message: " + stockDTO);
    }

}
