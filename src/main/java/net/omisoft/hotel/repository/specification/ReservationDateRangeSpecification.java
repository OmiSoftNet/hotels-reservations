package net.omisoft.hotel.repository.specification;

import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThan;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Disjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.JoinFetch;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import net.omisoft.hotel.model.Reservation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

@JoinFetch(paths = "room", joinType = JoinType.INNER)
@Disjunction({
        @And({
                @Spec(path = "startDate", params = "from", spec = LessThanOrEqual.class),
                @Spec(path = "endDate", params = "from", spec = GreaterThanOrEqual.class)
        }),
        @And({
                @Spec(path = "startDate", params = "to", spec = LessThanOrEqual.class),
                @Spec(path = "endDate", params = "to", spec = GreaterThanOrEqual.class)
        }),
        @And({
                @Spec(path = "startDate", params = "from", spec = GreaterThan.class),
                @Spec(path = "endDate", params = "to", spec = LessThan.class)
        })
})
public interface ReservationDateRangeSpecification extends Specification<Reservation> {

}
