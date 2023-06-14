package com.davydovskyi.spotify.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClusterizationRequest {
    private int numberOfClusters;
    private List<String> playlists;
}
