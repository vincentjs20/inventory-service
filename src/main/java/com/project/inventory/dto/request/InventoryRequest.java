package com.project.inventory.dto.request;

import com.project.inventory.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InventoryRequest {

    private Long itemId;

    private Integer qty;

    private String type;

}
