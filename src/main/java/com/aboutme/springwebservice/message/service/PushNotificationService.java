package com.aboutme.springwebservice.message.service;

import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.entity.CommonResponse;
import com.aboutme.springwebservice.message.entity.NotificationList;
import com.aboutme.springwebservice.message.model.PushNotificationRequest;
import com.aboutme.springwebservice.message.model.response.ResponseNotiList;
import com.aboutme.springwebservice.message.repository.NotificationRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
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
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public ResponseEntity<?extends BasicResponse> pushNotificationList(long userId){

        List<ResponseNotiList> responseNotiLists  = new ArrayList<>();
        UserInfo user = UserInfo.builder().seq(userId).build();
        List<NotificationList> notificationList = notificationRepository.findAllByAulthorId(user);

        for(NotificationList responseNoti:notificationList){
            responseNotiLists.add( ResponseNotiList.builder().color(this.convertColor(responseNoti.getColor()))
                                    .message(responseNoti.getMessage()).updateDate(this.converTime(responseNoti.getRegDate())).build());
        }

        return ResponseEntity.ok().body( new CommonResponse<List>(responseNotiLists));
    }

    public ResponseEntity<? extends BasicResponse> subscribePushNotifictaion(long userId){
        UserProfile user = userProfileRepository.findOneByUserID(userId);
        if(user.getPush_yn().equals("Y")){
            user.pushNo();
            userProfileRepository.save(user);
        }else{
            user.pushYes();
            userProfileRepository.save(user);
        }
       CommonResponse<String> response= new CommonResponse<String>(user.getPush_yn());
        return ResponseEntity.ok().body(response);
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

    public void sendPushNotificationWithTopic(PushNotificationRequest request) {
        try {
            fcmService.sendMessageWithTopic(getSamplePayloadData(), request);
        } catch (Exception e) {
            logger.error(e.getMessage()+ e.getCause());
        }
    }



    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("title", "오늘의나");
        pushData.put("message", "오늘도 질문에 답변 하실꺼죠?");
        pushData.put("topic", "notice");
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

    List<String> registrationTokens = new ArrayList<>();
    public List<String> getFcmToken(){
        List<UserProfile> token = userProfileRepository.findAll();
        for(int i=0;i< token.size();i++) {
            registrationTokens.add(token.get(i).getFcmToken());
            System.out.println(token.get(i).getFcmToken());
        }
        return registrationTokens;
    }
    public void zzz(List<String> registrationTokens,String topic) throws FirebaseMessagingException {
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(registrationTokens, topic);
        System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
    }

}