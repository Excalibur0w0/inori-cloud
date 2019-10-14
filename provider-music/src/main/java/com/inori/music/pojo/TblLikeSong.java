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

@Data
@Entity
@ToString
@Table
@NoArgsConstructor
@AllArgsConstructor
public class TblLikeSong {
    @Id
    @Column
    private String uuid;

    @Column
    private String userId;

    @Column
    private String songId;
}
