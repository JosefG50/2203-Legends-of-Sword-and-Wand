package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.Inn;
import com.github.campaign_progression.application.dto.GetShopResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GetShopUseCase {

    private final Inn inn;

    public GetShopUseCase(Inn inn) {
        this.inn = inn;
    }

    public GetShopResponseDTO execute() {

        List<GetShopResponseDTO.ItemDTO> items = inn.getShop()
                .stream()
                .map(type -> new GetShopResponseDTO.ItemDTO(
                        type.getName(),
                        type.getCost(),
                        type.getHpHeal(),
                        type.getManaHeal(),
                        type.canRevive()
                ))
                .collect(Collectors.toList());

        return new GetShopResponseDTO(items);
    }
}