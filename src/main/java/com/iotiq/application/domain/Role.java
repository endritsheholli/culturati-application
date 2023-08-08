package com.iotiq.application.domain;

import com.iotiq.application.domain.authority.ExhibitionAuthority;
import com.iotiq.application.domain.authority.FacilityAuthority;
import com.iotiq.application.domain.authority.NavPointAuthority;
import com.iotiq.application.domain.authority.GeoSpatialDataAuthority;
import com.iotiq.user.domain.authorities.UserManagementAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role implements com.iotiq.user.domain.authorities.Role {

    SUPER_ADMIN(
            new SimpleGrantedAuthority("ROLE_SUPER_ADMIN")
    ),
    ADMIN(
            new SimpleGrantedAuthority("ROLE_ADMIN"),


            UserManagementAuthority.VIEW,
            UserManagementAuthority.CREATE,
            UserManagementAuthority.UPDATE,
            UserManagementAuthority.DELETE,
            UserManagementAuthority.CHANGE_PASSWORD,

            ExhibitionAuthority.VIEW,
            ExhibitionAuthority.CREATE,
            ExhibitionAuthority.UPDATE,
            ExhibitionAuthority.DELETE,

            FacilityAuthority.VIEW,
            FacilityAuthority.CREATE,
            FacilityAuthority.UPDATE,
            FacilityAuthority.DELETE,

            NavPointAuthority.VIEW,
            NavPointAuthority.CREATE,
            NavPointAuthority.UPDATE,
            NavPointAuthority.DELETE,

            GeoSpatialDataAuthority.VIEW,
            GeoSpatialDataAuthority.CREATE,
            GeoSpatialDataAuthority.DELETE
            
    ),
    CURATOR(),
    VISITOR();

    final List<GrantedAuthority> authorities;

    Role(GrantedAuthority... authorities) {
        this.authorities = List.of(authorities);
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
