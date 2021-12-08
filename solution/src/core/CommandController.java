package core;

import models.Catalog;
import models.Movie;
import models.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CommandController {
    static void start(Catalog movies, List<User> users) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = reader.readLine();

        while (!command.equals("end")) {
            if (command.equals("recent")) {
                showRecentMovies(movies);
            } else if (command.equals("personal")) {
                showPersonalisedMovie(movies,users);
            }

            command = reader.readLine();
        }
    }

    private static void showRecentMovies(Catalog movies) {
        List<Movie> recentMovies = movies.getRecentMovies();
        System.out.println("recent movies");
    }

    private static void showPersonalisedMovie(Catalog movies, List<User> users) {

        System.out.println("personal movie");
    }
}
