package com.aboutme.springwebservice.message.controller;

import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.aboutme.springwebservice.message.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushNotificationController {

    @Autowired
    PushNotificationService pushNotificationService;
//
//    @GetMapping("/Message/PushNotificationList")
//    public ArrayList<PushNotificationVO> getPushNotificationList()
//    {
//        return null;
//    }

    //@Scheduled(cron= )
     @PostMapping("/Message/push")
     public void  sendPushNotification(@RequestBody PushNotificationRequest request ) throws Exception {
         pushNotificationService.sendPushNotification(request);
         return;
        }

}
