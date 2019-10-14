package com.inori.music.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class TblSheetSong {
    @Id
    @Column
    private String uuid;

    @Column
    private String songId;

    @Column
    private String shtId;
}
