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

    @Scheduled(cron = "0 0 09 * * ?")
    public void sendAutoNotification(){
        PushNotificationRequest request = new PushNotificationRequest();
        request.setToken("fvsJWhOnNU8vigBvqEdhhH:APA91bHSbxnLUvyII20kXUUqQPoT4bo5M2Di0OE8C6nEP9-BRfpxxzd78IIgLuoKg3_BKMzOU-rGoJQCmVedKW8PNzWsL8ehPicZoUsDuSJY3LJQZ9Yym3qeKd-_GGegYefxfW580ViD ");
        request.setTitle("오늘의나");
        request.setTopic("global");
        request.setMessage("오늘은 질문 해주시는거죠?");
        pushNotificationService.sendPushNotification(request);
     }

}
