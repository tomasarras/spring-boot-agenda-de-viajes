package edu.isistan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Data
public class Usuario {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Identificador del usuario",name = "id",required = false,value = "10")
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@Column(name = "id_usuario", nullable = false)
	private Integer id;
	@ApiModelProperty(notes = "Contraseña del usuario",name = "password",required = true,value = "password")
	@Column(nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	@JsonIgnore
	private boolean admin;
	@Column(nullable = false)
	private String username;
	@Transient
	private String token;
}
