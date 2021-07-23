package com.aboutme.springwebservice.message.service;

import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {

    private Logger logger = LoggerFactory.getLogger(FCMService.class);

    public void sendMessage(Map<String, String> data, PushNotificationRequest request)throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithData(data, request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        logger.info("Sent message with data. Topic: " + request.getTopic() + ", " + response+ " msg "+jsonOutput);
    }

//    public void sendMessageWithoutData(PushNotificationRequest request)throws InterruptedException, ExecutionException {
//        Message message = getPreconfiguredMessageWithoutData(request);
//        String response = sendAndGetResponse(message);
//        logger.info("Sent message without data. Topic: " + request.getTopic() + ", " + response);
//    }

    public void sendMessageToToken(PushNotificationRequest request)throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response+ " msg "+jsonOutput);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .putHeader("apns-priority","5")
                .putHeader("apns-push-type","background")
                .setAps(Aps.builder().setCategory(topic).setContentAvailable(true).setSound("default").setBadge(1).build()).build();
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
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig).setNotification(
                        new Notification(request.getTitle(), request.getMessage()));
    }

    private Message.Builder getPreconfiguredMessageBuilderCustomDataWithTopic(Map<String, String> data, PushNotificationRequest request) {
        ApnsConfig apnsConfig = getApnsConfig(data.get(request.getTopic()));
        return Message.builder()
                .setApnsConfig(apnsConfig).setNotification(
                        new Notification(data.get("title"), data.toString()));
    }



}