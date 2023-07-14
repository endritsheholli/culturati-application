package com.iotiq.application.domain.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component("ExhibitionAuth")
public class ExhibitionAuthority {

    private ExhibitionAuthority() {
    }

    public static final GrantedAuthority VIEW = new SimpleGrantedAuthority("EXHIBITION_VIEW");
    public static final GrantedAuthority CREATE = new SimpleGrantedAuthority("EXHIBITION_CREATE");
    public static final GrantedAuthority UPDATE = new SimpleGrantedAuthority("EXHIBITION_UPDATE");
    public static final GrantedAuthority DELETE = new SimpleGrantedAuthority("EXHIBITION_DELETE");
}
