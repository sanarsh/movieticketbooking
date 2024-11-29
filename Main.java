import java.io.*;
import java.util.*;

// User Class
class User {
    private String name;
    private String mobile;

    public User(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String newMobile) {
        this.mobile = newMobile;
    }
}

// Seat Class
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
// Theater Class
// Theater Class
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



// Movie Class
class Movie {
    private final String name;
    private final List<Theater> theaters;

    public Movie(String name) {
        this.name = name;
        this.theaters = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Theater> getTheaters() {
        return theaters;
    }

    public void addTheater(Theater theater) {
        theaters.add(theater);
    }
}

// MovieManager Class
class MovieManager {
    private final List<Movie> movies;

    public MovieManager() {
        this.movies = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void displayMovies() {
        System.out.println("\nAvailable Movies:");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i).getName());
        }
    }
}

// BookingSystem Class
class BookingSystem {
    private final MovieManager movieManager;
    private User user;

    public BookingSystem() {
        this.movieManager = new MovieManager();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addMovie(Movie movie) {
        movieManager.addMovie(movie);
    }

    public void displayMovies() {
        movieManager.displayMovies();
    }

    public void displayTheatersForMovie(int movieIndex) {
        List<Movie> movies = movieManager.getMovies();
        if (movieIndex >= 0 && movieIndex < movies.size()) {
            Movie movie = movies.get(movieIndex);
            System.out.println("\nAvailable Theaters for " + movie.getName() + ":");
            List<Theater> theaters = movie.getTheaters();
            for (int i = 0; i < theaters.size(); i++) {
                System.out.println((i + 1) + ". " + theaters.get(i).getName() + " (Screen " + theaters.get(i).getScreenNumber() + ")");
            }
        } else {
            System.out.println("Invalid movie selection.");
        }
    }

    public void bookSeats(int movieIndex, int theaterIndex) throws IOException {
        List<Movie> movies = movieManager.getMovies();
        if (movieIndex >= 0 && movieIndex < movies.size()) {
            Movie movie = movies.get(movieIndex);
            List<Theater> theaters = movie.getTheaters();
            if (theaterIndex >= 0 && theaterIndex < theaters.size()) {
                Theater theater = theaters.get(theaterIndex);

                // Ask user for seat type choice
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Do you want a Club seat or a Normal seat? (Club/Normal): ");
                String seatType = br.readLine().trim();

                // Show the seat layout for selected seat type
                theater.displaySeatLayout(seatType);
                System.out.print("Enter seat numbers to book: ");
                List<Integer> seatNumbers = parseSeatNumbers(br.readLine());
                if (theater.bookSeats(seatNumbers, seatType)) {
                    System.out.println("Seats booked successfully!");
                } else {
                    System.out.println("Try again.");
                }
            } else {
                System.out.println("Invalid theater selection.");
            }
        } else {
            System.out.println("Invalid movie selection.");
        }
    }

    public void cancelSeats(int movieIndex, int theaterIndex) throws IOException {
        List<Movie> movies = movieManager.getMovies();
        if (movieIndex >= 0 && movieIndex < movies.size()) {
            Movie movie = movies.get(movieIndex);
            List<Theater> theaters = movie.getTheaters();
            if (theaterIndex >= 0 && theaterIndex < theaters.size()) {
                Theater theater = theaters.get(theaterIndex);

                // Ask user for seat type choice
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Do you want to cancel Club seat or Normal seat? (Club/Normal): ");
                String seatType = br.readLine().trim();

                // Show the seat layout for selected seat type
                theater.displaySeatLayout(seatType);
                System.out.print("Enter seat numbers to cancel: ");
                List<Integer> seatNumbers = parseSeatNumbers(br.readLine());
                if (theater.cancelSeats(seatNumbers, seatType)) {
                    System.out.println("Seats canceled successfully!");
                } else {
                    System.out.println("Some seats were not booked or invalid. Try again.");
                }
            } else {
                System.out.println("Invalid theater selection.");
            }
        } else {
            System.out.println("Invalid movie selection.");
        }
    }

    private List<Integer> parseSeatNumbers(String input) {
        String[] parts = input.split(",");
        List<Integer> seatNumbers = new ArrayList<>();
        for (String part : parts) {
            seatNumbers.add(Integer.parseInt(part.trim()));
        }
        return seatNumbers;
    }

    public void updateMobileNumber() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter new mobile number: ");
        String newMobile = br.readLine();
        String oldMobile = user.getMobile(); // Get the current mobile number
        user.setMobile(newMobile); // Update the mobile number
        System.out.println("Mobile number updated successfully!");
        System.out.println("Your phone number has been updated from " + oldMobile + " to " + newMobile);
    }
}


// Main Class
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BookingSystem bookingSystem = new BookingSystem();

        System.out.print("Enter your name: ");
        String name = br.readLine();
        System.out.print("Enter your mobile number: ");
        String mobile = br.readLine();
        User user = new User(name, mobile);
        bookingSystem.setUser(user);

        // Adding Movies and Theaters
        Movie movie1 = new Movie("Venom Last Dance");
        movie1.addTheater(new Theater("PVR Cinemas", 5, 10, 1));
        movie1.addTheater(new Theater("Inox", 5, 10, 2));
        bookingSystem.addMovie(movie1);

        Movie movie2 = new Movie("Avengers: Endgame");
        movie2.addTheater(new Theater("IMAX", 5, 10, 1));
        movie2.addTheater(new Theater("Cinepolis", 5, 10, 2));
        bookingSystem.addMovie(movie2);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Welcome to the Booking System ---");
            System.out.println("1. View Movies");
            System.out.println("2. View Theaters for a Movie");
            System.out.println("3. Book Seats");
            System.out.println("4. Cancel Seats");
            System.out.println("5. Update/Change Mobile Number");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1:
                    bookingSystem.displayMovies();
                    break;
                case 2:
                    bookingSystem.displayMovies();
                    System.out.print("Enter movie number: ");
                    int movieIndex = Integer.parseInt(br.readLine()) - 1;
                    bookingSystem.displayTheatersForMovie(movieIndex);
                    break;
                case 3:
                    bookingSystem.displayMovies();
                    System.out.print("Enter movie number: ");
                    movieIndex = Integer.parseInt(br.readLine()) - 1;
                    bookingSystem.displayTheatersForMovie(movieIndex);
                    System.out.print("Enter theater number: ");
                    int theaterIndex = Integer.parseInt(br.readLine()) - 1;
                    bookingSystem.bookSeats(movieIndex, theaterIndex);
                    break;
                case 4:
                    bookingSystem.displayMovies();
                    System.out.print("Enter movie number: ");
                    movieIndex = Integer.parseInt(br.readLine()) - 1;
                    bookingSystem.displayTheatersForMovie(movieIndex);
                    System.out.print("Enter theater number: ");
                    theaterIndex = Integer.parseInt(br.readLine()) - 1;
                    bookingSystem.cancelSeats(movieIndex, theaterIndex);
                    break;
                case 5:
                    bookingSystem.updateMobileNumber();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        System.out.println("Thank you for using the Booking System!");
    }
}
