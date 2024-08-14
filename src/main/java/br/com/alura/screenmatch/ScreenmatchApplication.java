package br.com.alura.screenmatch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.screenmatch.model.EpisodeModel;
import br.com.alura.screenmatch.model.SeasonModel;
import br.com.alura.screenmatch.model.SeriesModel;
import br.com.alura.screenmatch.service.ConsumeApiService;
import br.com.alura.screenmatch.service.DataConverterService;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumeApiService consumeApiService = new ConsumeApiService();
		DataConverterService dataConverterService = new DataConverterService();

		// Dados da Série
		String json = consumeApiService.getData("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		SeriesModel seriesData = dataConverterService.getData(json, SeriesModel.class);
		System.out.println(seriesData);

		// Dados de um episódio
		json = consumeApiService.getData("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=6585022c");
		EpisodeModel episodeData = dataConverterService.getData(json, EpisodeModel.class);
		System.out.println(episodeData);

		List<SeasonModel> seasons = new ArrayList<>();

		// Dados de uma temporada
		for (int i = 1; i < seriesData.totalSeasons(); i++) {
			json = consumeApiService
					.getData("https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=6585022c");
			SeasonModel seasonData = dataConverterService.getData(json, SeasonModel.class);
			seasons.add(seasonData);
		}

		seasons.forEach(System.out::println);
	}

}
