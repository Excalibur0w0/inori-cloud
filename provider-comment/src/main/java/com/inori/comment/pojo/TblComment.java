package com.inori.comment.pojo;

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
@ToString
@Table
@NoArgsConstructor
@AllArgsConstructor
public class TblComment {
    @Id
    @Column
    private String uuid;

    @Column
    private String content;

    @Column
    private String userId;

    @Column
    private String songId;

    @Column
    private Long like;

    @Column
    private Long dislike;

    @Column
    private String commentStatus;
}
