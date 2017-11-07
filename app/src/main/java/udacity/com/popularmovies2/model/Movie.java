package udacity.com.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            Movie movie = new Movie(source);
            return movie;
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    String id;
    String author;
    String content;
    String url;

    public Movie(Parcel source) {
        this.id = source.readString();
        this.author = source.readString();
        this.content = source.readString();
        this.url = source.readString();
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
    }

}
