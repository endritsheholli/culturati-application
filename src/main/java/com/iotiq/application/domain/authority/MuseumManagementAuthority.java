package com.iotiq.application.domain.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component("MuseumManagementAuth")
public class MuseumManagementAuthority {
    private MuseumManagementAuthority() {
    }
    
    public static final GrantedAuthority VIEW = new SimpleGrantedAuthority("MUSEUM_MANAGEMENT_VIEW");
    public static final GrantedAuthority CREATE = new SimpleGrantedAuthority("MUSEUM_MANAGEMENT_CREATE");
    public static final GrantedAuthority UPDATE = new SimpleGrantedAuthority("MUSEUM_MANAGEMENT_UPDATE");
    public static final GrantedAuthority DELETE = new SimpleGrantedAuthority("MUSEUM_MANAGEMENT_DELETE");
}
