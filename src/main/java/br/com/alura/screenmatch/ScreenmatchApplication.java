package br.com.alura.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.screenmatch.service.ConsumeApiService;

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
		json = consumeApiService.getData("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
	}

}
