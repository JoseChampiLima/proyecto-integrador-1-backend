package com.deportido.services;

import java.time.LocalDate;

import com.deportivo.DTO.DashboardDTO;

public interface DashboardService {

	 DashboardDTO obtenerDashboard(
	            LocalDate fechaDesde,
	            LocalDate fechaHasta
	    );
}
