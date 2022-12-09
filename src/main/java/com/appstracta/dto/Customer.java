package com.appstracta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer implements Serializable {
	private static final long serialVersionUID = -638807739368234537L;

	private String id;

	private String firstName;

	private String lastName;

	private String telephone;

	private String type;

}
