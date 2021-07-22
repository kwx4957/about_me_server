package com.aboutme.springwebservice.message.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PushNotificationRequest {
    private String title;
    private String message;
    private String topic;
    private String token;
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String  getTopic(){
        return topic;
    }
    public String getToken(){
        return token;
    }
}
