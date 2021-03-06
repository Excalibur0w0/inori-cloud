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
@Entity
@Table
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TblSheetUser {
    @Id
    @Column
    private String uuid;

    @Column
    private String userId;

    @Column
    private String shtId;
}
