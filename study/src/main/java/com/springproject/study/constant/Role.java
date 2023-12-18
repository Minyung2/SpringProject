package com.springproject.study.constant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Role {
    REGISTER_USER, GUEST, USER, ADMIN, CEO, BAN, WITHDRAWAL;
}
