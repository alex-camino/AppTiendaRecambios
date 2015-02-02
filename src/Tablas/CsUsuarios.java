package Tablas;

// Generated 28-ene-2015 16:17:57 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * CsUsuarios generated by hbm2java
 */
public class CsUsuarios implements java.io.Serializable {

	private int csUserCodigo;
	private String csAlias;
	private String csPass;
	private String csDni;
	private String csNombre;
	private String csApellidos;
	private String csDireccion;
	private String csTelefono;
	private String csEmail;
	private Date csFechanac;

	public CsUsuarios() {
	}

	public CsUsuarios(int csUserCodigo) {
		this.csUserCodigo = csUserCodigo;
	}

	public CsUsuarios(int csUserCodigo, String csAlias, String csPass,
			String csDni, String csNombre, String csApellidos,
			String csDireccion, String csTelefono, String csEmail,
			Date csFechanac) {
		this.csUserCodigo = csUserCodigo;
		this.csAlias = csAlias;
		this.csPass = csPass;
		this.csDni = csDni;
		this.csNombre = csNombre;
		this.csApellidos = csApellidos;
		this.csDireccion = csDireccion;
		this.csTelefono = csTelefono;
		this.csEmail = csEmail;
		this.csFechanac = csFechanac;
	}

	public int getCsUserCodigo() {
		return this.csUserCodigo;
	}

	public void setCsUserCodigo(int csUserCodigo) {
		this.csUserCodigo = csUserCodigo;
	}

	public String getCsAlias() {
		return this.csAlias;
	}

	public void setCsAlias(String csAlias) {
		this.csAlias = csAlias;
	}

	public String getCsPass() {
		return this.csPass;
	}

	public void setCsPass(String csPass) {
		this.csPass = csPass;
	}

	public String getCsDni() {
		return this.csDni;
	}

	public void setCsDni(String csDni) {
		this.csDni = csDni;
	}

	public String getCsNombre() {
		return this.csNombre;
	}

	public void setCsNombre(String csNombre) {
		this.csNombre = csNombre;
	}

	public String getCsApellidos() {
		return this.csApellidos;
	}

	public void setCsApellidos(String csApellidos) {
		this.csApellidos = csApellidos;
	}

	public String getCsDireccion() {
		return this.csDireccion;
	}

	public void setCsDireccion(String csDireccion) {
		this.csDireccion = csDireccion;
	}

	public String getCsTelefono() {
		return this.csTelefono;
	}

	public void setCsTelefono(String csTelefono) {
		this.csTelefono = csTelefono;
	}

	public String getCsEmail() {
		return this.csEmail;
	}

	public void setCsEmail(String csEmail) {
		this.csEmail = csEmail;
	}

	public Date getCsFechanac() {
		return this.csFechanac;
	}

	public void setCsFechanac(Date csFechanac) {
		this.csFechanac = csFechanac;
	}

}
