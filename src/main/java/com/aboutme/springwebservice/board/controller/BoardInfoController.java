package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.board.model.*;
import com.aboutme.springwebservice.board.model.CommentDTO;
import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import com.aboutme.springwebservice.board.model.response.ResponseComment;
import com.aboutme.springwebservice.board.model.response.ResponseDailyLists;
import com.aboutme.springwebservice.board.entity.QnACategory;
import com.aboutme.springwebservice.board.model.response.ResponsePost;
import com.aboutme.springwebservice.board.repository.*;
import com.aboutme.springwebservice.board.service.BoardDailyService;
import com.aboutme.springwebservice.board.service.BoardInfoService;
import com.aboutme.springwebservice.board.service.BoardCommentService;
import com.aboutme.springwebservice.domain.repository.UserInfoRepository;
import com.aboutme.springwebservice.mypage.service.UserLevelService;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.aboutme.springwebservice.board.model.BoardMetaInfoVO;
import com.aboutme.springwebservice.board.model.BoardVO;
import com.aboutme.springwebservice.board.model.ReplayVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class BoardInfoController {
    //TODO : list에서 담고 있는게 이 함수가 필요할까 확인 필요.

    public QnACategoryLevelRepository answerRepository;
    public QnACommentRepository commRepository;
    public QnACategoryRepository questionRepository;
    public UserInfoRepository infoRepository;
    public BoardCommentRepository boardCommentRepository;
    public DefaultEnquiryRepository defaultEnquiryRepository;

    private final BoardDailyService boardDailyService;
    private final UserLevelService levelService;
    private final BoardInfoService boardInfoService;
    private final BoardCommentService boardCommentService;

    @GetMapping("/Board/info")
    public ResponsePost getBoardInfo(@RequestParam("answer_id") long answerId, @RequestParam("user_id") long userId)
    {
        ResponsePost res = new ResponsePost();
        if(!answerRepository.existsById(answerId)){
            res.setCode(400);
            res.setMessage("해당 게시글이 존재하지 않습니다");

            return res;
        }

        Object post = boardInfoService.getPost(userId, answerId);
        List<CommentDTO> comments = boardCommentService.getCommentList(answerId);

        return new ResponsePost(200, "OK", post, comments);
    }

    //지난 응답 화면
    @GetMapping("/Board/pastResponse")
    public ResponseBoardList getPastResponse(@RequestParam("user_id") long userId, @RequestParam(value = "answer_id", required = false, defaultValue = "-1") long answerId, @RequestParam(value = "quest_id", required = false, defaultValue = "-1") long questId)
    {
        ResponseBoardList res = new ResponseBoardList();
        if((answerId == -1 && questId == -1) || (answerId != -1 && questId != -1)){
            res.setCode(400);
            res.setMessage("파라미터 입력이 잘못 되었습니다.");

            return res;
        }

        if(answerId == -1 && questId != -1){
            if(!defaultEnquiryRepository.existsById(questId)){
                res.setCode(200);
                res.setMessage("해당 질문이 존재하지 않습니다.");

                return res;
            }
            answerId = boardInfoService.getAnswerIdFromQuestId(userId, questId);


            List postList = boardInfoService.getPastResponse(answerId, true);
            if (postList.size() == 0) {
                res.setCode(200);
                res.setMessage("작성한 답변이 없습니다");

                return res;
            }
            res.setCode(200);
            res.setMessage("OK");
            res.setPostList(postList);
        }
        else {
            if (!answerRepository.existsById(answerId)) {
                res.setCode(400);
                res.setMessage("해당 게시글이 존재하지 않습니다");

                return res;
            }

            List postList = boardInfoService.getPastResponse(answerId, false);
            if (postList.size() == 0) {
                res.setCode(200);
                res.setMessage("작성한 답변이 없습니다");

                return res;
            }
            res.setCode(200);
            res.setMessage("OK");
            res.setPostList(postList);
        }

        return res;
    }


    @PostMapping("/Board/comment")
    public ResponseComment saveComment(@RequestBody RequestComment requestComment){
        long userId = requestComment.getAuthorId();
        long answerId = requestComment.getAnswerId();
        String comment = requestComment.getComment();
        ResponseComment res = new ResponseComment();
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setAuthorId(userId);
        commentDTO.setAnswerId(answerId);
        commentDTO.setComment(comment);

        if (!infoRepository.existsById(userId)) {
            res.setCode(400);
            res.setMessage("해당 유저가 존재하지 않습니다.");

            return res;
        }
        if (!answerRepository.existsById(answerId)) {
            res.setCode(400);
            res.setMessage("해당 게시글이 존재하지 않습니다.");

            return res;
        }

        CommentDTO c = boardCommentService.saveComment(commentDTO);

        return new ResponseComment(201, "OK", c);
    }

    @DeleteMapping("/Board/comment")
    public ResponseComment deleteComment(@RequestParam("commentId") long commentId, @RequestParam("userId") long userId) {
        ResponseComment res = new ResponseComment();
        CommentDTO commentDTO = new CommentDTO();

        if (!boardCommentRepository.existsById(commentId)) {
            res.setCode(400);
            res.setMessage("해당 댓글이 존재하지 않습니다.");

            return res;
        }
        if (!infoRepository.existsById(userId)) {
            res.setCode(400);
            res.setMessage("해당 유저가 존재하지 않습니다.");

            return res;
        }

        commentDTO.setCommentId(commentId);

        return boardCommentService.deleteComment(commentDTO, userId);
    }

    @GetMapping("/Board/info/replayList")
    public List<ReplayVO> getBoardReplayListInfo(@RequestParam BoardMetaInfoVO boardMetaInfoVO)
    {
        return null;
    }

    //매일 받는 5색 질문 중 하나 저장
    @PostMapping(value = "/Board/dailyColors" , produces = "application/json;charset=UTF-8")
    public ResponseDailyLists saveDailyColors(@RequestBody BoardVO vo){
        DailyQuestDTO questDTO =  new DailyQuestDTO();
        DailyAnswerDTO answerDTO = new DailyAnswerDTO();
        ResponseDailyLists r = new ResponseDailyLists();

        if(!infoRepository.existsById((long)vo.getUser())){
            r.setCode(400);
            r.setMessage("해당 유저가 존재하지 않습니다.");
            return r;
        }
        else{
            switch (vo.getColor()){
                case "red":
                    questDTO.setColor(0);
                    break;
                case "yellow":
                    questDTO.setColor(1);
                    break;
                case "green":
                    questDTO.setColor(2);
                    break;
                case "pink":
                    questDTO.setColor(3);
                    break;
                case "purple":
                    questDTO.setColor(4);
                    break;
                default:
                    r.setCode(500);
                    r.setMessage("색상입력이 잘못되었습니다. 다시 시도해주세요");
                    return r;
            }
            questDTO.setUser(vo.getUser());
            questDTO.setTitle(vo.getTitle());

            int cardSeq = boardDailyService.setDailyStep1(questDTO);

            if(cardSeq == 0){
                r.setCode(500);
                r.setMessage("데이터 내역이 존재하지 않습니다. 다시 시도해주세요");
            }
            answerDTO.setCategory_seq(cardSeq);
            answerDTO.setLevel(vo.getLevel());
            answerDTO.setAnswer(vo.getAnswer());
            if(!vo.getShare_yn().equals("N")){
                answerDTO.setShare('Y');
            }
            else
                answerDTO.setShare('N');

            System.out.println(cardSeq);
            System.out.println( answerDTO.getLevel());
            System.out.println( answerDTO.getAnswer());

            levelService.updateUserLevelExperience(vo.getUser(),questDTO.getColor(),false);//진행도 증가

            return boardDailyService.setDailyStep2(answerDTO);
        }

    }
    //매일 받는 5색 질문 중 하나 수정
    @PutMapping("/Board/dailyColors")
    public ResponseDailyLists updateDailyColors(@RequestBody DailyAnswerDTO ans){
            ResponseDailyLists r = new ResponseDailyLists();
        if(!questionRepository.existsById((long)ans.getCategory_seq())){
            r.setCode(404);
            r.setMessage("해당 질답에 대한 정보가 존재하지 않습니다.");
            return r;
        }
        else return boardDailyService.setDailyStep2(ans);
    }
    //매일 받는 5색 질문 중 하나 삭제
    @DeleteMapping("/Board/dailyColors/{cardSeq}")
    public String deleteDailyColors(@PathVariable(name="cardSeq") long categoryLevelSeq){
        JsonObject o = new JsonObject();
        Optional<QnACategoryLevel> quest = answerRepository.findById(categoryLevelSeq);
        long categoryId = quest.get().getCategoryId();
        Optional<QnACategory> category = questionRepository.findById(categoryId);


        if(quest.isPresent() && category.isPresent()){
            commRepository.delCardComment(categoryLevelSeq);
            answerRepository.delCardAnswer(categoryLevelSeq);
            List<QnACategoryLevel> otherAnswers = answerRepository.findByCategoryId(categoryId);
            if(otherAnswers.isEmpty()){
                questionRepository.delCardQuestion(category.get().getSeq());
            }
            levelService.updateUserLevelExperience( category.get().getAuthorId(), category.get().getColor(),true); //진행도 감소
            o.addProperty("code",200);
            o.addProperty("message","삭제완료");
        }
        else {
            o.addProperty("code",404);
            o.addProperty("message","해당 질답에 대한 정보가 존재하지 않습니다.");
        }
        return o.toString();
    }
    //매일 받는 5색 질문 리스트 제공
    @GetMapping(path = "/Board/dailyColors/{user}")
    public ResponseDailyLists getDailyColors(HttpServletResponse response,@PathVariable(name = "user") int userId){
        if(!infoRepository.existsById((long)userId)){
            ResponseDailyLists r = new ResponseDailyLists();
            r.setCode(400);
            r.setMessage("해당 유저가 존재하지 않습니다.");
            return r;
        }
        else return boardDailyService.getDailyColors(userId);
    }
}
