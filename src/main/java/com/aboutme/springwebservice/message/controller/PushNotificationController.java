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
        request.setToken("fWs7iOUjLkL5tExH0qq2Rl:APA91bFPh34RD63hy_6MZgVQ4nA927FKC6JjKgyoskBSnPBLgcWQSGXpPTsdLY7G8NvSRUTSA5VX4ummqzfF3UuFiA12mbXaJfJs7G6WEjGlR1tJs-LSBFiP5E4xl1Nca-orgDpTmnJ7");
        request.setTitle("오늘의나");
        request.setTopic("global");
        request.setMessage("오늘은 질문 해주시는거죠?");
        pushNotificationService.sendPushNotification(request);
     }

}
