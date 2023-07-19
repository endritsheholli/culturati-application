package com.iotiq.application.messages.exhibit;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.domain.Exhibit_;
import com.iotiq.commons.message.request.PageableRequest;
import com.iotiq.commons.message.request.SearchRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static com.iotiq.commons.util.NullHandlerUtil.setIfNotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ExhibitFilter extends PageableRequest implements SearchRequest<Exhibit> {
    
    String name;
    
    @Override
    public Specification<Exhibit> buildSpecification() {
        return nameIsLike(name);
    }
    private Specification<Exhibit> nameIsLike(String name) {
        return (root, query, cb) ->
                setIfNotNull(getName(), () -> cb.like(cb.lower(root.get(Exhibit_.NAME)), "%" + name.toLowerCase() + "%"));
    }
}
