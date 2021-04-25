package com.bandido.app.status.models.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bandido.app.status.models.entity.Status;

public interface IStatusRepo extends JpaRepository<Status, Integer> {

}
