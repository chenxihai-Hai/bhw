package com.bhw.thirdparty.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_OUTCOME")
@Data
public class OutComeBean implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String type;

    private String content;

    private String school;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createtime;
}
