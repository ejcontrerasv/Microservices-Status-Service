package com.bandido.app.status.models.service;

import java.util.List;

public interface ICRUD <T> {

	T registrar(T obj);
	T modificar(T obj);
	T leerPorId(Integer id);
	List<T> listar();
	boolean eliminar(Integer id);
}
