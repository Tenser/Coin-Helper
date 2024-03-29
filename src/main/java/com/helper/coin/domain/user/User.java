package com.helper.coin.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private String apiKey;

    private String secretKey;

    private int isOn;

    private int level;


    @Builder
    public User(String id, String name, String password, String apiKey, String secretKey, int level){
        this.id = id;
        this.name = name;
        this.password = password;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.isOn = 0;
        this.level = level;
    }

    public boolean isSamePassword(String password){
        if (this.password.equals(password)) return true;
        return false;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void updateInform(String name, String password){
        this.name = name;
        this.password = password;
    }

    public void on(){
        this.isOn = 1;
    }

    public void off(){
        this.isOn = 0;
    }

    public void changeLevel(int level){
        this.level = level;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
