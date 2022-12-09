package com.appstracta.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovementResponse implements Serializable {
	private static final long serialVersionUID = -5090489667336263689L;

	private Long id;

	private String idCustomer;

	private String customer;

	private Long idAccount;

	private Long idCredit;

	private LocalDate createAt;

}
