package com.davydovskyi.spotify;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import com.davydovskyi.spotify.model.SongDTO;
import com.davydovskyi.spotify.services.ClusteringService;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.specification.Track;

@SpringBootTest
@Slf4j
class SpotifyAnalyzerApplicationTests {

	@Autowired
	private SpotifyApi spotifyApi;
	@Autowired
	private Converter<Track, SongDTO> converter;

	// private Map<ArtistDTO, Map<String,

	@Test
	@SneakyThrows
	void contextLoads() {
		var request = spotifyApi.getPlaylistsItems("37i9dQZF1DXcfZ6moR6J0G").build();
		var response = request.execute();
		List<SongDTO> tracks = new ArrayList<>();
		for (var item : Arrays.asList(response.getItems())) {
			var trackId = item.getTrack().getId();
			var trackRequest = spotifyApi.getTrack(trackId).build();
			var trackResponse = trackRequest.execute();
			tracks.add(converter.convert(trackResponse));
		}

		var result = ClusteringService.clusterizeByPopularity(tracks);

		for (int i = 0; i < result.size(); i++) {
			System.out.println("Cluster " + (i + 1) + ":");
			List<SongDTO> cluster = result.get(i);
			for (SongDTO song : cluster) {
				System.out.println("- " + song.getSongName() + " by " + song.getArtistNames());
			}
			System.out.println("-----------------------------------------");
		}
	}

}
