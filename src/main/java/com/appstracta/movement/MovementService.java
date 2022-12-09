package com.appstracta.movement;

import com.appstracta.dto.Customer;
import com.appstracta.response.MovementResponse;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class MovementService implements IMovementService {

	private final IMovementRepository repository;
	private final WebClient.Builder webClientBuilder;

	TcpClient tcpClient = TcpClient
			.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
			.doOnConnected(connection -> {
				connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
				connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
			});

	@Override
	public List<MovementResponse> findAll() {
		List<MovementResponse> response = new ArrayList<>();
		List<Movement> movements = repository.findAll();

		if (!movements.isEmpty()) {
			WebClient client = webClientBuilder.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
					.baseUrl("http://service-customer")
					.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.defaultUriVariables(Collections.singletonMap("url", "http://service-customer"))
					.build();
			List<Customer> customers = client.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
							.path("/")
							.build())
					.retrieve().bodyToFlux(Customer.class).collectList().block();

			movements.forEach(movement -> {
				MovementResponse movementResponse = new MovementResponse();
				movementResponse.setId(movement.getId());
				movementResponse.setIdCustomer(movement.getIdCustomer());
				movementResponse.setCustomer("");
				movementResponse.setIdCredit(movement.getIdCredit());
				movementResponse.setIdAccount(movement.getIdAccount());
				String customer = customers.stream().filter(c -> movementResponse.getIdCustomer().equals(c.getId()))
						.findAny()
						.map(c2 -> String.format("%s %s", c2.getFirstName(), c2.getLastName()))
						.orElse(null);

				movementResponse.setCustomer(customer);

				response.add(movementResponse);
			});
		}

		return response;
	}

	@Override
	public Optional<Movement> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Movement save(Movement movement) {
		return repository.save(movement);
	}

}
