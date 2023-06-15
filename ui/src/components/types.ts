export interface SongDTO {
    songName: string;
    releaseDate: string;
    albumName: string;
    albumType: string;
    artistNames: string[];
    popularityOfSong: number;
    durationInMs: number;
  }