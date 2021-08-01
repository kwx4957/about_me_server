package com.aboutme.springwebservice.message.service;

import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.entity.CommonResponse;
import com.aboutme.springwebservice.message.entity.NotificationList;
import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.aboutme.springwebservice.message.model.response.ResponseNotiList;
import com.aboutme.springwebservice.message.repository.NotificationRepository;
import com.aboutme.springwebservice.mypage.model.response.ResponseCrushList;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PushNotificationService {

    private final Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    @Autowired
    private FCMService fcmService;
    private final NotificationRepository notificationRepository;

    @Transactional
    public ResponseEntity<?extends BasicResponse> pushNotificationList(long userId){

        List<ResponseNotiList> responseNotiLists  = new ArrayList<>();

        UserInfo user = UserInfo.builder().seq(userId).build();
        List<NotificationList> notificationList = notificationRepository.findAllByAulthorId(user);
        for(NotificationList responseNoti:notificationList){
            ResponseNotiList responseNotiList = ResponseNotiList.builder()
                                                                .color(this.convertColor(responseNoti.getColor()))
                                                                .message(responseNoti.getMessage())
                                                                .updateDate(this.converTime(responseNoti.getRegDate()))
                                                                .build();
            responseNotiLists.add(responseNotiList);
        }
        return ResponseEntity.ok().body( new CommonResponse<List>(responseNotiLists));
    }


    public void sendPushNotification(PushNotificationRequest request) {
        try {
            fcmService.sendMessage(getSamplePayloadData(), request);
        } catch (Exception e) {
            logger.error(e.getMessage()+ e.getCause());
        }
    }

    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
                fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationToTokenWithData(Map<String, String> data,PushNotificationRequest request) {
        try {
            fcmService.sendMessageToTokenWithData(data,request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("title", "오늘의나");
        pushData.put("message", "오늘도 질문에 답변 하실꺼죠?");
        pushData.put("topic", "global");
        return pushData;
    }

    private String convertColor(int color){
        String colorString="";
        switch (color) {
            case 0: colorString = "red"; break;
            case 1: colorString = "yellow"; break;
            case 2: colorString = "green"; break;
            case 3: colorString = "pink"; break;
            case 4: colorString = "purple"; break;
        }
        return colorString;
    }

    private String converTime(LocalDateTime update_date){
        String updateDate="";
        LocalDateTime currentTime= LocalDateTime.now();
        if((currentTime.getDayOfYear() - update_date.getDayOfYear())== 0) {
            if ((currentTime.getHour() - update_date.getHour()) > 0) {
                updateDate = "약 " + (currentTime.getHour() - update_date.getHour()) + "시간 전";
            } else if ((currentTime.getHour() - update_date.getHour()) == 0) {

                if ((currentTime.getMinute() - update_date.getMinute()) > 0) {
                    updateDate = "약 " + (currentTime.getMinute() - update_date.getMinute()) + "분 전";
                } else if ((currentTime.getMinute() - update_date.getMinute()) == 0) {
                    updateDate = " 방금 전";
                }
            }
        }
        else {
            return update_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return updateDate;
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