package com.danjinae.web.mgfee.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewMgFeeRequest {
    private String address;
    private Integer aptId;
    private Integer fee;
    private Integer catId;
    private String content;
    private String date;
}
