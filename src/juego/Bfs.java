package juego;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Bfs {

	public static boolean esConexo(Grafo grafo, String espiaInicial) {
		if (grafo == null)
			throw new IllegalArgumentException ("El grafo no puede ser nulo");
		if(espiaInicial.isEmpty())
			throw new IllegalArgumentException ("No hay espia");
		if (grafo.tamanio()==1)
			return true;
		return alcanzables(grafo,espiaInicial).size()== grafo.tamanio();
	}

	private static Set<String> alcanzables(Grafo grafo, String espiaInicial) {
	    Set<String> vecinos = new HashSet<>();
	    Queue<String> cola = new LinkedList<>();
	    
	    cola.add(espiaInicial);
	    boolean[] marcados = new boolean[grafo.tamanio()];
	    marcados[grafo.getIndiceEspia(espiaInicial)] = true;

	    while (!cola.isEmpty()) {
	        String espia = cola.poll();
	        vecinos.add(espia);
	        
	        for (String vecino : grafo.espiasVecinos(espia)) {
	            if (!marcados[grafo.getIndiceEspia(vecino)]) {
	                marcados[grafo.getIndiceEspia(vecino)] = true;
	                cola.add(vecino);
	            }
	        }
	    }
	    return vecinos;
	}

}