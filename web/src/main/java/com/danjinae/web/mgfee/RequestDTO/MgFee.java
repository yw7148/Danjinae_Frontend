package com.danjinae.web.mgfee.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MgFee {
    private Integer userId;
    private Integer aptId;
    private Integer fee;
    private Integer catId;
}