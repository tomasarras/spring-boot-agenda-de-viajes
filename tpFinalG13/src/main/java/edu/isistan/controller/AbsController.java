package edu.isistan.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import edu.isistan.model.Viaje;

public abstract class AbsController {
	
	protected int getIdUsuarioDelToken() {
		return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
	
	protected boolean lePerteneceAlUsuario(Viaje viaje) {
		int idUsuario = getIdUsuarioDelToken();
		return viaje.getUsuario().getId() == idUsuario;
	}
}
