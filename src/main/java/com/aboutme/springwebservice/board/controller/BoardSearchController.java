package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.model.BoardVO;
import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import com.aboutme.springwebservice.board.service.BoardSearchService;
import com.aboutme.springwebservice.domain.repository.UserInfoRepository;
import com.aboutme.springwebservice.mypage.model.response.ResponseProgressing;
import com.aboutme.springwebservice.mypage.service.MyPageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class BoardSearchController {

    private final BoardSearchService boardSearchService;
    private final MyPageService myPageService;

    public UserInfoRepository userInfoRepository;

    @GetMapping("/Board/latestList/{userId}")
    public ResponseBoardList getLatestList(@PathVariable("userId") int userId,@RequestParam(value="color", required = false) String color)
    {
        int _color = 0;
        ResponseBoardList res = new ResponseBoardList();
        if(!userInfoRepository.existsById((long)userId)){
            return new ResponseBoardList(400, "해당 유저가 존재하지 않습니다.", null);
        }

        if (color == null) {
            _color = -1;
        }
        else {
            switch (color) {
                case "red":
                    _color = 0;
                    break;
                case "yellow":
                    _color = 1;
                    break;
                case "green":
                    _color = 2;
                    break;
                case "pink":
                    _color = 3;
                    break;
                case "purple":
                    _color = 4;
                    break;
                default:
                    res.setCode(400);
                    res.setMessage("color 입력이 잘 못 되었습니다.");

                    return res;
            }
        }
        List postList = boardSearchService.getLatestPost(userId, _color);
        if(postList.size()>0){
            res.setCode(200);
            res.setMessage("OK");
            res.setPostList(postList);
        }
        else{
            res.setCode(200);
            res.setMessage("데이터가 없습니다");
        }

        return res;
    }

    @GetMapping("/Board/currentHotList/{userId}")
    public ResponseBoardList getCurrentHotList(@PathVariable("userId") int userId,@RequestParam(value="color", required = false) String color)
    {
        String _color = color == null ? "no" :color;
        if(!userInfoRepository.existsById((long)userId)){
            return new ResponseBoardList(400, "해당 유저가 존재하지 않습니다.", null);
        }
        ResponseBoardList res = new ResponseBoardList();
        List postList = boardSearchService.getHotPosts(userId,_color);

        if(postList.size()>0){
            res.setCode(200);
            res.setMessage("OK");
            res.setPostList(postList);
        }
        else{
            res.setCode(200);
            res.setMessage("데이터가 없습니다");
        }

        return res;
    }

    // 취향순 글 보기
    @GetMapping("/Board/latestList/Category/{userId}")
    public ResponseBoardList getLatestListSearchedByCategory(@PathVariable("userId") Long userId,@RequestParam(value="color", required = false) String color)
    {
        String _color = color == null ? "no" :color;
        ResponseBoardList res = new ResponseBoardList();

        if(!userInfoRepository.existsById(userId)){
            return new ResponseBoardList(400, "해당 유저가 존재하지 않습니다.", null);
        }

        List postList = boardSearchService.getMyPopularList(userId,_color);


        if(postList.size()>0){
            res.setCode(200);
            res.setMessage("OK");
            res.setPostList(postList);
        }
        else{
            res.setCode(200);
            res.setMessage("데이터가 없습니다");
        }
        return res;
    }
    @GetMapping("/Board/{userId}/search")
    public ResponseBoardList getSearchList(@PathVariable("userId") int userId,@RequestParam(value="keyword", required = false) String keyword){

        String isTrim = keyword==null ? "" : keyword.trim();
        ResponseBoardList res = new ResponseBoardList();
        if(!userInfoRepository.existsById((long)userId)){
            return new ResponseBoardList(400, "해당 유저가 존재하지 않습니다.", null);
        }

        if(keyword==null||isTrim.equals("")||keyword.length()<2|| isTrim.length()<2){
            return new ResponseBoardList(400, "두 글자 이상의 검색어를 입력해주세요", null);
        }

        List postList = boardSearchService.getSearchList(userId,keyword);
        if(postList.size()>0){
            res.setCode(200);
            res.setMessage("OK");
            res.setPostList(postList);
        }
        else{
            res.setCode(200);
            res.setMessage("데이터가 없습니다");
        }
        return res;
    }
}
