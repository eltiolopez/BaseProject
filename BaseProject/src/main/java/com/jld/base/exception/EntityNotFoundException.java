package com.jld.base.exception;

public class EntityNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String error;

	private String mensaje;
	
	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(String error, String mensaje) {
		super();
		this.error = error;
		this.mensaje = mensaje;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
