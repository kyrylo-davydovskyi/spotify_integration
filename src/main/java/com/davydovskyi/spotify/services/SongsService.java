package com.davydovskyi.spotify.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.davydovskyi.spotify.model.SongDTO;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongsService {
    private final SpotifyApi spotifyApi;
    private final Converter<Track, SongDTO> converter;


    public List<SongDTO> getPlaylistsSongs(List<String> playlistIds) {
        return playlistIds.stream()
                .map(this::getPlaylistSongs)
                .flatMap(List::stream)
                .toList();
    }

    @SneakyThrows
    public List<SongDTO> getPlaylistSongs(String playlistId) {
        log.info("Searching for playlist with id: {}", playlistId);
        var request = spotifyApi.getPlaylistsItems(playlistId).build();
        var response = request.execute();

        return Arrays.asList(response.getItems()).stream()
                .map(item -> item.getTrack().getId())
                .map(trackId -> spotifyApi.getTrack(trackId).build())
                .map(songRequest -> {
                    try {
                        return songRequest.execute();
                    } catch (Exception e) {
                        log.error("Error while getting song by id: {}", songRequest, e);
                        return null;
                    }
                })
                .map(songResponse -> converter.convert(songResponse))
                .toList();
    }
}
