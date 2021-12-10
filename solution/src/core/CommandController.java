package core;

import models.DataBase;
import models.Movie;
import models.User;

import java.io.*;
import java.util.*;

public class CommandController {
    static void start(DataBase<Movie> movies, DataBase<User> users, Map<Integer, Integer> userSession) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("""
                Hello! If you want to see the most recent movies just type "recent N" where N is the number of the movies that you want to see!
                If you want to see the personalised movies, just type "personal"
                If you want to close the application, just type "end\"""");

        String command = reader.readLine().toLowerCase().trim();

        while (!command.equals("end")) {
            if (command.equals("recent")) {
                showRecentMovies(movies, 3);

            } else if (command.contains("recent")) {
                int count = Integer.parseInt(command.split("\\s+")[1]);
                showRecentMovies(movies, count);

            } else if (command.equals("personal")) {
                System.out.println("Please type a Username, if you want to go back, just type \"back\"");
                command = reader.readLine().toLowerCase().trim();

                while (!command.equals("back")) {
                    switch (command) {
                        case "olav", "tage", "ida", "mia" -> showPersonalisedMovie(movies, users, userSession, command);
                        default -> System.out.println("There is no data for these user");
                    }

                    command = reader.readLine().toLowerCase().trim();
                }
            } else {
                System.out.println("there is no such command");
            }

            command = reader.readLine().toLowerCase().trim();
        }
    }

    private static void showRecentMovies(DataBase<Movie> movies, int count) {
        movies.getData().stream()
                .sorted(Comparator.comparingDouble(Movie::getRating)
                        .thenComparing(Movie::getTimesPurchased)
                        .reversed())
                .limit(count)
                .forEach(System.out::println);
    }

    private static void showPersonalisedMovie(DataBase<Movie> movies, DataBase<User> users, Map<Integer, Integer> userSession, String name) {
        Movie personalisedMovie = findPersonalisedMovie(movies, users, userSession, name);
        System.out.println(personalisedMovie);
    }


    private static Movie findPersonalisedMovie(DataBase<Movie> movies, DataBase<User> users, Map<Integer, Integer> userSession, String name) {
        User crrUser = getUserByName(users, name);
        int crrViewedMovieId = userSession.get(crrUser.getId());

        HashSet<String> crrViewedMovieGenres = Objects.requireNonNull(movies.getData().stream()
                        .filter(e -> e.getId() == crrViewedMovieId)
                        .findFirst()
                        .orElse(null))
                .getGenres();

        Movie bestMatch = null;
        int bestMatchCounter = 0;

        for (Movie movie : movies.getData()) {
            int crrUserId = crrUser.getId();
            int[] boughtMovies = crrUser.getPurchasedMovies();
            boolean isBought = Arrays.stream(boughtMovies).anyMatch(e -> e == movie.getId());

            if (movie.getId() == userSession.get(crrUserId) || isBought) {
                continue;
            }

            HashSet<String> crrGenres = movie.getGenres();
            int counter = 0;

            for (String genre : crrViewedMovieGenres) {
                if (crrGenres.contains(genre)) {
                    counter++;
                }
            }

            if (counter > bestMatchCounter) {
                bestMatch = movie;
                bestMatchCounter = counter;
            }
        }

        return bestMatch;
    }

    private static User getUserByName(DataBase<User> users, String name) {
        return users.getData().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }


}
