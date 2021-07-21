package com.aboutme.springwebservice.message.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PushNotificationResponse {

    private int status;
    private String message;
    private String url;
    private int badge = 1;

    public PushNotificationResponse(int status, String url) {
        this.status = status;
        this.url = url;
    }
}