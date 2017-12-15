package c4n.net.cataloguemovieuiux.Model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by c4n on 14/11/2017.
 */

public class MovieItem implements Serializable {

    public static String POSTER_URL = "http://image.tmdb.org/t/p/";
    private int id;
    private String title;
    private String description;
    private String releaseDate;
    private String movieRate;
    private String posterUrl;


    public MovieItem(JSONObject object){

        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String description = object.getString("overview");
            double movieRatet = object.getDouble("vote_average");
            String movieRate = new DecimalFormat("#.#").format(movieRatet);
            String releaseDate = object.getString("release_date");
            String posterUrl = object.getString("poster_path");
            posterUrl = POSTER_URL + "w185" + posterUrl;
            description = description.length() <= 5  ? description.substring(0,5)+"...":description;


            Log.d("movie poster", posterUrl);
            Log.d("movie title ", title);
            Log.d("movie description ", description);
            Log.d("movie release ", releaseDate);
            Log.d("movie rate ", movieRate);

            this.id = id;
            this.title = title;
            this.description = description;
            this.releaseDate = releaseDate;
            this.posterUrl = posterUrl;
            this.movieRate = movieRate;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPosterUrl() {
        return posterUrl;
    }
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getMovieRate() {
        return movieRate;
    }
    public void setMovieRate(String movieRate) {
        this.movieRate = movieRate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


}
