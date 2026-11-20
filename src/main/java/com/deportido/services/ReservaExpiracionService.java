package com.deportido.services;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deportido.model.EstadoReserva;
import com.deportido.model.Reserva;
import com.deportido.repository.EstadoReservaRepository;
import com.deportido.repository.ReservaRepository;

@Service
public class ReservaExpiracionService {
	 private final ReservaRepository reservaRepository;
	    private final EstadoReservaRepository estadoReservaRepository;

	    public ReservaExpiracionService(
	            ReservaRepository reservaRepository,
	            EstadoReservaRepository estadoReservaRepository) {

	        this.reservaRepository = reservaRepository;
	        this.estadoReservaRepository = estadoReservaRepository;
	    }

	    @Scheduled(fixedDelay = 10000)
	    @Transactional
	    public void cancelarReservasVencidas() {

	    	LocalDateTime fechaLimite =
	    	        LocalDateTime.now().minusMinutes(5);

	        List<Reserva> reservas =
	                reservaRepository.buscarReservasPendientesVencidas(
	                        fechaLimite);

	        if (reservas.isEmpty()) {
	            return;
	        }

	        EstadoReserva cancelada =
	                estadoReservaRepository.findByNombre("CANCELADA")
	                        .orElseThrow(() ->
	                                new IllegalStateException(
	                                        "No existe el estado CANCELADA"));

	        for (Reserva reserva : reservas) {
	            reserva.setEstadoReserva(cancelada);
	        }

	        reservaRepository.saveAll(reservas);

	        System.out.println(
	                "Reservas vencidas canceladas: " + reservas.size()
	        );
	    }
}
