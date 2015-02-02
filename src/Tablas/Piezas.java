package Tablas;

import java.util.ArrayList;


public class Piezas implements java.io.Serializable {

	private Integer pieCodigo;
	private CatEspecifica catEspecifica;
	private Proveedores proveedores;
	private String pieReferencia;
	private String pieNombre;
	private String pieDescripcion;
	private Integer pieCantidad;
	private Float piePrecio;
	private ArrayList<PedidosProveedor> pedidosProveedores = new ArrayList<PedidosProveedor>();
	private ArrayList<LineasPedido> lineasPedidos = new ArrayList<LineasPedido>();
	

	public Piezas() {
	}

	public Piezas(String pieReferencia) {
		this.pieReferencia = pieReferencia;
	}

	public Piezas(CatEspecifica catEspecifica, Proveedores proveedores,
			String pieReferencia, String pieNombre, String pieDescripcion,
			Integer pieCantidad, Float piePrecio,
			ArrayList<PedidosProveedor> pedidosProveedoreses, ArrayList<LineasPedido> lineasPedidos) {
		this.catEspecifica = catEspecifica;
		this.proveedores = proveedores;
		this.pieReferencia = pieReferencia;
		this.pieNombre = pieNombre;
		this.pieDescripcion = pieDescripcion;
		this.pieCantidad = pieCantidad;
		this.piePrecio = piePrecio;
		this.lineasPedidos = lineasPedidos;
		this.lineasPedidos = lineasPedidos;
		this.pedidosProveedores = pedidosProveedores;
	}

	public Integer getPieCodigo() {
		return this.pieCodigo;
	}

	public void setPieCodigo(Integer pieCodigo) {
		this.pieCodigo = pieCodigo;
	}

	public CatEspecifica getCatEspecifica() {
		return this.catEspecifica;
	}

	public void setCatEspecifica(CatEspecifica catEspecifica) {
		this.catEspecifica = catEspecifica;
	}

	public Proveedores getProveedores() {
		return this.proveedores;
	}

	public void setProveedores(Proveedores proveedores) {
		this.proveedores = proveedores;
	}

	public String getPieReferencia() {
		return this.pieReferencia;
	}

	public void setPieReferencia(String pieReferencia) {
		this.pieReferencia = pieReferencia;
	}

	public String getPieNombre() {
		return this.pieNombre;
	}

	public void setPieNombre(String pieNombre) {
		this.pieNombre = pieNombre;
	}

	public String getPieDescripcion() {
		return this.pieDescripcion;
	}

	public void setPieDescripcion(String pieDescripcion) {
		this.pieDescripcion = pieDescripcion;
	}

	public Integer getPieCantidad() {
		return this.pieCantidad;
	}

	public void setPieCantidad(Integer pieCantidad) {
		this.pieCantidad = pieCantidad;
	}

	public Float getPiePrecio() {
		return this.piePrecio;
	}

	public void setPiePrecio(Float piePrecio) {
		this.piePrecio = piePrecio;
	}

	public ArrayList<PedidosProveedor> getProveedoreses() {
		return this.pedidosProveedores;
	}

	public void setProveedoreses(ArrayList<PedidosProveedor> pedidosProveedores) {
		this.pedidosProveedores = pedidosProveedores;
	}

	
	public ArrayList<LineasPedido> getLineasPedidos() {
		return this.lineasPedidos;
	}

	public void setLineasPedidos(ArrayList<LineasPedido> lineasPedidos) {
		this.lineasPedidos = lineasPedidos;
	}

	
}
