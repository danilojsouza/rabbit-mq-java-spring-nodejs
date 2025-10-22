package djs.inventory.controller;

import djs.inventory.dto.PriceDTO;
import djs.inventory.service.RabbitMQService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static djs.inventory.constant.RabbitMQConstant.PRICE_QUEUE;

@RestController
@RequestMapping("price")
public class PriceController {

    private final RabbitMQService rabbitMQService;

    public PriceController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @PutMapping
    private ResponseEntity<PriceDTO> updatePrice(@RequestBody PriceDTO priceDTO) {
        this.rabbitMQService.sendMessage(PRICE_QUEUE, priceDTO);
        return new ResponseEntity<>(priceDTO, HttpStatus.OK);
    }
}
