package edu.isistan.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class Viaje {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Identificador del viaje",name = "id",required = false,value  = "5")
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@Column(name = "id_viaje", nullable = false)
	private Integer id;
	@Column(name = "nombre_viaje", nullable = false)
	@ApiModelProperty(notes = "Nombre del viaje",name = "nombre",required = true,value  = "nombre_viaje")
	private String nombre;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_usuario",nullable = false)
	private Usuario usuario;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "viaje")
	private List<Plan> planes;
	@Column(name = "ciudad_destino", nullable = false)
	private String ciudadDestino;
	@Column(name = "fecha_inicio", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaInicio;
	@Column(name = "fecha_fin", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaFin;
	@Column(nullable = false)
	private String descripcion;
	
	public boolean esValido() {
		if (this.fechaInicio != null && this.fechaFin != null) {
			String inicio = this.fechaInicio.toString();
			String fin = this.fechaFin.toString();
			
			boolean valido = (inicio.compareTo(fin)) < 0;
			if (!valido) {
				return false;
			}
			
		} else {
			return false;
		}
		return !StringUtils.isEmpty(this.getNombre())
				&& !StringUtils.isEmpty(this.getCiudadDestino())
				&& !StringUtils.isEmpty(this.getDescripcion());
	}
	
	public Viaje() {
		super();
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Plan> getPlanes() {
		return planes;
	}

	public void setPlanes(List<Plan> planes) {
		this.planes = planes;
	}
	
	public void addPlan(Plan plan) {
		this.planes.add(plan);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(String ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Viaje [id=" + id + ", nombre=" + nombre + ", usuario=" + usuario + ", planes=" + planes
				+ ", ciudadDestino=" + ciudadDestino + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", descripcion=" + descripcion + "]";
	}
	
	
	
	
}
