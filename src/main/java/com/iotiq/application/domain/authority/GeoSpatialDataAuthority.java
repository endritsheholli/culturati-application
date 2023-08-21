package com.iotiq.application.domain.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component("GeoSpatialDataAuth")
public class GeoSpatialDataAuthority {
    private GeoSpatialDataAuthority() {
    }

    public static final GrantedAuthority VIEW = new SimpleGrantedAuthority("GEO_SPATIAL_DATA_VIEW");
    public static final GrantedAuthority CREATE = new SimpleGrantedAuthority("GEO_SPATIAL_DATA_CREATE");
    public static final GrantedAuthority DELETE = new SimpleGrantedAuthority("GEO_SPATIAL_DATA_DELETE");
}
