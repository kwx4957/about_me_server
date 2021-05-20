package com.aboutme.springwebservice.message;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.entity.CommonResponse;
import com.aboutme.springwebservice.message.entity.DefaultReasonList;
import com.aboutme.springwebservice.message.entity.UserVoc;
import com.aboutme.springwebservice.message.model.SueVO;
import com.aboutme.springwebservice.message.repository.SueRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@RunWith(SpringRunner.class)
@Service
public class SueServiceTest {

    //@Autowired
   // private MockMvc mvc;
    private SueRepository sueRepository;
    private QnACategoryLevelRepository qnACategoryLevelRepository;
    @Test
    public ResponseEntity<?extends BasicResponse> sue(@RequestBody SueVO vo) {

        QnACategoryLevel qnACategoryLevel = qnACategoryLevelRepository.findById(vo.getTargetQuestionId())
                                                                      .orElseThrow(()-> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
        //임시Id
        UserInfo authorId = UserInfo.builder().seq(vo.getSuedUserId()).build();
        UserVoc userVoc= UserVoc.builder().reasonId(new DefaultReasonList(vo.getSueReason())).authorId(authorId).questionId(qnACategoryLevel).build();
        sueRepository.save(userVoc);
        return  ResponseEntity.ok().body( new CommonResponse<>());
    }

}
