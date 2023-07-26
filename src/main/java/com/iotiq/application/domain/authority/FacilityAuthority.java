package com.iotiq.application.domain.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component("FacilityAuth")
public class FacilityAuthority {
    private FacilityAuthority() {
    }

    public static final GrantedAuthority VIEW = new SimpleGrantedAuthority("FACILITY_VIEW");
    public static final GrantedAuthority CREATE = new SimpleGrantedAuthority("FACILITY_CREATE");
    public static final GrantedAuthority UPDATE = new SimpleGrantedAuthority("FACILITY_UPDATE");
    public static final GrantedAuthority DELETE = new SimpleGrantedAuthority("FACILITY_DELETE");
}
