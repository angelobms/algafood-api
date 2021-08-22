package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class OrderRestaurant {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal subtotal;
	
	@Column(nullable = false)
	private BigDecimal freightRate;
	
	@Column(nullable = false)
	private BigDecimal totalValue;
	
	@Embedded
	private Address address;
	
	StatusOrder status;
	
	@CreationTimestamp
	private OffsetDateTime registrationDate;
	
	private OffsetDateTime confirmationDate;
	private OffsetDateTime cancellationDate;
	private OffsetDateTime deliveryDate;
	
	@ManyToOne
	@JoinColumn(name = "payment_method_id", nullable = false)
	private PaymentMethod paymentMethod;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id", nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
    @JoinColumn(name = "customer_user_id", nullable = false)
    private UserSystem customer;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<OrderItem> orderItems = new ArrayList<>();
	
}
