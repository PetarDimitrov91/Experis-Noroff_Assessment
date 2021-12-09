package core;

import models.DataBase;
import models.Movie;
import models.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Engine implements Runnable {

    private static final String MOVIES_PATH = "D:\\Programmieren\\Assessment_Second_Phase\\Movie product data\\Products.txt";
    private static final String USER_DATA_PATH = "D:\\Programmieren\\Assessment_Second_Phase\\Movie product data\\Users.txt";
    private static final String USER_SESSION_PATH = "D:\\Programmieren\\Assessment_Second_Phase\\Movie product data\\CurrentUserSession.txt";
    private final DataBase<Movie> movieDataBase;
    private final DataBase<User> usersDataBase;
    private final Map<Integer, Integer> userSession;

    public Engine() throws IOException {
        this.movieDataBase = new DataBase<>(this.getMovies());
        this.usersDataBase = new DataBase<>(this.getUsers());
        this.userSession = new HashMap<Integer, Integer>(this.getUserSession());
    }

    private Map<Integer, Integer> getUserSession() throws FileNotFoundException {
        return readDataFromFile(USER_SESSION_PATH)
                .stream()
                .collect(Collectors.toMap(e -> Integer.parseInt(e.split("[\\s,]+")[0]), e -> Integer.parseInt(e.split("[\\s,]+")[1])));
    }

    @Override
    public void run() {
        try {
            CommandController.start(this.movieDataBase, this.usersDataBase,this.userSession);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Movie> getMovies() throws IOException {
        String regex = "(?<id>^\\d+), (?<name>[A-Za-z0-9 .:'()&]+).(?<year>\\d+). (?<keywords>[A-Za-z, -]+)(?<rating>[0-9.]+),(?<price>[0-9.]+)";
        return matchData(regex, MOVIES_PATH, "movie");
    }

    private List<User> getUsers() throws FileNotFoundException {
        String regex = "(?<id>^\\d+), (?<name>\\w+), (?<viewedProducts>[0-9;]+), (?<purchasedProducts>[0-9;]*)";
        return matchData(regex, USER_DATA_PATH, "user");
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

    private User createUser(Matcher matcher) {
        int userId = Integer.parseInt(matcher.group("id"));
        String name = matcher.group("name");
        int[] viewedMovies = fetchMovieData(matcher, "viewedProducts");
        int[] purchasedMovies = fetchMovieData(matcher, "purchasedProducts");

        Arrays.stream(purchasedMovies).forEach(id -> getMovieById(id).incrementTimesPurchased());

        return new User(userId, name, viewedMovies, purchasedMovies);
    }

    private int[] fetchMovieData(Matcher matcher, String purchasedProducts) {
        return Arrays.stream(matcher.group(purchasedProducts)
                        .split(";"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private List<String> readDataFromFile(String path) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        return reader.lines().collect(Collectors.toList());
    }

    private <T> List<T> matchData(String regex, String path, String type) throws FileNotFoundException {
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

    public Movie getMovieById(int id) {
        return this.movieDataBase.getData().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
