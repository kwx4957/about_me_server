package com.aboutme.springwebservice.auth.kakao.model;

import lombok.Data;

@Data
public class KaKaoUser {

    public long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;


    @Data
    public class Properties {

        public String nickname;
        public String profile_image;
        public String thumbnail_image;

    }

    @Data
    public class KakaoAccount {

        public Boolean profile_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
        public Boolean has_age_range;
        public Boolean age_range_needs_agreement;
        public Boolean has_birthday;
        public Boolean birthday_needs_agreement;
        public Boolean has_gender;
        public Boolean gender_needs_agreement;


        @Data
        public class Profile {

            public String nickname;
            public String thumbnail_image_url;
            public String profile_image_url;
            public boolean is_default_image;

        }
    }

}