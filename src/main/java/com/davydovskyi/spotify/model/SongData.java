package com.davydovskyi.spotify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongData {
    private SongDTO song;
    private double[] data;
}
