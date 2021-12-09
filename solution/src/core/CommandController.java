package core;

import models.DataBase;
import models.Movie;
import models.User;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CommandController {
    static void start(DataBase<Movie> movies, DataBase<User> users, Map<Integer, Integer> userSession) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("""
                Hello! If you want to see the most recent movies just type "recent N" where N is the number of the movies that you want to see!
                If you want to see the personalised movies, just type "personal"
                If you want to close the application, just type "end\"""");

        String command = reader.readLine().toLowerCase().trim();

        while (!command.equals("end")) {
            if (command.contains("recent")) {
                int count = Integer.parseInt(command.split("\\s+")[1]);
                showRecentMovies(movies, count);

            } else if (command.equals("personal")) {
                System.out.println("Please type a Username, if you wan\\'t to go back, just type back");
                command = reader.readLine().toLowerCase().trim();
                while (!command.equals("back")) {
                    switch (command) {
                        case "olav", "tage", "ida", "mia" -> showPersonalisedMovie(movies, users, userSession, command);
                        default -> System.out.println("There is no data for these user");
                    }

                    command = reader.readLine().toLowerCase().trim();
                }
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
        User currentUser = getUserByName(users, name);
        int currentLookedMovieId = userSession.get(currentUser.getId());

        String[] currentLookedMovieKeywords = movies.getData().stream()
                .filter(e -> e.getId() == currentLookedMovieId)
                .findFirst()
                .orElseThrow()
                .getKeywords();

        Predicate<Movie> predicate = e -> Arrays.asList(e.getKeywords()).contains(Arrays.asList(currentLookedMovieKeywords).get(0)) &&
                Arrays.asList(e.getKeywords()).contains(Arrays.asList(currentLookedMovieKeywords).get(1));

        return movies.getData().stream()
                .filter(predicate)
                .limit(1)
                .findFirst()
                .orElse(null);
    }

    private static User getUserByName(DataBase<User> users, String name) {
        return users.getData().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }


}
