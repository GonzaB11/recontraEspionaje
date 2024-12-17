package juego;

public class Arista {

	private String espia1;
	private String espia2;
	private double probabilidadIntercepcion;

	public Arista(String espia1, String espia2, double probabilidadIntercepcion) {
		if(probabilidadIntercepcion < 0) {
			throw new IllegalArgumentException("La probabilidad de  intercepcion no puede ser  menor a 0");		
		}
		if(probabilidadIntercepcion > 1) {
			throw new IllegalArgumentException("La probabilidad de  intercepcion no puede ser  mayor a 100");
		}
		
		if (espia1.isEmpty() || espia2.isEmpty()) {
			throw new IllegalArgumentException("El nombre del espia no puede estar vacio");
		}
		this.espia1 = espia1;
		this.espia2 = espia2;
		this.probabilidadIntercepcion = probabilidadIntercepcion;
	}

	public String getVert1() {
		return this.espia1;
	}

	public String getVert2() {
		return this.espia2;
	}

	public double getPeso() {
		return this.probabilidadIntercepcion * 100;
	}

}
