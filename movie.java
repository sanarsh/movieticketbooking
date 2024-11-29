import java.util.ArrayList;
import java.util.List;

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
