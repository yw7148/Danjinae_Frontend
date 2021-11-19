package com.danjinae.web.notice.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notice {
    private Integer aptId;
    private String content;
    private String startDate;
    private String endDate;
    private Integer catId;
}
