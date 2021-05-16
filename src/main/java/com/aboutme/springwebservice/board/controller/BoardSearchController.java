package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.model.BoardVO;
import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import com.aboutme.springwebservice.board.service.BoardSearchService;
import com.aboutme.springwebservice.domain.repository.UserInfoRepository;
import com.aboutme.springwebservice.mypage.model.response.ResponseProgressing;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class BoardSearchController {

    private final BoardSearchService boardSearchService;

    public UserInfoRepository userInfoRepository;

    @GetMapping("/Board/latestList")
    public ResponseBoardList getLatestList()
    {
        List postList = boardSearchService.getLatestPost();

        ResponseBoardList res = new ResponseBoardList();

        res.setCode(200);
        res.setMessage("OK");
        res.setPostList(postList);

        return res;
    }

    @GetMapping("/Board/currentHotList")
    public ResponseBoardList getCurrentHotList()
    {
        List postList = boardSearchService.getHotPosts();

        ResponseBoardList res = new ResponseBoardList();

        res.setCode(200);
        res.setMessage("OK");
        res.setPostList(postList);

        return res;
    }

    @GetMapping("/Board/latestList/Category/{userId}")
    public ResponseBoardList getLatestListSearchedByCategory(@PathVariable("userId") Long userId)
    {
        if(!userInfoRepository.existsById(userId)){
            return new ResponseBoardList(500, "해당 유저가 존재하지 않습니다.", null);
        }

        List postList = boardSearchService.getMyPopularList();

        ResponseBoardList res = new ResponseBoardList();

        res.setCode(200);
        res.setMessage("OK");
        res.setPostList(postList);

        return res;
    }
}
