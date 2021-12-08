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

    private static final String MOVIES_PATH = "D:\\Programmieren\\Assessment_Second_Phase\\Movie product data\\Products.txt";
    private static final String USER_DATA_PATH = "D:\\Programmieren\\Assessment_Second_Phase\\Movie product data\\Users.txt";
    private final Catalog movieCatalog;
    private final List<User> users;

    public Engine() throws IOException {
        this.movieCatalog = new Catalog(this.getMovies());
        this.users = getUsers();
    }

    @Override
    public void run() {
        try {
            CommandController.start(this.movieCatalog, this.users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Movie> getMovies() throws IOException {
        String regex = "(?<id>^\\d+), (?<name>[A-Za-z0-9 .:'()&]+).(?<year>\\d+). (?<keywords>[A-Za-z, -]+)(?<rating>[0-9.]+),(?<price>[0-9.]+)";
        return matchData(regex, MOVIES_PATH, "movie");
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
        String regex = "(?<id>^\\d+), (?<name>\\w+), (?<viewedProducts>[0-9;]+), (?<purchasedProducts>[0-9;]*)";
        return matchData(regex, USER_DATA_PATH, "user");
    }

    private <T> User createUser(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String name = matcher.group("name");
        int[] viewedMovies = Arrays.stream(matcher.group("viewedProducts")
                        .split(";"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] purchasedMovies = Arrays.stream(matcher.group("purchasedProducts")
                        .split(";"))
                .mapToInt(Integer::parseInt)
                .toArray();

        return new User(id, name, viewedMovies, purchasedMovies);
    }

    private List<String> readDataFromFile(String path) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        return reader.lines().collect(Collectors.toList());
    }

    public <T> List<T> matchData(String regex, String path, String type) throws FileNotFoundException {
        List<T> result = new ArrayList<>();

        Pattern pattern = Pattern.compile(regex);
        List<String> lines = readDataFromFile(path);

        lines.forEach(e -> {
            Matcher matcher = pattern.matcher(e);
            if (matcher.find()) {
                if (type.equals("user")) {
                    result.add((T) createUser(matcher));
                } else {
                    result.add((T) createMovie(matcher));
                }
            }
        });

        return result;
    }

}
