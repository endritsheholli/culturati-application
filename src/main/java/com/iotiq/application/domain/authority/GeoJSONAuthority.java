package com.iotiq.application.domain.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component("GeoJSONAuth")
public class GeoJSONAuthority {
    private GeoJSONAuthority() {
    }

    public static final GrantedAuthority VIEW = new SimpleGrantedAuthority("GEO_JSON_VIEW");
    public static final GrantedAuthority CREATE = new SimpleGrantedAuthority("GEO_JSON_CREATE");
    public static final GrantedAuthority DELETE = new SimpleGrantedAuthority("GEO_JSON_DELETE");
}
