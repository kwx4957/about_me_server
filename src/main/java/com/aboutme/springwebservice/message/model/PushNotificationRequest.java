package com.aboutme.springwebservice.message.model;

import lombok.*;

@Getter
@NoArgsConstructor
public class PushNotificationRequest  {
    private String title;
    private String message;
    private String topic;
    private String token;

    @Builder
    public  PushNotificationRequest(String title, String message, String topic, String token){
        this.title = title;
        this.message = message;
        this.topic = topic;
        this.token = token;
    }
}
