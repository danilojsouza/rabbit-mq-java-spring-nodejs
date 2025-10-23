package djs.inventory.consumer;

import djs.inventory.constant.RabbitMQConstant;
import djs.inventory.dto.StockDTO;
import djs.inventory.service.StockService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockConsumer {

    private final StockService stockService;

    public StockConsumer(StockService stockService) {
        this.stockService = stockService;
    }

    @RabbitListener(queues = RabbitMQConstant.STOCK_QUEUE)
    private void consumer(StockDTO stockDTO) {
        stockService.updateStock(stockDTO);
    }

}
