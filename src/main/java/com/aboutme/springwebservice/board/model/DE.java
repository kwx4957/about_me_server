package com.aboutme.springwebservice.board.model;

import com.aboutme.springwebservice.board.repository.QnACategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DE {
    private long seq;

    private long author_id;

    private long title_id;

    private int color;

    public QnACategory toEntity() {
        return QnACategory.builder()
                .author_id(author_id)
                .title_id(title_id)
                .color(color)
                .build();
    }
}
