package com.aboutme.springwebservice.domain.service;

import com.aboutme.springwebservice.domain.MailDto;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.domain.repository.UserInfoRepository;
import com.google.gson.JsonObject;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MailService {

    private JavaMailSender mailSender;

    private static final String FROM_ADDRESS = "aboutme2auth@gmail.com";
    private final UserInfoRepository infoRepository;

    @Transactional(readOnly = true)
    public String mailSend(MailDto mailDto) {
        JsonObject o = new JsonObject();
        Optional<UserInfo> info = infoRepository.findById((long)mailDto.getUserId());
        Date currentTime = new Date();

        String from = info.get().getEmail();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(MailService.FROM_ADDRESS);
        message.setFrom(from);
        message.setReplyTo(from);
        switch (mailDto.getReasonNum()){
            case 6:
                message.setSubject("[오늘의 나] 도배,스팸 관련 문의");
                break;
            case 7:
                message.setSubject("[오늘의 나] 서비스 관련 문의");
                break;
            case 8:
                message.setSubject("[오늘의 나] 신고 관련 문의");
                break;
            case 9:
                message.setSubject("[오늘의 나] 광고 제휴 문의");
                break;
            case 10:
                message.setSubject("[오늘의 나] 그외 기타 문의");
                break;
            default:
                o.addProperty("code",500);
                o.addProperty("message","요청 문의 목록이 잘못됐습니다.");
                break;
        }
        message.setText(mailDto.getMessage());
        message.setSentDate(currentTime);

       try {
           mailSender.send(message);
           o.addProperty("code",200);
           o.addProperty("message","메일 전송을 완료했습니다. 최대한 빠른 시일내에 답변드리겠습니다.");
           return o.toString();
       } catch (MailException e) {
            e.printStackTrace();
           o.addProperty("code",500);
           o.addProperty("message","메일 전송에 실패 했습니다. 다시 시도해주세요.");
           return o.toString();
       }

    }
}
