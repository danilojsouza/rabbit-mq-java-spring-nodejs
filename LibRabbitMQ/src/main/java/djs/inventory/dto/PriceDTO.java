package djs.inventory.dto;

import java.io.Serializable;

public record PriceDTO(String productCode, double price) implements Serializable {
}
