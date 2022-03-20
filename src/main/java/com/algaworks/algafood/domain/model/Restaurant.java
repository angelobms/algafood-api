package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.ValueZeroAddDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValueZeroAddDescription(valueField = "freightRate", descriptionField = "name", mandatoryDescription="Free shipping")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String name;
	
	@NotNull
	@PositiveOrZero
	//@PositiveOrZero(message = "{freightRate.invalid}")
	//@FreightRate
	//@Multiple(number = 5)
	@Column(name = "freight_rate", nullable = false)
	private BigDecimal freightRate;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.KitchenId.class)
	@NotNull
//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "kitchen_id", nullable = false)
	private Kitchen kitchen;
	
	@Embedded
	private Address address;
	
	private Boolean active = Boolean.TRUE;
	
	private Boolean open = Boolean.FALSE;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime registrationDate;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime updateDate;
	
	@ManyToMany
	@JoinTable(name = "restaurant_payment_method", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
	private Set<PaymentMethod> paymentMethods = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "restaurant_responsible_user", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<UserSystem> responsibles = new HashSet<>();
	
	public void activate() {
		setActive(true);
	}
	
	public void inactivate() {
		setActive(false);
	}
	
	public void open() {
		setOpen(true);
	}
	
	public void close() {
		setOpen(false);
	}
	
	public boolean addPaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethods().add(paymentMethod);
	}
	
	public boolean removePaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethods().remove(paymentMethod);
	}
		
	public boolean addResponsible(UserSystem user) {
	    return getResponsibles().add(user);
	}
	
	public boolean removeResponsible(UserSystem user) {
	    return getResponsibles().remove(user);
	}

	public boolean acceptsPaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethods().contains(paymentMethod);
	}
	
	public boolean notAcceptPaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethods().contains(paymentMethod);
	}
}
