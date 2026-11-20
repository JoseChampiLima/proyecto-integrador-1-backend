package com.deportido.services;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deportido.model.EstadoReserva;
import com.deportido.model.Reserva;
import com.deportido.repository.EstadoReservaRepository;
import com.deportido.repository.ReservaRepository;

@Service
public class ReservaFinalizacionService {
	  private final ReservaRepository reservaRepository;
	    private final EstadoReservaRepository estadoReservaRepository;

	    public ReservaFinalizacionService(
	            ReservaRepository reservaRepository,
	            EstadoReservaRepository estadoReservaRepository) {

	        this.reservaRepository = reservaRepository;
	        this.estadoReservaRepository = estadoReservaRepository;
	    }

	    @Scheduled(fixedDelay = 60000)
	    @Transactional
	    public void finalizarReservas() {

	        LocalDate fechaActual = LocalDate.now();
	        LocalTime horaActual = LocalTime.now();

	        List<Reserva> reservas =
	                reservaRepository.buscarReservasConfirmadasFinalizadas(
	                        fechaActual,
	                        horaActual
	                );

	        if (reservas.isEmpty()) {
	            return;
	        }

	        EstadoReserva finalizada =
	                estadoReservaRepository.findByNombre("FINALIZADA")
	                        .orElseThrow(() ->
	                                new IllegalStateException(
	                                        "No existe el estado FINALIZADA"
	                                )
	                        );

	        for (Reserva reserva : reservas) {
	            reserva.setEstadoReserva(finalizada);
	        }

	        reservaRepository.saveAll(reservas);

	        System.out.println(
	                "Reservas finalizadas automáticamente: "
	                        + reservas.size()
	        );
	    }
}
