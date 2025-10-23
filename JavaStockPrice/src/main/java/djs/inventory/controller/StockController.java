package djs.inventory.controller;

import djs.inventory.dto.StockDTO;
import djs.inventory.service.RabbitMQService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static djs.inventory.constant.RabbitMQConstant.STOCK_QUEUE;

@RestController
@RequestMapping("stock")
public class StockController {

    private static final Logger log = LogManager.getLogger(StockController.class);
    private final RabbitMQService rabbitMQService;

    public StockController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @PutMapping
    private ResponseEntity<StockDTO> updateStock(@RequestBody StockDTO stockDTO) {
        try {
            this.rabbitMQService.sendMessage(STOCK_QUEUE, stockDTO);
        } catch (Exception e) {
            log.error("e: ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(stockDTO, HttpStatus.OK);
    }
}
