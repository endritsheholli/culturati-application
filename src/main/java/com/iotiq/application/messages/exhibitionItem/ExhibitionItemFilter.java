package com.iotiq.application.messages.exhibitionItem;

import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.domain.ExhibitionItem_;
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
public class ExhibitionItemFilter extends PageableRequest implements SearchRequest<ExhibitionItem> {
    String title;

    @Override
    public Specification<ExhibitionItem> buildSpecification() {
        return titleIsLike(title);
    }

    private Specification<ExhibitionItem> titleIsLike(String title) {
        return (root, query, cb) ->
                setIfNotNull(getTitle(), () -> cb.like(cb.lower(root.get(ExhibitionItem_.TITLE)), "%" + title.toLowerCase() + "%"));
    }
}
