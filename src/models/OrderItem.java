package models;

public class OrderItem {

    private String name;
    private int quantity;
    private double price;

    // ===== CONSTRUCTOR =====
    public OrderItem(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // ===== GETTERS =====
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // ===== HELPER =====
    public void increaseQuantity() {
        quantity++;
    }
}
