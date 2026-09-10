package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		ConverteDados conversor = new ConverteDados();

		var json = consumoApi.obterDadosApi("https://www.omdbapi.com/?t=the+mentalist&apikey=6585022c");
		System.out.println("JSON da Série: " + json);

		DadosSerie dadosSerie = conversor.converterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);

		List<DadosTemporada> temporadas = new ArrayList<>();

		// Mostrando todas as temporadas
		for (int i = 1; i <= dadosSerie.TotalTemporadas(); i++) {
			json = consumoApi.obterDadosApi("https://www.omdbapi.com/?t=the+mentalist&season=" + i + "&apikey=6585022c");
			DadosTemporada dadosTemporada = conversor.converterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

		json = consumoApi.obterDadosApi("https://www.omdbapi.com/?t=the+mentalist&season=1&episode=1&apikey=6585022c");
		DadosEpisodio dadosEpisodio = conversor.converterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);
	}
}
