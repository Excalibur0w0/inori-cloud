package com.example.demo;

import lombok.Data;
import lombok.ToString;
//import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@ToString
@Table(name = "demo")
//@Table(appliesTo = "tbl_demo",comment = "demo例子")
public class DemoEntity {

    @Id
    @Column
    private int uuid;


}
