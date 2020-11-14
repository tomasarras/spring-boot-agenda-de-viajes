package edu.isistan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class Usuario {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Identificador del usuario",name = "id",required = false,value = "10")
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@Column(name = "id_usuario", nullable = false)
	private Integer id;
	@ApiModelProperty(notes = "Contraseña del usuario",name = "password",required = true,value = "password")
	@Column
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@Column
	private String email;
	@Column
	private String username;
	@Transient
	private String token;
	
	public Usuario() {}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", password=" + password + ", email=" + email + ", username=" + username
				+ ", token=" + token + "]";
	}
	
	
	
	
	
}
