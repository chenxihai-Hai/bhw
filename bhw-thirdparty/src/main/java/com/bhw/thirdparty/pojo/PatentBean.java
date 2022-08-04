package com.bhw.thirdparty.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_PATENT")
@Data
public class PatentBean  implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer schoolid;

    private String patentcode;

    private String patentname;

    private String shenqingdate;

    private String falvzta;

    private String falvztab;

    private String falvztb;

    private String falvztbb;

    private String zhuquanx;

    private String gongkaihao;

    private String gongkaidate;

    private String dailijigou;

    private String dailiren;

    private String content;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createtime;
}
