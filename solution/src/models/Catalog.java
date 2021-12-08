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

    public Movie getMovieById(int id) {
        return this.movies.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Movie> getRecentMovies() {
        //
        return this.movies;
    }
}
