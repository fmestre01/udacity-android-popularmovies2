package udacity.com.popularmovies2.movieresponse;

public enum Language {
    LANGUAGE_EN("en"),
    LANGUAGE_Hi("hi");

    private String value;

    Language(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}

