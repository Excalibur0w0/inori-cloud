package com.inori.music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UploadProgressDTO {
    private boolean canContinue;
    private long chunkIndex;
    private long chunkTotal;
}
