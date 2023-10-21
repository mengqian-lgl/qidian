package com.lgl.qidian.controller;

import com.lgl.qidian.service.AckAdjudicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 刘广林
 */
@RestController
public class AckAdjudicationController {

    @Autowired
    AckAdjudicationService ackAdjudicationService;

    @GetMapping("/ackadjudication/{is_ok}/{user_id}")
    public String ackAdjudication1(@PathVariable(name = "is_ok") String is_ok,
                                  @PathVariable(name = "user_id") String user_id){

        String string = ackAdjudicationService.service(is_ok, user_id);

        return string;
    }

}
