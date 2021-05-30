package com.aboutme.springwebservice.auth.model;

import lombok.Data;

@Data
public class Key {
    private String kty;
    private String kid;
    private String use;
    private String alg;
    private String n;
    private String e;
}
