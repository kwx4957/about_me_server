package com.aboutme.springwebservice.board.service;

import com.aboutme.springwebservice.board.model.DE;
import com.aboutme.springwebservice.board.repository.QnACategory;
import com.aboutme.springwebservice.board.repository.QnACategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class qaService {
    @Autowired
    QnACategoryRepository qnACategoryRepository;

    public DE get(DE de) {
        QnACategory a = qnACategoryRepository.getOne(new Long(1));

        DE res = new DE();
        res.setColor(a.getColor());
        res.setSeq(a.getSeq());
        res.setAuthor_id(a.getAuthor_id());
        res.setTitle_id(a.getTitle_id());
        return res;
    }
}
