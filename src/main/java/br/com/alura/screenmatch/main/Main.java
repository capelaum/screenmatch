package br.com.alura.screenmatch.main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.SeasonModel;
import br.com.alura.screenmatch.model.SeriesModel;
import br.com.alura.screenmatch.service.ConsumeApiService;
import br.com.alura.screenmatch.service.DataConverterService;

public class Main {

    private Scanner scanner = new Scanner(System.in);

    private ConsumeApiService consumer = new ConsumeApiService();

    private DataConverterService converter = new DataConverterService();

    private static final String ADDRESS = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "6585022c";

    public void showMenu() {
        System.out.println("Digite o nome da série para buscar:");

        try {
            String seriesName = URLEncoder.encode(scanner.nextLine(), "UTF-8");
            String json = consumer.getData(ADDRESS + seriesName + "&apikey=" + API_KEY);

            SeriesModel seriesData = converter.getData(json, SeriesModel.class);
            // System.out.println(seriesData);

            List<SeasonModel> seasons = new ArrayList<>();

            // Dados de uma temporada
            for (int i = 1; i <= seriesData.totalSeasons(); i++) {
                json = consumer.getData(ADDRESS + seriesName + "&season=" + i + "&apikey=" + API_KEY);
                SeasonModel seasonData = converter.getData(json, SeasonModel.class);
                seasons.add(seasonData);
            }

            // seasons.forEach(System.out::println);

            // Lambda (Consumer)
            // seasons.forEach(this::showSeasonEpisodes);

            /* List<EpisodeModel> episodesData = seasons.stream()
                    .flatMap(s -> s.episodes().stream())
                    .collect(Collectors.toList());
            
            System.out.println("\nTop 10 episódios de " + seriesData.title() + "\n");
            
            episodesData.stream()
                    .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                    .peek(e -> System.out.println("Primeiro filtro (N/A): " + e))
                    .sorted(Comparator.comparing(EpisodeModel::rating).reversed())
                    .peek(e -> System.out.println("Ordenação: " + e))
                    .limit(10)
                    .peek(e -> System.out.println("Limite: " + e))
                    .map(e -> e.title().toUpperCase())
                    .peek(e -> System.out.println("Maiuscula: " + e))
                    .forEach(System.out::println); */

            System.out.println("--------------------------");
            System.out.println("\nTodos episódios de " + seriesData.title() + "\n");

            List<Episode> episodes = seasons.stream()
                    .flatMap(s -> s.episodes().stream().map(e -> new Episode(s.number(), e)))
                    .collect(Collectors.toList());

            episodes.forEach(System.out::println);

            System.out.println("--------------------------");
            System.out.println("Digite o nome do episódio para buscar:");
            var titlePart = scanner.nextLine();
            Optional<Episode> episode = episodes.stream()
                    .filter(e -> e.getTitle().toLowerCase().contains(titlePart.toLowerCase()))
                    .findFirst();

            if (episode.isPresent()) {
                System.out.println(episode.get().getTitle());
            }

            if (episode.isEmpty()) {
                System.out.println("Não foi encontrado nenhum episódio com esse nome");
            }

            /* System.out.println("--------------------------");
            System.out.println("A partir de que ano você deseja ver os episódios?");
            int year = scanner.nextInt();
            scanner.nextLine();
            System.out.println("--------------------------");
            
            LocalDate searchDate = LocalDate.of(year, 1, 1);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            episodes.stream()
                    .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
                    .forEach(e -> System.out
                            .println("Temporada " + e.getSeason() + " - " + e.getTitle() + " - "
                                    + e.getReleaseDate().format(formatter))); */

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void showSeasonEpisodes(SeasonModel season) {
        System.out.println("\n-----------------");
        System.out.println("Temporada " + season.number());
        System.out.println("-----------------");
        season.episodes().forEach(e -> System.out.println(e.title()));
    }
}
