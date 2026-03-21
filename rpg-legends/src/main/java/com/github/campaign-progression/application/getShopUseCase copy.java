package com.github.application;

import com.github.domain.Inn;
import com.github.domain.ItemType;

import java.util.List;

/**
 * Application-layer use case to fetch the available shop items from the inn.
 */
public class GetShopUseCase {

    private final Inn inn;

    public GetShopUseCase(Inn inn) {
        this.inn = inn;
    }

    /**
     * Returns a copy of the inn's shop items.
     *
     * @return List of available ItemType in the shop
     */
    public List<ItemType> execute() {
        return inn.getShop();
    }
}