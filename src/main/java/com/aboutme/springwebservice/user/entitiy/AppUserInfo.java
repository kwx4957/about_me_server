package com.aboutme.springwebservice.user.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class AppUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private Long naverUserId;

    @Column(length = 20, nullable = true)
    private String name;

    @Column(length = 255, nullable = true)
    private String email;

    @Column(length = 255, nullable = true)
    private String profileImage;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    public AppUserInfo(Long userId, String name, String email, String profileImage) {
        this.naverUserId = userId;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }
}
