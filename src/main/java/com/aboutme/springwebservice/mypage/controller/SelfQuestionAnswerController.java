package com.aboutme.springwebservice.mypage.controller;

import com.aboutme.springwebservice.mypage.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

@RestController
@AllArgsConstructor
public class SelfQuestionAnswerController {
    @Autowired
    public SelfQuestRepository selfQuestRepository;

    private final SelfQuestService selfQuestService;
    @RequestMapping(value="/MyPage/10Q10A/answer",method = RequestMethod.POST,produces = "application/json; charset=utf8")
    public String createSelfQnA(@RequestBody String param) throws JsonProcessingException {
        //초기 질문들을 담아서 리턴
        ObjectMapper om = new ObjectMapper();
        SelfRequestVO sq = om.readValue(param,SelfRequestVO.class);
        QuestionAnswerDTO qaDto = new QuestionAnswerDTO();

        JsonObject js = new JsonObject();
        for(int i=0;i<sq.getAnswerLists().size();i++) {
            int j=i+1;
            qaDto.setUser(sq.getUser());
            qaDto.setTheme(sq.getTheme());
            qaDto.setTitle(sq.getAnswerLists().get(i).getQuestion());
            qaDto.setAnswer(sq.getAnswerLists().get(i).getAnswer());
            qaDto.setStage(sq.getStage());
            qaDto.setLevels(sq.getAnswerLists().get(i).getLevel());
            js.addProperty("result ", selfQuestService.createSelfQuestionAnswer(qaDto));
        }
        return js.toString();
    }

    @RequestMapping(value="/MyPage/10Q10A/updateAnswer",method = RequestMethod.PUT,produces = "application/json; charset=utf8")
    public String updateSelfQnA(@RequestBody String param) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
        SelfRequestVO sq = om.readValue(param,SelfRequestVO.class);
        QuestionAnswerDTO qaDto = new QuestionAnswerDTO();

        JsonObject js = new JsonObject();
        qaDto.setUser(sq.getUser());
        qaDto.setTheme(sq.getTheme());
        qaDto.setTitle(sq.getAnswerLists().get(0).getQuestion());
        qaDto.setAnswer(sq.getAnswerLists().get(0).getAnswer());
        qaDto.setStage(sq.getStage());
        qaDto.setLevels(sq.getAnswerLists().get(0).getLevel());
        js.addProperty("result ", selfQuestService.updateSelfQuestionAnswer(qaDto));

        return js.toString();
    }

    @DeleteMapping("/MyPage/10Q10A")
    void deleteSeleQuestionAnswer(@RequestParam int userid)
    {
        //delete가 필요 한가?
    }

    @RequestMapping(value = "Mypage/10Q10A/listDetail", method = RequestMethod.POST,produces = "application/json; charset=utf8")
    public String getSelfQnAList(@RequestBody String param) throws JsonProcessingException {
        //전체 조회 로직도 여기서 만들어야 되려나?
        ObjectMapper om = new ObjectMapper();
        SelfRequestVO sq = om.readValue(param,SelfRequestVO.class);
        QuestionAnswerDTO qaDto = new QuestionAnswerDTO();

        qaDto.setUser(sq.getUser());
        qaDto.setStage(sq.getStage());
        qaDto.setTheme(sq.getTheme());

        return selfQuestService.getSelfQuestList(qaDto);
    }

    @RequestMapping(value = "Mypage/10Q10A/theme", method = RequestMethod.POST,produces = "application/json; charset=utf8")
    public String getStageList(@RequestBody String param) throws JsonProcessingException {
        //전체 조회 로직도 여기서 만들어야 되려나?
        ObjectMapper om = new ObjectMapper();
        SelfRequestVO sq = om.readValue(param,SelfRequestVO.class);
        return selfQuestService.getThemeList(sq.getUser());
    }

    //TODO: 자문자답리스트, 관심리스트, 진행도 표출
}
