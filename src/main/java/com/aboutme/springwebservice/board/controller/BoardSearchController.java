package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.model.BoardVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardSearchController {
    @GetMapping("/Board/latestList")
    public List<BoardVO> getLatestList()
    {
        return null;
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
