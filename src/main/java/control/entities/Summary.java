package control.entities;

public class Summary {
    private final int id;
    private int rating;
    private String description, date;

    public Summary(int id, int rating, String description, String date) {
        this.id = id;
        this.rating = rating;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
