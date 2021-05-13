package com.aboutme.springwebservice.domain.controller;

import com.aboutme.springwebservice.domain.MailDto;
import com.aboutme.springwebservice.domain.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class InquireController {

    private final MailService mailService;

    @PostMapping("/sendInquire")
    public String execMail(@RequestBody MailDto mailDto) {
       return mailService.mailSend(mailDto);
    }
}
