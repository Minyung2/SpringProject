package com.springproject.study.user;

import com.springproject.study.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {
    //    @Id
//    @SequenceGenerator(
//            name = "ID_SEQ_GENERATOR",
//            sequenceName = "ID_SEQ",
//            initialValue = 1, allocationSize = 50
//    )
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,
//            generator = "ID_SEQ_GENERATOR")
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
