package com.danjinae.web.complaint.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintProcess {
    private Integer cplId;
    private Integer mgrId;
    private String content;
}
