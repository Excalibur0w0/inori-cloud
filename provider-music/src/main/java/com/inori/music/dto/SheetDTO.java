package com.inori.music.dto;

import com.inori.music.pojo.TblSheet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SheetDTO {
    private TblSheet tblSheet;
    private String authorName;
}
