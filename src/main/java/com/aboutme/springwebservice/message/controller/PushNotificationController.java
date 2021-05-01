package com.aboutme.springwebservice.message.controller;

import com.aboutme.springwebservice.message.model.PushNotificationVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class PushNotificationController {
    @GetMapping("/Message/pushNotification")
    public PushNotificationVO getPushNotification()
    {
        return null;
    }

    @GetMapping("/Message/PushNotificationList")
    public ArrayList<PushNotificationVO> getPushNotificationList()
    {
        return null;
    }

}
