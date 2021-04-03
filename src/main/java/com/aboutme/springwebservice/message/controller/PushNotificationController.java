package com.aboutme.springwebservice.message.controller;

import com.aboutme.springwebservice.message.model.PushNotificationVO;
import com.aboutme.springwebservice.message.model.SueVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PushNotificationController {
    @GetMapping("/Message/pushNotification")
    public PushNotificationVO getPushNotification()
    {
        return null;
    }

    @GetMapping("/Message/PushNotificationList")
    public List<PushNotificationVO> getPushNotificationList()
    {
        return null;
    }
}
