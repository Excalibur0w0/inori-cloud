package com.inori.music.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;
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

    @Column
    private String storePath;

    @Column
    private Integer duration;

    @Column
    private String imgPath;

    @Column
    private String fileType;

    @Transient
    private Boolean isFavorite;

}
