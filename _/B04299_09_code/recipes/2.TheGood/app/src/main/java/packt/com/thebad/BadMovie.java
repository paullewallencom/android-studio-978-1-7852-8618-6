package packt.com.thebad;

/**
 * Created by mike on 26-08-15.
 */
public class BadMovie {
    public String title;
    public String genre;
    public String year;
    public String director;
    public String actors;
    public String imageUrl;

    public BadMovie(String title, String genre, String year, String director, String actors, String imageUrl){
        this.title = title;
        this.genre = genre;
        this.year =year;
        this.director = director;
        this.actors = actors;
        this.imageUrl = imageUrl;
    }
}
