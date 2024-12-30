package dev.teamso.flightbooking.model.entities;

import java.util.UUID;

public class Seat {
    private UUID id;
    private SeatType type;
    private double price;
    private boolean isPurchased;

    public Seat(UUID id, SeatType type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.isPurchased = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", type=" + type +
                ", price=" + price +
                ", isPurchased=" + isPurchased +
                '}';
    }
}
