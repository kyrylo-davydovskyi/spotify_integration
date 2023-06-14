package com.davydovskyi.spotify.services;

import smile.clustering.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.davydovskyi.spotify.model.SongDTO;
import com.davydovskyi.spotify.model.SongData;

public class ClusteringService {

    private static List<SongData> prepareData(List<SongDTO> songs) {
        return songs.stream()
                .map(song -> new SongData(song, new double[] { song.getPopularityOfSong(), song.getDurationInMs() }))
                .collect(Collectors.toList());
    }

    private static Map<Integer, List<SongDTO>> processClusterData(List<SongData> songDataList,
            CentroidClustering<double[], double[]> clusters) {
        return songDataList.stream()
                .collect(Collectors.groupingBy(songData -> clusters.predict(songData.getData()),
                        Collectors.mapping(SongData::getSong, Collectors.toList())));
    }

    public static Map<Integer, List<SongDTO>> kMeans(List<SongDTO> songs, Integer clusterSize) {
        List<SongData> songDataList = prepareData(songs);
        var clusters = KMeans.fit(songDataList.stream().map(SongData::getData).toArray(double[][]::new), clusterSize);
        return processClusterData(songDataList, clusters);
    }

    public static Map<Integer, List<SongDTO>> xMeans(List<SongDTO> songs, Integer clusterSize) {
        List<SongData> songDataList = prepareData(songs);
        var clusters = XMeans.fit(songDataList.stream().map(SongData::getData).toArray(double[][]::new), clusterSize);
        return processClusterData(songDataList, clusters);
    }

    public static Map<Integer, List<SongDTO>> gMeans(List<SongDTO> songs, Integer clusterSize) {
        List<SongData> songDataList = prepareData(songs);
        var clusters = GMeans.fit(songDataList.stream().map(SongData::getData).toArray(double[][]::new), clusterSize);
        return processClusterData(songDataList, clusters);
    }

}
