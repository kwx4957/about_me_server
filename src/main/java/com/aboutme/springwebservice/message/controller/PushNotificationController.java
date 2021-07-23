package com.aboutme.springwebservice.message.controller;

import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.aboutme.springwebservice.message.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushNotificationController {

    @Autowired
    PushNotificationService pushNotificationService;

//    @GetMapping("/Message/PushNotificationList")
//    public ArrayList<PushNotificationVO> getPushNotificationList()
//    {
//        return null;
//    }

     @PostMapping("/Message/push")
     public void  sendPushNotification(@RequestBody PushNotificationRequest request ) throws Exception {
         pushNotificationService.sendPushNotification(request);
        }

    @Scheduled(cron = "0 0 08 * * ?")
    public void sendAutoNotification(){
        PushNotificationRequest request = new PushNotificationRequest();
        request.setToken("cwYMmejr-kAFrhXLbyuvoh:APA91bHgSN3Bk6grCAip5wTMt-PHToFvghOyFmPX-5cIrC0-Iuo8nmsUh6Ix7M_q7TsPoqpTFqi-v4RRdHjnr1Xv3yAtR0-bg1NPIQxPZHkC-Pz6USLteUb2S_ohsY5ugLdPwRQVwr-5");
        request.setTitle("오늘의나");
        request.setTopic("global");
        request.setMessage("오늘은 질문 해주시는거죠?");
        pushNotificationService.sendPushNotification(request);
     }

}
