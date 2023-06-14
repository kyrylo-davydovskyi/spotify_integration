package com.davydovskyi.spotify.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import se.michaelthelin.spotify.enums.AlbumType;

@Data
public class SongDTO {
    private String songName;
    private String releaseDate;
    private String albumName;
    private AlbumType albumType;
    private List<String> artistNames;
    private int popularityOfSong;
    private int durationInMs;
}
