package com.github.domain;

import java.util.List;

/**
 * The InvData class is a Data Transfer Object (DTO) that holds inventory information.
 * It encapsulates a list of items to be transferred between subsystems.
 */
public class InvData {
    /**
     * A list of items in the inventory.
     * Currently uses Object as a placeholder for the actual Item class.
     */
    public List<Object> itemsList; // Replace Object with your Item class
}
