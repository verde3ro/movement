package com.appstracta.movement;

import com.appstracta.response.MovementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MovementController {

	private final IMovementService service;


	@GetMapping
	public ResponseEntity<List<MovementResponse>> findAll() {
		List<MovementResponse> credits = service.findAll();

		if (credits.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(credits);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Movement>> findById(@PathVariable Long id) {
		Optional<Movement> credit = service.findById(id);

		return ResponseEntity.ok(credit);
	}

}
