import java.io.*;
import java.util.*;

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
                    System.out.println("Your screen number is: " + theater.getScreenNumber());
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
