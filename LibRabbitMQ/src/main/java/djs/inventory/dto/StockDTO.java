package djs.inventory.dto;

import java.io.Serializable;

public record StockDTO(String productCode, int quantity) implements Serializable {
}
