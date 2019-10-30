package com.inori.comment.pojo;

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
    private Long likeCount;

    @Column
    private Long dislikeCount;

    @Column
    private String commentStatus;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Transient
    private String uname;

    @Transient
    private String avatar;
}
