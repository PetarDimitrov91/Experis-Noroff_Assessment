package models;

public class Movie {
    private final int id;
    private final String name;
    private final int year;
    private final String[] keywords;
    private final double rating;
    private final double price;
    private int timesPurchased;

    public Movie(int id, String name, int year, String[] keywords, double rating, double price) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.keywords = keywords;
        this.rating = rating;
        this.price = price;
        this.timesPurchased = 0;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }


    public int getId() {
        return id;
    }

    public int getTimesPurchased() {
        return this.timesPurchased;
    }

    public void incrementTimesPurchased() {
        this.timesPurchased += 1;
    }

    public String toString() {
        return String.format("Movie id: %d, Movie name: %s, Movie year: %d, keywords: %s, Movie rating: %.2f, Movie price: %.2f Times Purchased: %d",
                getId(), getName(), getYear(), String.join(",", getKeywords()), getRating(), getPrice(), getTimesPurchased());
    }
}
