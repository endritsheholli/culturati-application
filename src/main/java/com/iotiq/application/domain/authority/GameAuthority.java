package com.iotiq.application.domain.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component("Game")
public class GameAuthority {
    private GameAuthority() {
    }

    public static final GrantedAuthority VIEW = new SimpleGrantedAuthority("GAME_VIEW");
    public static final GrantedAuthority CREATE = new SimpleGrantedAuthority("GAME_CREATE");
    public static final GrantedAuthority UPDATE = new SimpleGrantedAuthority("GAME_UPDATE");
}
