package juego;

import java.util.HashSet;
import java.util.Set;

public class Prim {
    private static Set<String> espiasMarcados;

    public static Grafo algoritmoPrim(Grafo grafo, String espiaInicial) {
        if (!grafo.esConexo()) {
            throw new IllegalArgumentException("el grafo es invalido por que no es conexo");
        }
        Grafo arbolEspias = new Grafo();
        espiasMarcados = new HashSet<>();
        espiasMarcados.add(espiaInicial);

        while (espiasMarcados.size() < grafo.tamanio()) {
            Arista arista = elegirAristaConMenorPeso(grafo);
            if (arista != null) {
                arbolEspias.agregarArista(arista.getVert1(), arista.getVert2(), arista.getPeso());
                verificarSiEspiasEstanMarcados(arista);
            }
        }
        return arbolEspias;
    }

    private static Arista elegirAristaConMenorPeso(Grafo grafo) {
    	Arista minima = null;
    	for (String espia : espiasMarcados) {
    		Set<String> vecinos = grafo.espiasVecinos(espia);
    		for (String vecino : vecinos) {
    			if (!espiasMarcados.contains(vecino)) { 
    				Arista arista = grafo.getArista(espia, vecino);
    				if (minima == null || arista.getPeso() < minima.getPeso()) {
    					minima = arista;
    				}
    			}
    		}
    	}
    	return minima;
    }

    private static void verificarSiEspiasEstanMarcados(Arista arista) {
        if (!espiasMarcados.contains(arista.getVert1())) {
            espiasMarcados.add(arista.getVert1());
        }
        if (!espiasMarcados.contains(arista.getVert2())) {
            espiasMarcados.add(arista.getVert2());
        }
    }
    
}
