package com.iotiq.application.messages.register;

import com.iotiq.application.domain.Role;
import com.iotiq.user.messages.request.UserCreateDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VisitorRegistrationRequest extends UserCreateDto {
    public VisitorRegistrationRequest() {
        super();
        this.setRole(Role.VISITOR);
    }
}