package com.inori.music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UploadProgressDTO {
    private boolean canContinue;
    private List<Long> badChunks;
}
