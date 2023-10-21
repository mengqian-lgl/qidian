package com.lgl.qidian.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lgl.qidian.service.GetAdjudicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @auther 刘广林
 */
@RestController
public class GetAdjudicationController {

    @Autowired
    GetAdjudicationService getAdjudicationService;

    @PostMapping("/getadjudication")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getadjudication() throws Exception{
        String string = getAdjudicationService.service();
        return string;
    }


}
