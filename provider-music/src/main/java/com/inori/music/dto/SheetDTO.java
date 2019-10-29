package com.inori.music.dto;

import com.inori.music.pojo.TblSheet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Deprecated
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SheetDTO {
    private TblSheet sheet;
    // 通常imgPath为sheet下 一首歌的封面
    private String imgPath;
}
