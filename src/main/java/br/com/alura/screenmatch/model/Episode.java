package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer number;
    private Double rating;
    private LocalDate releaseDate;

    public Episode(Integer season, EpisodeModel episodeModel) {
        this.season = season;
        this.title = episodeModel.title();
        this.number = episodeModel.number();

        try {
            this.rating = Double.valueOf(episodeModel.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(episodeModel.releaseDate());
        } catch (DateTimeParseException e) {
            this.releaseDate = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "season=" + season +
                ", title=" + title +
                ", number=" + number +
                ", rating=" + rating +
                ", releaseDate=" + releaseDate;
    }

}
