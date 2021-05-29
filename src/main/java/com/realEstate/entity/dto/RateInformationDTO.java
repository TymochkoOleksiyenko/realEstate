package com.realEstate.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class RateInformationDTO {
    int wishListId;
    List<RateItemDTO> rates;
}
