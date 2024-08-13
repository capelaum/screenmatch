package br.com.alura.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		String json = consumeApiService.getData("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);
		DataConverterService dataConverterService = new DataConverterService();
		SeriesModel seriesModel = dataConverterService.getData(json, SeriesModel.class);
		System.out.println(seriesModel);
	}

}
