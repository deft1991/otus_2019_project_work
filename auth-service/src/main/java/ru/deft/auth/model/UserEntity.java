package ru.deft.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/*
 * Created by sgolitsyn on 12/11/19
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;
    @Column(name = "TELEGRAM_USER_ID")
    Integer telegramId;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "NICK_NAME")
    private String nickName;
    @Column(name = "PASSWORD")
    private String password;

    @Transient
    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public UserEntity(String username, Integer telegramId, String password) {
        this.username = username;
        this.telegramId = telegramId;
        this.password = password;
    }

    public UserEntity(Integer telegramId, String username, String password) {
        this.telegramId = telegramId;
        this.username = username;
        this.password = password;
    }
}
