package djs.inventory.controller;

import djs.inventory.connection.RabbitMQConnection;
import djs.inventory.dto.StockDTO;
import djs.inventory.service.RabbitMQService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
public class StockController {

    private final RabbitMQService rabbitMQService;

    public StockController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @PutMapping
    private ResponseEntity<StockDTO> updateStock(@RequestBody StockDTO stockDTO) {
        this.rabbitMQService.sendMessage(RabbitMQConnection.STOCK_QUEUE, stockDTO);
        return new ResponseEntity<>(stockDTO, HttpStatus.OK);
    }
}
