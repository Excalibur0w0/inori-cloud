package com.inori.music.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileChunk {
    private String md5;
    private Long index;
    private Long total;
    private byte[] data;
}
