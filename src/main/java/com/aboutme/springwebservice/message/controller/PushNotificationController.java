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
         return;
        }

    @Scheduled(cron = "0 0 08 * * ?")
    public void sendAutoNotification(){
        PushNotificationRequest request = new PushNotificationRequest();
        request.setToken("chsac4qNy0vPjIZWzhPtrH:APA91bGIpM3-24VuDldkFdfU28lqW2LKuN_EBQvkhJwEBuzldr6zEmfDxsw3daf2yFRv09cJrVdAE9zTn2v9xnl-Nz5YDwP5M7lRPcYMi8ekmwsdLIOhWbJ2gd_O4oms8S7sdx8YBvik");
        request.setTitle("오늘의나");
        request.setTopic("global");
        request.setMessage("오늘은 질문 해주시는거죠?");
        pushNotificationService.sendPushNotification(request);
     }

}
