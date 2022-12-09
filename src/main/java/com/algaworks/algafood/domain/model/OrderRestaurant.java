package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.algaworks.algafood.domain.event.OrderCancelledEvent;
import com.algaworks.algafood.domain.event.OrderConfirmedEvent;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class OrderRestaurant extends AbstractAggregateRoot<OrderRestaurant> {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String xtid;

	@Column(nullable = false)
	private BigDecimal subTotal;

	@Column(nullable = false)
	private BigDecimal freightRate;

	@Column(nullable = false)
	private BigDecimal totalValue;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	StatusOrder status = StatusOrder.CRIETED;

	@CreationTimestamp
	private OffsetDateTime registrationDate;

	private OffsetDateTime confirmationDate;
	private OffsetDateTime cancellationDate;
	private OffsetDateTime deliveryDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_method_id", nullable = false)
	private PaymentMethod paymentMethod;

	@ManyToOne
	@JoinColumn(name = "restaurant_id", nullable = false)
	private Restaurant restaurant;

	@ManyToOne
	@JoinColumn(name = "customer_user_id", nullable = false)
	private UserSystem customer;

	@JsonIgnore
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	List<OrderItem> orderItems = new ArrayList<>();

	public void calculateTotalValue() {
		this.subTotal = getOrderItems().stream().map(item -> item.getTotalPrice()).reduce(BigDecimal.ZERO,
				BigDecimal::add);
		this.totalValue = this.subTotal.add(this.freightRate);

		this.totalValue = this.subTotal.add(this.freightRate);
	}

	public void confirm() {
		setStatus(StatusOrder.CONFIRMED);
		setConfirmationDate(OffsetDateTime.now());
		
		registerEvent(new OrderConfirmedEvent(this));
	}

	public void cancel() {
		setStatus(StatusOrder.CANCELED);
		setCancellationDate(OffsetDateTime.now());
		
		registerEvent(new OrderCancelledEvent(this));
	}

	public void delivery() {
		setStatus(StatusOrder.DELIVERED);
		setDeliveryDate(OffsetDateTime.now());
	}

	private void setStatus(StatusOrder newStatus) {
		if (getStatus().canNotChangeTo(newStatus)) {
			throw new BusinessException(String.format("Order status %s cannot be changed from %s to %s", getXtid(),
					getStatus().getDescription(), newStatus.getDescription()));
		}
		this.status = newStatus;
	}
	
	@PrePersist
	private void generateXtid() {
		setXtid(UUID.randomUUID().toString());
	}
}
