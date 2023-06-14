package com.davydovskyi.spotify.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.davydovskyi.spotify.model.ClusterizationRequest;
import com.davydovskyi.spotify.model.SongDTO;
import com.davydovskyi.spotify.services.ClusteringService;
import com.davydovskyi.spotify.services.SongsService;

import lombok.RequiredArgsConstructor;

@Controller("/playlists")
@RequiredArgsConstructor
public class PlaylistClusterizerController {

    private final SongsService songsService;

    @PostMapping("/kmeans")
    @CrossOrigin
    public ResponseEntity<Map<Integer, List<SongDTO>>> kmeans(@RequestBody ClusterizationRequest request) {
        var songs = songsService.getPlaylistsSongs(request.getPlaylists());

        var clusters = ClusteringService.kMeans(songs, request.getNumberOfClusters());
        return ResponseEntity.ok(clusters);
    }

    @PostMapping("/xmeans")
    @CrossOrigin
    public ResponseEntity<Map<Integer, List<SongDTO>>> xmeans(@RequestBody ClusterizationRequest request) {
        var songs = songsService.getPlaylistsSongs(request.getPlaylists());

        var clusters = ClusteringService.xMeans(songs, request.getNumberOfClusters());
        return ResponseEntity.ok(clusters);
    }

    @PostMapping("/gmeans")
    @CrossOrigin
    public ResponseEntity<Map<Integer, List<SongDTO>>> gmeans(@RequestBody ClusterizationRequest request) {
        var songs = songsService.getPlaylistsSongs(request.getPlaylists());

        var clusters = ClusteringService.gMeans(songs, request.getNumberOfClusters());
        return ResponseEntity.ok(clusters);
    }
}
