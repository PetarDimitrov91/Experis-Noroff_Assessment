package models;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class User {
    private final int id;
    private final String name;
    private final int[] viewedMovies;
    private final int[] purchasedMovies;

    public User(int id, String name, int[] viewedMovies, int[] purchasedMovies) {
        this.id = id;
        this.name = name;
        this.viewedMovies = viewedMovies;
        this.purchasedMovies = purchasedMovies;
    }

    public String getName() {
        return name;
    }

    public int[] getViewedMovies() {
        return viewedMovies;
    }

    public int[] getPurchasedMovies() {
        return purchasedMovies;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return String.format("Id: %d, Name: %s, ViewedMovies: %s, PurchasedMovies: %s", getId(), getName(),
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
