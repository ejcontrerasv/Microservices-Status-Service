package com.bandido.app.status.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bandido.app.status.exception.ModeloNotFoundException;
import com.bandido.app.status.models.entity.Status;
import com.bandido.app.status.models.service.IStatusService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RequestMapping(path = "/app/status")
public class StatusController {
	
	@Autowired
	private IStatusService service;
	
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Status status){
		Status obj = service.registrar(status);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@HystrixCommand(fallbackMethod = "listarFallBackList", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10") })
	@GetMapping(path = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Status>> listar(){
		List<Status> lista = service.listar();
//		try {
//			Thread.sleep(10000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return new ResponseEntity<List<Status>>(lista, HttpStatus.OK);
	}
	
	public ResponseEntity<List<Status>> listarFallBackList() {
		List<Status> lista = new ArrayList<Status>();
		return new ResponseEntity<List<Status>>(lista, HttpStatus.OK);
	}
	
	@HystrixCommand(fallbackMethod = "listarFallBack")
	@GetMapping(path = "/listar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> listarPorId(@PathVariable("id") Integer id) {
		Status status = service.leerPorId(id);
		
//		try {
//			Thread.sleep(10000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return new ResponseEntity<Status>(status, HttpStatus.OK);
	}
	
	public ResponseEntity<Status> listarFallBack(Integer id) {
		Status status  = new Status();
		status.setName("Fallback");
		status.setDescription("Estado asociado a una falla o latencia");
		return new ResponseEntity<Status>(status, HttpStatus.OK);
	}
	
	@GetMapping("/listarPageable")
	public ResponseEntity<Page<Status>> listarPageable(Pageable pageable){
		Page<Status> docs = service.listarPageable(pageable);
		return new ResponseEntity<Page<Status>>(docs, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Status> modificar(@Valid @RequestBody Status status){
		Status obj = service.modificar(status);
		return new ResponseEntity<Status>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) throws ModeloNotFoundException{
		Status obj = service.leerPorId(id);
		if (obj.getId() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: [" + id + "]");
		}
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
