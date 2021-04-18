package com.aboutme.springwebservice.mypage.controller;

import com.aboutme.springwebservice.mypage.model.QuestionAnswerDTO;
import com.aboutme.springwebservice.mypage.model.SelfQuestRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class SelfQuestionAnswerController {
    @Autowired
    private SelfQuestRepository selfQuestRepository;

    @PostMapping("/MyPage/10Q10A/answer")
    List<QuestionAnswerDTO> createSelfQuestionAnswer(@RequestBody QuestionAnswerDTO qaDTO){
        //초기 질문들을 담아서 리턴
        return null;
    }

    @PutMapping("/MyPage/10Q10A")
    void updateSelfQuestionAnswer(@RequestBody QuestionAnswerDTO questionAnswerVO)
    {
        //CREATE를 통해서 만들어진 질문에 답변을 다는 로직을 여기서 하면 될거 같음.
    }

    @DeleteMapping("/MyPage/10Q10A")
    void deleteSeleQuestionAnswer(@RequestParam int questionSeq)
    {
        //delete가 필요 한가?
    }

    @GetMapping("Mypage/10Q10A")
    List<QuestionAnswerDTO> getSelfQuestionAnswer(@RequestBody int questionSeq){
        //전체 조회 로직도 여기서 만들어야 되려나?
        return null;
    }

    //TODO: 자문자답리스트, 관심리스트, 진행도 표출
}
