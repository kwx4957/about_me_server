package com.aboutme.springwebservice.message.service;

import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserInfoRepository;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class FCMService {

    private  Logger logger = LoggerFactory.getLogger(FCMService.class);

    @Autowired
    private ObjectMapper objectMapper;

    private final UserProfileRepository userProfileRepository;

//    @Transactional
//    public void callUrl() throws JsonProcessingException {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "josn", Charset.forName("UTF-8")));
//
//        Map<String ,Object> map = new HashMap<>();
//        map.put("FCMtoken","put your fcmtoken");
//        String para = objectMapper.writeValueAsString(map);
//
//        HttpEntity entity = new HttpEntity(para,headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> responseEntity = restTemplate.exchange("http://127.0.0.1/Message/FCMToken" , HttpMethod.POST,entity, String.class);
//    }

    @Transactional
    public void saveFCMToken(String fcmToken,long userID){
           UserProfile user = userProfileRepository.findOneByUserID(userID);
           user.savefcmToken(fcmToken);
           userProfileRepository.save(user);
    }


    public void sendMessage(Map<String, String> data, PushNotificationRequest request)throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithData(data, request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        logger.info("Sent message with data. Topic: " + request.getTopic() + ", " + response+ " msg "+jsonOutput);
    }

    public void sendMessageToToken(PushNotificationRequest request)throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response+ " msg "+jsonOutput);
    }

    public void sendMessageToTokenWithData(Map<String, String> data,PushNotificationRequest request)throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithData(data,request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response+ " msg "+jsonOutput);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private ApnsConfig getApnsConfig(String topic) {
        //왜 인지 모르겠지만 헤더값을 추가할 경우 알림이 디바이스로 가질 않음
        //.putHeader("apns-priority","5")
        //.putHeader("apns-push-type","background")
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setContentAvailable(true).setBadge(1).setSound("default").build()).build();
    }

    private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
                .build();
    }

    private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        ApnsConfig apns = getApnsConfig(request.getTopic());
        return Message.builder()
                .setApnsConfig(apns).setNotification(
                        new Notification(request.getTitle(), request.getMessage()));
    }

}