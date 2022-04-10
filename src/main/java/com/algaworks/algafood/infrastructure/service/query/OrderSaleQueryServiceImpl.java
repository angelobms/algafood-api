package com.algaworks.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.OrderRestaurant;
import com.algaworks.algafood.domain.model.StatusOrder;
import com.algaworks.algafood.domain.model.dto.DailySale;
import com.algaworks.algafood.domain.service.OrderSaleQueryService;

@Repository
public class OrderSaleQueryServiceImpl implements OrderSaleQueryService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DailySale> findDailySales(DailySaleFilter filter, String timeOffset) {
		var builder = entityManager.getCriteriaBuilder();
		var query = builder.createQuery(DailySale.class);
		var root = query.from(OrderRestaurant.class);
		var predicates = new ArrayList<Predicate>();

		var functionConvertTzRegistrationDate = builder.function(
				"convert_tz", Date.class, root.get("registrationDate"),
				builder.literal("+00:00"), builder.literal(timeOffset));

		var functionDateRegistrationDate = builder.function("date", Date.class, functionConvertTzRegistrationDate);

		var selection = builder.construct(DailySale.class, 
				functionDateRegistrationDate, 
				builder.count(root.get("id")),
				builder.sum(root.get("totalValue")));
		
		if (filter.getRestaurantId() != null) {
			predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
		}

		if (filter.getStartRegistrationDate() != null) {
			predicates
					.add(builder.greaterThanOrEqualTo(root.get("registrationDate"), filter.getStartRegistrationDate()));
		}

		if (filter.getEndRegistrationDate() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("registrationDate"), filter.getEndRegistrationDate()));
		}

		predicates.add(root.get("status").in(StatusOrder.CONFIRMED, StatusOrder.DELIVERED));

		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateRegistrationDate);

		return entityManager.createQuery(query).getResultList();
	}

}
