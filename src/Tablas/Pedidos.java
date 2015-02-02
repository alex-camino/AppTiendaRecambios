package Tablas;

// Generated 28-ene-2015 16:17:57 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Pedidos generated by hbm2java
 */
public class Pedidos implements java.io.Serializable {

	private Integer pedCodigo;
	private Usuarios usuarios;
	private String pedEstado;
	private String pedComentarios;
	private Float pedImporte;
	private Date fechaHora;
	private Set<LineasPedido> lineasPedidos = new HashSet<LineasPedido>(0);

	public Pedidos() {
	}

	public Pedidos(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public Pedidos(Usuarios usuarios, String pedEstado, String pedComentarios,
			Float pedImporte, Date fechaHora, Set<LineasPedido> lineasPedidos) {
		this.usuarios = usuarios;
		this.pedEstado = pedEstado;
		this.pedComentarios = pedComentarios;
		this.pedImporte = pedImporte;
		this.fechaHora = fechaHora;
		this.lineasPedidos = lineasPedidos;
	}

	public Integer getPedCodigo() {
		return this.pedCodigo;
	}

	public void setPedCodigo(Integer pedCodigo) {
		this.pedCodigo = pedCodigo;
	}

	public Usuarios getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public String getPedEstado() {
		return this.pedEstado;
	}

	public void setPedEstado(String pedEstado) {
		this.pedEstado = pedEstado;
	}

	public String getPedComentarios() {
		return this.pedComentarios;
	}

	public void setPedComentarios(String pedComentarios) {
		this.pedComentarios = pedComentarios;
	}

	public Float getPedImporte() {
		return this.pedImporte;
	}

	public void setPedImporte(Float pedImporte) {
		this.pedImporte = pedImporte;
	}

	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Set<LineasPedido> getLineasPedidos() {
		return this.lineasPedidos;
	}

	public void setLineasPedidos(Set<LineasPedido> lineasPedidos) {
		this.lineasPedidos = lineasPedidos;
	}

}
