package br.com.alura.screenmatch.main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.alura.screenmatch.model.SeasonModel;
import br.com.alura.screenmatch.model.SeriesModel;
import br.com.alura.screenmatch.service.ConsumeApiService;
import br.com.alura.screenmatch.service.DataConverterService;

public class Main {

    private Scanner scanner = new Scanner(System.in);

    private ConsumeApiService consumer = new ConsumeApiService();

    private DataConverterService converter = new DataConverterService();

    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "6585022c";

    public void showMenu() {
        System.out.println("Digite o nome da série para buscar:");

        try {
            String seriesName = URLEncoder.encode(scanner.nextLine(), "UTF-8");
            String json = consumer.getData(ADDRESS + seriesName + "&apikey=" + API_KEY);

            SeriesModel seriesData = converter.getData(json, SeriesModel.class);
            System.out.println(seriesData);

            List<SeasonModel> seasons = new ArrayList<>();

            // Dados de uma temporada
            for (int i = 1; i <= seriesData.totalSeasons(); i++) {
                json = consumer.getData(ADDRESS + seriesName + "&season=" + i + "&apikey=" + API_KEY);
                SeasonModel seasonData = converter.getData(json, SeasonModel.class);
                seasons.add(seasonData);
            }

            seasons.forEach(System.out::println);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
