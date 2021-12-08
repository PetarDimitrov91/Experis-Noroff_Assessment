package models;

import java.util.List;

public class Catalog {
    private List<Movie> movies;

    public Catalog(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return this.movies;
    }
}
