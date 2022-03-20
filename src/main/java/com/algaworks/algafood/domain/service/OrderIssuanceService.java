package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.OrderRestaurantNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.OrderRestaurant;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.model.UserSystem;
import com.algaworks.algafood.domain.repository.OrderRestaurantRepository;

@Service
public class OrderIssuanceService {
	
	@Autowired
	private OrderRestaurantRepository orderRestaurantRepository;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private CityRegistrationService cityRegistrationService;
	
	@Autowired
	private UserSystemRegistrationService userSystemRegistrationService;
	
	@Autowired
	private ProductRegistrationService productRegistrationService; 
	
	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;
	
	@Transactional
	public OrderRestaurant emit(OrderRestaurant order) {
		validateOrder(order);
		validateItems(order);
		
		order.setFreightRate(order.getRestaurant().getFreightRate());
		order.calculateTotalValue();
		
		return orderRestaurantRepository.save(order);
	}
	
	private void validateOrder(OrderRestaurant order) {
		City city = cityRegistrationService.findOrFail(order.getAddress().getCity().getId());
		UserSystem customer = userSystemRegistrationService.findOrFail(order.getCustomer().getId());
		Restaurant restaurant = restaurantRegistrationService.findOrFail(order.getRestaurant().getId());
		PaymentMethod paymentMethod = paymentMethodRegistrationService.findOrFail(order.getPaymentMethod().getId());
		
		order.getAddress().setCity(city);
		order.setCustomer(customer);
		order.setRestaurant(restaurant);
		order.setPaymentMethod(paymentMethod);
		
		if (restaurant.notAcceptPaymentMethod(paymentMethod)) {
			throw new BusinessException(String.format("Payment Method '%s' not accepted by this restaurant." , 
				paymentMethod.getDescription()));
		}
	}
	
	private void validateItems(OrderRestaurant order) {
		order.getOrderItems().forEach(item -> {
			Product product = productRegistrationService.findOrFail(order.getRestaurant().getId(), item.getProduct().getId());
			
			item.setOrder(order);
			item.setProduct(product);
			item.setUnitPrice(product.getPrice());
		});
	}
	
	public OrderRestaurant findOrFail(String xtid) {
		return orderRestaurantRepository.findByXtid(xtid).orElseThrow(() -> new OrderRestaurantNotFoundException(xtid));
	}

}
