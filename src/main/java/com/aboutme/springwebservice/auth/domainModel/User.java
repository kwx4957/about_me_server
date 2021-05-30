package com.aboutme.springwebservice.auth.domainModel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    private String userId;

    @Column
    private String accesToken;

    @Column
    private String refreshToken;

    @Column
    private Date updateDate;

    @Builder
    public User(String userId, String accesToken, String refreshToken, Date updateDate) {
        this.userId = userId;
        this.accesToken = accesToken;
        this.refreshToken = refreshToken;
        this.updateDate = updateDate;
    }
}
