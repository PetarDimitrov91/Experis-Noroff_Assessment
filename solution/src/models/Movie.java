package models;

import java.util.HashSet;

public class Movie {
    private final int id;
    private final String name;
    private final int year;
    private final HashSet<String> genres;
    private final double rating;
    private final double price;
    private int timesPurchased;

    public Movie(int id, String name, int year, HashSet<String> genres, double rating, double price) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.genres = genres;
        this.rating = rating;
        this.price = price;
        this.timesPurchased = 0;
    }

    public String getName() {
        return this.name;
    }

    public int getYear() {
        return this.year;
    }

    public HashSet<String> getGenres() {
        return this.genres;
    }

    public double getRating() {
        return this.rating;
    }

    public double getPrice() {
        return this.price;
    }

    public int getId() {
        return this.id;
    }

    public int getTimesPurchased() {
        return this.timesPurchased;
    }

    public void incrementTimesPurchased() {
        this.timesPurchased += 1;
    }

    public String toString() {
        return String.format("Movie id: %d, Movie name: %s, Movie year: %d, Genres: %s, Movie rating: %.2f, Movie price: %.2f Times purchased: %d",
                getId(), getName(), getYear(), String.join(",", getGenres()), getRating(), getPrice(), getTimesPurchased());
    }
}
