package djs.inventory.service;

import djs.inventory.dto.StockDTO;
import org.springframework.stereotype.Service;

@Service
public interface StockService {

    void updateStock(StockDTO stockDTO);

}
