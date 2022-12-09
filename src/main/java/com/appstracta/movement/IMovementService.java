package com.appstracta.movement;

import com.appstracta.response.MovementResponse;

import java.util.List;
import java.util.Optional;

public interface IMovementService {

	List<MovementResponse> findAll();

	Optional<Movement> findById(Long id);

	Movement save(Movement movement);

}
