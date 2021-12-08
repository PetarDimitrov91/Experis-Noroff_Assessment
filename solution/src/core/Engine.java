package core;

import models.Catalog;
import models.Movie;
import models.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Engine implements Runnable {

    //if you want to start the program properly,maybe you must change the directory of the String path matching the directory of the file on your computer.
    private static final String moviesPath = "D:\\Programmieren\\Assessment_Second_Phase\\Movie product data\\Products.txt";
    private static final String userData = "D:\\Programmieren\\Assessment_Second_Phase\\Movie product data\\Users.txt";
    private Catalog movieCatalog;
    private List<User> users;

    public Engine() throws IOException {
        this.movieCatalog = new Catalog(this.getMovies());
        this.users = getUsers();
    }

    @Override
    public void run() {
        try {
            CommandController.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Movie> getMovies() throws IOException {
        List<Movie> movies = new ArrayList<>();

        String regex = "(?<id>^\\d+), (?<name>[A-Za-z0-9 .:'()&]+).(?<year>\\d+). (?<keywords>[A-Za-z, -]+)(?<rating>[0-9.]+),(?<price>[0-9.]+)";
        Pattern pattern = Pattern.compile(regex);

        List<String> lines = readDataFromFile(moviesPath);

        lines.forEach(e -> {
            Matcher matcher = pattern.matcher(e);
            if (matcher.find()) {
                movies.add(createMovie(matcher));
            }
        });

        return movies;
    }

    private List<String> readDataFromFile(String path) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        return reader.lines().collect(Collectors.toList());
    }

    private Movie createMovie(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String name = matcher.group("name");
        int year = Integer.parseInt(matcher.group("year"));
        String[] keywords = matcher.group("keywords").split("[\\s,]+");
        double rating = Double.parseDouble(matcher.group("rating"));
        double price = Double.parseDouble(matcher.group("price"));

        return new Movie(id, name, year, keywords, rating, price);
    }

    private List<User> getUsers() throws FileNotFoundException {
        List<User> users = new ArrayList<>();

        String regex = "(?<id>^\\d+), (?<name>\\w+), (?<viewedProducts>[0-9;]+), (?<purchasedProduchts>[0-9;]*)";
        Pattern pattern = Pattern.compile(regex);

        List<String> lines = readDataFromFile(userData);

        lines.forEach(e -> {
            Matcher matcher = pattern.matcher(e);
            if (matcher.find()) {
                users.add(createUser(matcher));
            }
        });
        return users;
    }

    private User createUser(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String name = matcher.group("name");
        int[] viewedMovies = Arrays.stream(matcher.group("viewedProducts")
                        .split(";"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] purchasedMovies = Arrays.stream(matcher.group("purchasedProduchts")
                        .split(";"))
                .mapToInt(Integer::parseInt)
                .toArray();

        return new User(id, name, viewedMovies, purchasedMovies);
    }

}
