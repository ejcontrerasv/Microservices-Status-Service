package com.bandido.app.status.models.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bandido.app.status.models.entity.Status;

public interface IStatusService extends ICRUD<Status> {
	
	Page<Status> listarPageable(Pageable pageable);

}
