package br.com.alura.screenmatch.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonModel(
        @JsonAlias("Season") Integer number,
        @JsonAlias("Episodes") List<EpisodeModel> episodes) {

}
