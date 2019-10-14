package com.inori.music.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@ToString
@Table
@NoArgsConstructor
@AllArgsConstructor
public class TblSong {
    @Id
    @Column
    private String uuid;

    @Column
    private String songName;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private String songAuthor;

    @Column
    private String songUploader;

    @Column
    private String songAlbum;
}
