package juego;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BfsTest {

	@Test(expected = IllegalArgumentException.class)
	public void grafoNuloTest() {
		Bfs.esConexo(null,"");
	}
	
	@Test
	public void grafoConUnEspiaTest() {
		Grafo g= new Grafo();
		g.agregarEspia("Lois");
		assertTrue(Bfs.esConexo(g,"Lois"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void espiaInicialVacioTest() {
		Grafo g= new Grafo();
		Bfs.esConexo(g, "");
	}
	
	@Test
	public void dosEspiasAisladosTest() {
		Grafo g= new Grafo();
		g.agregarEspia("Lois");
		g.agregarEspia("Ania");
		assertFalse(Bfs.esConexo(g, "Lois"));
	}
	@Test 
	public void grafoDosEspiasConectadosTest() {
		Grafo g= new Grafo();
		g.agregarEspia("Lois");
		g.agregarEspia("Ania");
		g.agregarArista("Lois", "Ania", 20);
		assertTrue (Bfs.esConexo(g, g.getEspiaInicial()));
	}
	
	@Test
	public void grafoNoConexoTest() {
		Grafo g= inicializarGrafoNoConexo();
		assertFalse(Bfs.esConexo(g, "Lois"));
	}
	
	@Test
	public void grafoConexoTest() {
		Grafo g= inicializarGrafoNoConexo();
		g.agregarArista("Ania", "Lois", 20);
		assertTrue(Bfs.esConexo(g, "Lois"));
	}

	private Grafo inicializarGrafoNoConexo() {
		Grafo g= new Grafo();
		g.agregarEspia("Lois");
		g.agregarEspia("Ania");
		return g;
	}
}