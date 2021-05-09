package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.model.BoardVO;
import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import com.aboutme.springwebservice.board.service.BoardSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BoardSearchController {

    @Autowired
    private BoardSearchService boardSearchService;

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
    public List<BoardVO> getCurrentHotList()
    {
        return null;
    }

    @GetMapping("/Board/latestList/Category")
    public List<BoardVO> getLatestListSeacrhedByCategory(String userId)
    {
        return null;
    }
}
