package com.inori.music.dto;

import com.inori.music.pojo.TblSong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {
    private TblSong song;
    private Boolean isFavorite;
}
