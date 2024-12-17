package juego;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

public class PrimTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void grafoNoConexoTest() {
		Grafo g= new Grafo();
		g.agregarEspia("Ania");
		g.agregarEspia("Lois");
		Prim.algoritmoPrim(g,"Ania");
	}
	
	@Test
	public void grafoConexoTest() {
		Grafo g= new Grafo();
		g.agregarEspia("Ania");
		g.agregarEspia("Lois");
		g.agregarArista("Ania", "Lois",30);
		Prim.algoritmoPrim(g, "Ania");
	}
	
	@Test
	public void grafoConUnSoloEspiaTest() {
		Grafo g = new Grafo();
		g.agregarEspia("Lois");
		Grafo arbol= Prim.algoritmoPrim(g, "Lois");
		assertEquals(0, arbol.getAristas().size());
	}
	
	@Test
	public void grafoConMultiplesAristasTest() {
	    Grafo g = new Grafo();
	    g.agregarEspia("Ania");
	    g.agregarEspia("Lois");
	    g.agregarEspia("German");
	    
	    g.agregarArista("Ania", "Lois", 30);
	    g.agregarArista("Ania", "German", 10);
	    g.agregarArista("Lois", "German", 20);
	    
	    
	    Grafo arbol = Prim.algoritmoPrim(g, "Ania");
	    
	    assertEquals(2, arbol.getAristas().size());
	}

	
	
}