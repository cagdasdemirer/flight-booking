package dev.teamso.flightbooking.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonIgnore
    private Flight flight;
    @Column(nullable = false)
    private int seatNumber;
    @Enumerated(EnumType.STRING)
    private SeatType type;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isPurchased;
    @Version
    private Long version;

    public Seat(int seatNumber, SeatType type, double price) {
        this.seatNumber = seatNumber;
        this.type = type;
        this.price = price;
        this.isPurchased = false;
    }

    public Seat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeatNumber() { return seatNumber;}

    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber;}

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

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", flight_id=" + flight.getId() +
                ", seatNumber=" + seatNumber +
                ", type=" + type +
                ", price=" + price +
                ", isPurchased=" + isPurchased +
                '}';
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
