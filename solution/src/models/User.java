package models;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class User {
    private final int id;
    private final String name;
    private final int[] viewedMovies;
    private final int[] purchasedMovies;

     /*
    probably, I can store the  "viewedMovies" and the "purchasedMovies" in another Collection,
    but at this point, it is not needed in the application.
     */

    public User(int id, String name, int[] viewedMovies, int[] purchasedMovies) {
        this.id = id;
        this.name = name.toLowerCase();
        this.viewedMovies = viewedMovies;
        this.purchasedMovies = purchasedMovies;
    }

    public String getName() {
        return this.name;
    }

    public int[] getViewedMovies() {
        return this.viewedMovies;
    }

    public int[] getPurchasedMovies() {
        return this.purchasedMovies;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return String.format("Id: %d, Name: %s, Viewed movies: %s, Purchased movies: %s", getId(), getName(),
                collect(getViewedMovies()),
                collect(getPurchasedMovies())
        );
    }

    private String collect(int[] viewedMovies) {
        return Arrays.stream(viewedMovies)
                .mapToObj(Objects::toString)
                .collect(Collectors.joining(", "));
    }
}
