package com.polovni_automobili.auta.domain;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table( name = "tokens" )
public class JwtToken {

    @Id
    private String token;

    private Long userId;
}