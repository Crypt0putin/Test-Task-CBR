package ui;

public class ProductDetails {
    private final String name;
    private final String size;
    private final String color;
    private final String price;
    private final int quantity;

    public ProductDetails(String name, String size, String color, String price, int quantity) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public String getName() { return name; }
    public String getSize() { return size; }
    public String getColor() { return color; }
    public String getPrice() { return price; }
    public int getQuantity() { return quantity; }
} 