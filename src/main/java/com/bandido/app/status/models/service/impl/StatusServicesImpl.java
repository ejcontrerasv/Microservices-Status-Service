package com.bandido.app.status.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandido.app.status.models.entity.Status;
import com.bandido.app.status.models.repo.IStatusRepo;
import com.bandido.app.status.models.service.IStatusService;

@Service
public class StatusServicesImpl implements IStatusService{
	
	@Autowired
	private IStatusRepo repo;
	
	@Override
	public Status registrar(Status obj) {
		return repo.save(obj);
	}

	@Override
	public Status modificar(Status obj) {
		return repo.save(obj);
	}

	@Override
	public Status leerPorId(Integer id) {
		Optional<Status> op = repo.findById(id);
		return op.isPresent() ? op.get() : new Status();
	}

	@Override
	public List<Status> listar() {
		return repo.findAll();
	}

	@Override
	public boolean eliminar(Integer id) {
		repo.deleteById(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Status> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}

}
