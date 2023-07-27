package com.iotiq.application.domain.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component("NavPointAuth")
public class NavPointAuthority {
    private NavPointAuthority(){
    }

    public static final GrantedAuthority VIEW = new SimpleGrantedAuthority("NAV_POINT_VIEW");
    public static final GrantedAuthority CREATE = new SimpleGrantedAuthority("NAV_POINT_CREATE");
    public static final GrantedAuthority UPDATE = new SimpleGrantedAuthority("NAV_POINT_UPDATE");
    public static final GrantedAuthority DELETE = new SimpleGrantedAuthority("NAV_POINT_DELETE");
}
