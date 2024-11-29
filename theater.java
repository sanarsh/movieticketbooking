import java.util.ArrayList;
import java.util.List;

class Theater {
    private final String name;
    private final int screenNumber;
    private final List<Seat> seats;

    public Theater(String name, int rows, int cols, int screenNumber) {
        this.name = name;
        this.screenNumber = screenNumber;
        this.seats = new ArrayList<>();
        int totalSeats = rows * cols;
        for (int i = 1; i <= totalSeats; i++) {
            // Set first 20 seats as Club seats, the rest as Normal
            String type = (i <= 20) ? "Club" : "Normal";
            seats.add(new Seat(i, type));
        }
    }

    public String getName() {
        return name;
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public void displaySeatLayout(String seatType) {
        System.out.println("Seat Layout for " + seatType + " seats (A = Available, B = Booked):");

        // Loop to print seats
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            if (seat.getType().equalsIgnoreCase(seatType)) {
                // Print seat number and status in the requested format
                System.out.print(seat.getSeatNumber() + ":" + (seat.isBooked() ? "B" : "A") + "   ");
            }

            // After every 10 seats, move to the next line
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println(); // Add a newline after the seat layout
    }

    public boolean bookSeats(List<Integer> seatNumbers, String seatType) {
        for (int seatNumber : seatNumbers) {
            Seat seat = seats.get(seatNumber - 1);
            if (seat.isBooked() || !seat.getType().equalsIgnoreCase(seatType)) {
                System.out.println("Please choose a seat from the selected option.");
                return false;
            }
        }
        for (int seatNumber : seatNumbers) {
            seats.get(seatNumber - 1).book();
        }
        return true;
    }

    public boolean cancelSeats(List<Integer> seatNumbers, String seatType) {
        for (int seatNumber : seatNumbers) {
            Seat seat = seats.get(seatNumber - 1);
            if (!seat.isBooked() || !seat.getType().equalsIgnoreCase(seatType)) {
                System.out.println("Please choose a seat from the selected option.");
                return false;
            }
        }
        for (int seatNumber : seatNumbers) {
            seats.get(seatNumber - 1).cancel();
        }
        return true;
    }
}
