package com.revature.comparing;

public class InventoryItem implements Comparable<InventoryItem> {
    String name;
    String vendor;
    float price;

    @Override
    public String toString() {
        return "InventoryItem{" +
                "name='" + name + '\'' +
                ", vendor='" + vendor + '\'' +
                ", price=" + price +
                '}';
    }

    public InventoryItem(String name, String vendor, float price) {
        this.name = name;
        this.vendor = vendor;
        this.price = price;
    }

    @Override
    public int compareTo(InventoryItem o) {
        return Float.compare(this.price, o.price);
    }
}
