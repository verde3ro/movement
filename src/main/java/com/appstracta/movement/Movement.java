package com.appstracta.movement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "movements")
@AllArgsConstructor
@NoArgsConstructor
public class Movement implements Serializable {

	private static final long serialVersionUID = 3178679025482526449L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 24)
	private String idCustomer;

	@Column(nullable = false)
	private Long idAccount;

	@Column(nullable = false)
	private Long idCredit;

	@JsonIgnore
	@DateTimeFormat(pattern = "yyyy,MM,d") // Para el formateo de fechas
	private LocalDate createAt;

	@PrePersist
	private void prePersistNum() {
		this.createAt = LocalDate.now();
	}

}
