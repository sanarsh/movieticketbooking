class Seat {
    private final int seatNumber;
    private boolean isBooked;
    private String type; // New property to differentiate seat type

    public Seat(int seatNumber, String type) {
        this.seatNumber = seatNumber;
        this.isBooked = false;
        this.type = type;  // Set seat type (Club or Normal)
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        isBooked = true;
    }

    public void cancel() {
        isBooked = false;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return seatNumber + ":" + (isBooked ? "B" : "A") + "(" + type + ")";
    }
}
