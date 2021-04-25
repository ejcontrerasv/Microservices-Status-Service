package com.bandido.app.status.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Informacion sobre los estados generales de los microservicios")
@Entity
@Table(name = "status")
public class Status {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ApiModelProperty(notes = "Nombre representativo del estado, maximo 30 caracteres")
	@Column(length = 30, nullable = false)
	private String name;
	
	@ApiModelProperty(notes = "Descripcion que indica para que se utiliza dicho estado, maximo 250 caracteres")
	@Column(length = 250, nullable = false)
	private String description;

}
