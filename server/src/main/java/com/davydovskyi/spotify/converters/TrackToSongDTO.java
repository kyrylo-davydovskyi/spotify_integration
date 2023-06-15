package com.davydovskyi.spotify.converters;

import java.util.Arrays;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.davydovskyi.spotify.model.SongDTO;

import se.michaelthelin.spotify.model_objects.specification.Track;

@Component
public class TrackToSongDTO implements Converter<Track, SongDTO> {

    @Override
    @Nullable
    public SongDTO convert(Track source) {
        var result = new SongDTO();
        result.setSongName(source.getName());
        result.setReleaseDate(source.getAlbum().getReleaseDate());
        result.setAlbumName(source.getAlbum().getName());
        result.setAlbumType(source.getAlbum().getAlbumType());
        result.setArtistNames(Arrays.asList(source.getArtists()).stream().map(artist -> artist.getName()).toList());
        result.setPopularityOfSong(source.getPopularity());
        result.setDurationInMs(source.getDurationMs());
        return result;
    }
    
}
