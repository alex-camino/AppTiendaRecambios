package Tablas;

import java.util.ArrayList;


public class CatEspecifica implements java.io.Serializable {

	private Integer catEspCodigo;
	private CatPiezas catPiezas;
	private String catEspNombre;
	private ArrayList<Piezas> piezas = new ArrayList<Piezas>();

	public CatEspecifica() {
	}

	public CatEspecifica(String catEspNombre) {
		this.catEspNombre = catEspNombre;
	}

	public CatEspecifica(CatPiezas catPiezas, String catEspNombre,
			ArrayList<Piezas> piezas) {
		this.catPiezas = catPiezas;
		this.catEspNombre = catEspNombre;
		this.piezas = piezas;
	}

	public Integer getCatEspCodigo() {
		return this.catEspCodigo;
	}

	public void setCatEspCodigo(Integer catEspCodigo) {
		this.catEspCodigo = catEspCodigo;
	}

	public CatPiezas getCatPiezas() {
		return this.catPiezas;
	}

	public void setCatPiezas(CatPiezas catPiezas) {
		this.catPiezas = catPiezas;
	}

	public String getCatEspNombre() {
		return this.catEspNombre;
	}

	public void setCatEspNombre(String catEspNombre) {
		this.catEspNombre = catEspNombre;
	}

	public ArrayList<Piezas> getPiezases() {
		return this.piezas;
	}

	public void setPiezases(ArrayList<Piezas> piezas) {
		this.piezas = piezas;
	}

}
