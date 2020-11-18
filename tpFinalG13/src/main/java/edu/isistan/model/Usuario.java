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
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "id_usuario", nullable = false)
	private Integer id;
	@ApiModelProperty(required = true)
	@Column(nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@Column(nullable = false)
	@ApiModelProperty(required = true)
	private String email;
	@Column(nullable = false)
	@JsonIgnore
	private boolean admin;
	@Column(nullable = false)
	@ApiModelProperty(required = true)
	private String username;
	@Transient
	@JsonProperty(access = Access.READ_ONLY)
	private String token;
}
