package com.jld.base.pagination;

import java.util.List;

public class PaginationResultInfo <T> {
	
	List<T> listaResultados;
	
	Integer totalResultados;

	public List<T> getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(List<T> listaResultados) {
		this.listaResultados = listaResultados;
	}

	public Integer getTotalResultados() {
		return totalResultados;
	}

	public void setTotalResultados(Integer totalResultados) {
		this.totalResultados = totalResultados;
	}
}
