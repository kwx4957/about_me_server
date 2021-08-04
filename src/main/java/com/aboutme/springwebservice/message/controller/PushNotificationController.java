package com.aboutme.springwebservice.message.controller;

import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.aboutme.springwebservice.message.service.FCMService;
import com.aboutme.springwebservice.message.service.PushNotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
public class PushNotificationController {

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    FCMService fcmService;

    @GetMapping("Message/Push/{userId}/List") //
    public ResponseEntity<? extends BasicResponse> getPushNoticationList(@PathVariable("userId") long userId){
        return pushNotificationService.pushNotificationList(userId);
    }

     @PostMapping("/Message/push")
     public void  sendPushNotification(@RequestBody PushNotificationRequest request ) throws Exception {
         pushNotificationService.sendPushNotification(request);
        }

     @PostMapping("/Message/FCMToken")
     public void insertFCMToken(@RequestParam String token,@RequestParam long userId){
        fcmService.saveFCMToken(token,userId);
     }

    @Scheduled(cron = "0 0 09 * * ?")
    public void sendAutoNotification(){
        PushNotificationRequest request= PushNotificationRequest.builder()
                .message("오늘은 질문 해주시는거죠?")
                .title("오늘의나")
                .token("fWs7iOUjLkL5tExH0qq2Rl:APA91bFPh34RD63hy_6MZgVQ4nA927FKC6JjKgyoskBSnPBLgcWQSGXpPTsdLY7G8NvSRUTSA5VX4ummqzfF3UuFiA12mbXaJfJs7G6WEjGlR1tJs-LSBFiP5E4xl1Nca-orgDpTmnJ7")
                .topic("global").build();
        pushNotificationService.sendPushNotification(request);
     }

}
