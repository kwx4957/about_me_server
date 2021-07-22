package com.aboutme.springwebservice.message.service;

import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PushNotificationService {

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    public void sendPushNotification(PushNotificationRequest request) {
        try {
            fcmService.sendMessage(getSamplePayloadData(), request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
                fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("title", "오늘의나");
        pushData.put("message", "오늘도 질문에 답변 하실꺼죠?");
        pushData.put("url", "http://3.36.188.237:8080/Board/dailyColors/1");
        pushData.put("badge", "1");
        pushData.put("topic", "global");
        return pushData;
    }

//    private Map<String, String> getSamplePayloadDataWithSpecificJsonFormat() {
//        Map<String, String> pushData = new HashMap<>();
//        Map<String, String> data = new HashMap<>();
//        ArrayList<Map<String, String>> payload = new ArrayList<>();
//        Map<String, String> article_data = new HashMap<>();
//
//        pushData.put("title", "jsonformat");
//        pushData.put("message", "itsworkingkudussssssssssssssssssssssssssssssssssss");
//        pushData.put("image", "qqq");
//        pushData.put("timestamp", "fefe");
//        article_data.put("article_data", "ffff");
//        payload.add(article_data);
//        pushData.put("payload", String.valueOf(payload));
//        data.put("data", String.valueOf(pushData));
//        return data;
//
//    }

}