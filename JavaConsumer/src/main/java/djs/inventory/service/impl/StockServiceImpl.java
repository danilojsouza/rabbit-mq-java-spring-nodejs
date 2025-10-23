package djs.inventory.service.impl;

import djs.inventory.dto.StockDTO;
import djs.inventory.service.StockService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StockServiceImpl implements StockService {

    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    @Override
    public void updateStock(StockDTO stockDTO) {

        if (stockDTO.quantity() < 0) {
            log.error("SERVICE: Estoque negativo detectado! Rejeitando atualização: {}", stockDTO);
            throw new IllegalArgumentException("Quantidade de estoque não pode ser negativa.");
        }

        // Stock existingStock = stockRepository.findByProductCode(stockDTO.productCode());
        int newQuantity = stockDTO.quantity();
        // stockRepository.save(new Stock(stockDTO.productCode(), newQuantity));

        log.info("SERVICE: Sucesso. Estoque do produto {} atualizado para {} unidades.", stockDTO.productCode(), newQuantity);
    }
}