package com.project.inventory.dto.response;

import com.project.inventory.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse extends OrderDTO {

    private String orderNo;


}
