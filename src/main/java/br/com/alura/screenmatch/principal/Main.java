package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.lang.classfile.constantpool.IntegerEntry;
import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    Scanner sc = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibirMenu() {
        System.out.print("Digite o nome da série para busca: ");
        var nomeSerie = sc.nextLine();
        var json = consumoApi.obterDadosApi(ENDERECO + nomeSerie.replaceAll(" ", "+") + API_KEY);
        DadosSerie dadosSerie = conversor.converterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();
        // Mostrando todas as temporadas
        for (int i = 1; i <= dadosSerie.TotalTemporadas(); i++) {
            json = consumoApi.obterDadosApi(ENDERECO + nomeSerie.replaceAll(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.converterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

//        for (int i = 0; i < dadosSerie.TotalTemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).Episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).Titulo());
//            }
//        }

        // Lambda para adaptar o for acima
        temporadas.forEach(t -> t.Episodios().forEach(e -> System.out.println(e.Titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.Episodios().stream())
                        .collect(Collectors.toList());

        System.out.println("\nTop 5 episódios: ");
        dadosEpisodios.stream()
                .filter(e -> !e.Avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::Avaliacao).reversed())
                .limit(5)
                .peek(e -> System.out.println("Titulo em minúsculo: " + e.Titulo()))
                .map(e -> e.Titulo().toUpperCase())
                .forEach(System.out::println);

        // Todos os episódios
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.Episodios().stream()
                        .map(d -> new Episodio(t.NumeroTemporada(), d))
                ).collect(Collectors.toList());

        System.out.println("\nTodos os episódios:");
        episodios.forEach(System.out::println);

        System.out.print("\nDigite o nome do episódio que deseja buscar: ");
        var trechoTitulo= sc.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()) {
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        } else {
            System.out.println("Episódio não encontrado!");
        }

//        System.out.print("\nA partir de que ano você deseja ver os episódios? ");
//        var ano = sc.nextInt();
//        sc.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        System.out.println("\nEpisódios na data " + dataBusca);
//        episodios.stream()
//                .filter(e -> e != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episódio: " + e.getTitulo() +
//                                " Data Lançamento: " + e.getDataLancamento().format(formatador)
//                ));

        Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println("\n" + avaliacaoPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("\nMédia: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
    }
}
