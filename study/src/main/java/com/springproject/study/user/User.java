package com.springproject.study.user;

import com.springproject.study.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String mobile;
}
