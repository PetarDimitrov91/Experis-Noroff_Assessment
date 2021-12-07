import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) {
        List<Movie> movies = null;

        try {
            movies = readMovies();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static List<Movie> readMovies() throws IOException {
        //if you want to start the program properly,maybe you must change the directory of the String path matching the directory of the file on your computer.
        String path = "D:\\Programmieren\\Assessment_Second_Phase\\Movie product data\\Products.txt";

        List<Movie> movies = new ArrayList<>();

        BufferedReader movieReader = new BufferedReader(new FileReader(path));

        String regex = "(?<id>^\\d+), (?<name>[A-Za-z0-9 .:'()&]+).(?<year>\\d+). (?<keywords>[A-Za-z, -]+)(?<rating>[0-9.]+),(?<price>[0-9.]+)";
        Pattern pattern = Pattern.compile(regex);

        String line = movieReader.readLine();
        while (line != null) {
            String rawProduct = line;
            Matcher matcher = pattern.matcher(rawProduct);

            if (matcher.find()) {
                movies.add(createMovie(matcher));
            }

            line = movieReader.readLine();
        }

        return movies;
    }

    private static Movie createMovie(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String name = matcher.group("name");
        int year = Integer.parseInt(matcher.group("year"));
        String[] keywords = matcher.group("keywords").split("[\\s,]+");
        double rating = Double.parseDouble(matcher.group("rating"));
        double price = Double.parseDouble(matcher.group("price"));

        return new Movie(id, name, year, keywords, rating, price);
    }
}
