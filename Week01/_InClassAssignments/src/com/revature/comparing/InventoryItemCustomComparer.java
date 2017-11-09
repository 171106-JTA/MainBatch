package com.revature.comparing;

import java.util.Comparator;

public class InventoryItemCustomComparer implements Comparator<InventoryItem> {
    @Override
    public int compare(InventoryItem o1, InventoryItem o2) {
        int initCompare = o1.vendor.compareToIgnoreCase(o2.vendor);

        if (initCompare == 0)
            return Float.compare(o1.price, o2.price);
        else
            return initCompare;

    }
}
