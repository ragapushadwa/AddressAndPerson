package com.ragajulan.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Address {
	@Column(nullable = false)
	private String street;
	@NonNull
	private String city;
	@NonNull
	private String state;
	@NonNull
	private String postalCode;
	@NonNull
	private String countryCode;

}
