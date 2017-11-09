package com.revature.comparing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        List<InventoryItem> items = new ArrayList<>();
        items.add(new InventoryItem("oca", "boyarsky, selikoff", 2.03f));
        items.add(new InventoryItem("return of bobbert", "grantly", 2.02f));
        items.add(new InventoryItem("bobbert", "ryan lessley", 2.01f));

        Collections.sort(items);
        System.out.println("\nsorted by natural ordering: by price");
        for (InventoryItem i : items)
            System.out.println(i);

        Collections.sort(items, new InventoryItemCustomComparer());
        System.out.println("\nsorted by custom comparator: by vendor name with price as tie-breaker");
        for (InventoryItem i : items)
            System.out.println(i);

        String[] strings = {"c", "cc", "ccd", "a", "b", "ab"};
        List<String> stringsList = new ArrayList<>();
        for (String s: strings)
            stringsList.add(s);
        Collections.sort(stringsList, new StringReverseComparator());

        System.out.println("\nstrings sorted in reverse");
        System.out.println(stringsList);
    }
}
