package com.danjinae.web.HttpRequest.RequestDTO;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notice {
    private Integer id;
    private String content;
    private String from_date;
    private String to_date;
    private Integer cat_id;
}
