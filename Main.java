import java.io.*;
import java.util.*;


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
            // Inside the while loop in the Main class

            System.out.println("\nWelcome " + user.getName() + "!");
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
