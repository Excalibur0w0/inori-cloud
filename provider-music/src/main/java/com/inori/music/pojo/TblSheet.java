package com.inori.music.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString
@Table
@NoArgsConstructor
@AllArgsConstructor
public class TblSheet {
    @Id
    @Column
    private String uuid;

    @Column(unique = true)
    private String shtName;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private String shtCreator;

    @Column
    private String shtDesc;
}
