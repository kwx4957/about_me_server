package com.aboutme.springwebservice.message.controller;

import com.aboutme.springwebservice.board.model.BoardVO;
import com.aboutme.springwebservice.message.model.SueVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SueController {
    @GetMapping("/Message/sueList")
    public List<SueVO> getSueList()
    {
        return null;
    }

    @PostMapping("/Message/sue")
    public void createSue(@RequestBody SueVO sueVO)
    {
    }
}
