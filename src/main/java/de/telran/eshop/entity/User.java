package de.telran.eshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    private final String SEQ_NAME = "user_seq";  //переменная-чтобы не нпзначать ID ручным способом

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME,allocationSize = 1)
    private Long id;

    private String name;

    private String password;

    private String email;

    private Boolean archive;

    @Enumerated(EnumType.STRING)  //    роли пользователя
    private Role role;

    //один пользователь - одна корзина. При удалении пользователя-корзину тоже удаляем
    @OneToOne(cascade = CascadeType.REMOVE)
    private Bucket bucket;

}
