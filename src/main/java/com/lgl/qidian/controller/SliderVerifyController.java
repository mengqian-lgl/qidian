package com.lgl.qidian.controller;

import com.lgl.qidian.service.SliderVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther 刘广林
 */
@RestController
public class SliderVerifyController {
    @Autowired
    SliderVerifyService sliderVerifyService;

    @PostMapping("/sliderverify")
    public void sliderverify(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sliderVerifyService.verifySlider(request, response);
    }
}
