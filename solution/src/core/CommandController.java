package core;

import models.DataBase;
import models.Movie;
import models.User;

import java.io.*;
import java.util.Comparator;
import java.util.List;

public class CommandController {
    static void start(DataBase<Movie> movies, DataBase<User> users) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("""
                Hello! If you want to see the most recent movies just type "recent N" where N is the number of the movies that you want to see!
                If you want to see the personalised movies, just type "personal"
                If you want to close the application, just type "end\"""");

        String command = reader.readLine();

        while (!command.equals("end")) {
            if (command.contains("recent")) {
                int count = Integer.parseInt(command.split("\\s+")[1]);
                showRecentMovies(movies, count);

            } else if (command.equals("personal")) {
                System.out.println("Please type a Username, if you wan\\'t to go back, just type back");
                command = reader.readLine();
                while (!command.equals("back")) {
                    switch (command) {
                        case "Olav" -> System.out.println("Olav is the best");
                        case "Tage" -> System.out.println("Tage is the best");
                        case "Ida" -> System.out.println("Ida is the best");
                        case "Eivind" -> System.out.println("Eivind is the best");
                        case "Mia" -> System.out.println("Mia is the best");
                        default -> System.out.println("There is no data for these user");
                    }
                    command = reader.readLine();
                }
                // showPersonalisedMovie(movies, users);
            }
            command = reader.readLine();
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

    private static void showPersonalisedMovie(DataBase<Movie> movies, DataBase<User> users) {
        List<User> data = users.getData();
        data.forEach(System.out::println);
    }
}
