package com.aboutme.springwebservice.message.controller;

import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.aboutme.springwebservice.message.model.response.PushNotificationResponse;
import com.aboutme.springwebservice.message.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     public ResponseEntity  sendPushNotification(@RequestBody PushNotificationRequest request ) throws Exception {
         pushNotificationService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(),"http://3.36.188.237:8080/Board/dailyColors/1"), HttpStatus.OK);
        }

}
