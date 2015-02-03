package Tablas;

import java.util.ArrayList;


public class Proveedores implements java.io.Serializable {

	private Integer proCodigo;
	private String proNombre;
	private String proTelefono;
	private String proDireccion;
	private String proNumcuenta;
	private ArrayList<Piezas> piezas = new ArrayList<Piezas>();
	private ArrayList<PedidosProveedor> pedidosProveedors = new ArrayList<PedidosProveedor>();

	public Proveedores() {
	}

	public Proveedores(String proNombre) {
		this.proNombre = proNombre;
	}

	public Proveedores(String proNombre, String proTelefono,
			String proDireccion, String proNumcuenta, ArrayList<Piezas> piezas,
			ArrayList<PedidosProveedor> pedidosProveedors) {
		this.proNombre = proNombre;
		this.proTelefono = proTelefono;
		this.proDireccion = proDireccion;
		this.proNumcuenta = proNumcuenta;
		this.piezas = piezas;
		this.pedidosProveedors = pedidosProveedors;

	}

	public Integer getProCodigo() {
		return this.proCodigo;
	}

	public void setProCodigo(Integer proCodigo) {
		this.proCodigo = proCodigo;
	}

	public String getProNombre() {
		return this.proNombre;
	}

	public void setProNombre(String proNombre) {
		this.proNombre = proNombre;
	}

	public String getProTelefono() {
		return this.proTelefono;
	}

	public void setProTelefono(String proTelefono) {
		this.proTelefono = proTelefono;
	}

	public String getProDireccion() {
		return this.proDireccion;
	}

	public void setProDireccion(String proDireccion) {
		this.proDireccion = proDireccion;
	}

	public String getProNumcuenta() {
		return this.proNumcuenta;
	}

	public void setProNumcuenta(String proNumcuenta) {
		this.proNumcuenta = proNumcuenta;
	}

	public ArrayList<Piezas> getPiezas() {
		return this.piezas;
	}

	public void setPiezases(ArrayList<Piezas> piezas) {
		this.piezas = piezas;
	}

	public ArrayList<PedidosProveedor> getPedidosProveedors() {
		return this.pedidosProveedors;
	}

	public void setPedidosProveedors(ArrayList<PedidosProveedor> pedidosProveedors) {
		this.pedidosProveedors = pedidosProveedors;
	}

	

}
