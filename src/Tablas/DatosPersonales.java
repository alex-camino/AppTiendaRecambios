package Tablas;

// Generated 28-ene-2015 16:17:57 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * DatosPersonales generated by hbm2java
 */
public class DatosPersonales implements java.io.Serializable {

	private Integer dpCodigo;
	private Usuarios usuarios;
	private String dpDni;
	private String dpNombre;
	private String dpApellidos;
	private String dpDireccion;
	private String dpTelefono;
	private String dpEmail;
	private Date dpFechanac;

	public DatosPersonales() {
	}

	public DatosPersonales(Usuarios usuarios, String dpDni, String dpNombre,
			String dpApellidos, String dpDireccion, String dpTelefono,
			String dpEmail, Date dpFechanac) {
		this.usuarios = usuarios;
		this.dpDni = dpDni;
		this.dpNombre = dpNombre;
		this.dpApellidos = dpApellidos;
		this.dpDireccion = dpDireccion;
		this.dpTelefono = dpTelefono;
		this.dpEmail = dpEmail;
		this.dpFechanac = dpFechanac;
	}

	public Integer getDpCodigo() {
		return this.dpCodigo;
	}

	public void setDpCodigo(Integer dpCodigo) {
		this.dpCodigo = dpCodigo;
	}

	public Usuarios getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public String getDpDni() {
		return this.dpDni;
	}

	public void setDpDni(String dpDni) {
		this.dpDni = dpDni;
	}

	public String getDpNombre() {
		return this.dpNombre;
	}

	public void setDpNombre(String dpNombre) {
		this.dpNombre = dpNombre;
	}

	public String getDpApellidos() {
		return this.dpApellidos;
	}

	public void setDpApellidos(String dpApellidos) {
		this.dpApellidos = dpApellidos;
	}

	public String getDpDireccion() {
		return this.dpDireccion;
	}

	public void setDpDireccion(String dpDireccion) {
		this.dpDireccion = dpDireccion;
	}

	public String getDpTelefono() {
		return this.dpTelefono;
	}

	public void setDpTelefono(String dpTelefono) {
		this.dpTelefono = dpTelefono;
	}

	public String getDpEmail() {
		return this.dpEmail;
	}

	public void setDpEmail(String dpEmail) {
		this.dpEmail = dpEmail;
	}

	public Date getDpFechanac() {
		return this.dpFechanac;
	}

	public void setDpFechanac(Date dpFechanac) {
		this.dpFechanac = dpFechanac;
	}

}
