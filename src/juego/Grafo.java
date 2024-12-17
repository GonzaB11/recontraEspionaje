package juego;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo{
	private List<String> espias;
	private Set<Arista> sePuedenEncontrar;

	public Grafo() {
		this.espias= new ArrayList<>();
		this.sePuedenEncontrar= new HashSet<>();
	}
	
	public void agregarEspia(String espia) {
		if (contieneEspia(espia))
			throw new IllegalArgumentException("El espia ya existe");
		espias.add(espia);
	}

	public void agregarArista(String espia1, String espia2, double prob_intercepcion) {
		nombreEspiaVacio(espia1);
		nombreEspiaVacio(espia2);
		double probabilidad= verificarProbabilidadIntercepcion(prob_intercepcion);
		Arista arista = new Arista (espia1, espia2, probabilidad);
		sePuedenEncontrar.add(arista);
	}

	public Set<String> espiasVecinos(String espia) { 
		Set <String> vecinos= new HashSet<>();
		for (Arista arista : sePuedenEncontrar) {
			if (arista.getVert1().equals(espia)) {
				vecinos.add(arista.getVert2());
			}else if (arista.getVert2().equals(espia)) {
				vecinos.add(arista.getVert1());
			}	
		}
		return vecinos;
	}

	public int tamanio() {
		return espias.size();
	}

	public boolean contieneEspia(String espia) {
		if (espia == null || espia.isEmpty()) {
			throw new IllegalArgumentException("Nombre del Espia no puede estar vacio");
		}
		return espias.contains(espia);
	}

	public List<String> getEspias() {
		return espias;
	}

	public Set<Arista> getAristas() {
		return sePuedenEncontrar;
	}

	public String getEspiaInicial() {
		if (espias.isEmpty()) {
			throw new IllegalStateException("No hay espías en el grafo");
		}
		return espias.get(0);
	}

	public Arista getArista(String espia1, String espia2) {
		for (Arista arista : sePuedenEncontrar) {
			if ((arista.getVert1().equals(espia1) && arista.getVert2().equals(espia2)) ||
					(arista.getVert1().equals(espia2) && arista.getVert2().equals(espia1))) {
				return arista;
			}
		}
		return null;
	}

	public int getIndiceEspia(String espia) {
		int index = espias.indexOf(espia);
		if (index == -1) {
			throw new IllegalArgumentException("El espía no existe");
		}
		return index;
	}
	
	public double getAristaPeso(String espia1, String espia2) {
		double peso = 0;
		for(Arista arista : sePuedenEncontrar) {
			if ((arista.getVert1().equals(espia1) && arista.getVert2().equals(espia2)) ||
	               (arista.getVert1().equals(espia2) && arista.getVert2().equals(espia1))) {
					peso = arista.getPeso();
			}
		}
		return peso;
	}

	private void nombreEspiaVacio(String espia) {
		if (espia.isEmpty()) {
			throw new IllegalArgumentException("El nombre de espia no puede estar vacio");
		}
	}

	private double verificarProbabilidadIntercepcion(double probabilidad) {
		if (probabilidad < 0 || probabilidad > 100) {
			throw new IllegalArgumentException("La probabilidad de intercepcion debe estar entre 0 y 100");
		}
		return probabilidad / 100;
	}

	public Grafo arbolGeneradorMinimo () {
		return Prim.algoritmoPrim(this, getEspiaInicial());
	}  

	public boolean esConexo() {
		return Bfs.esConexo(this, getEspiaInicial());
	}
}