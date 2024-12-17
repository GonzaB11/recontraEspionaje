package juego;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import java.util.Set;

import org.junit.Test;

public class GrafoTest {

    @Test
    public void grafoConstructorTest() {
        Grafo g = new Grafo();
        assertTrue(g.getEspias().isEmpty());
        assertTrue(g.getAristas().isEmpty());
    }

    @Test
    public void agregarEspiaTest() {
        Grafo g = new Grafo();
        g.agregarEspia("Lois");
        assertTrue(g.contieneEspia("Lois"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void agregarEspiaVacioTest() {
    	Grafo g = new Grafo();
    	g.agregarEspia("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void agregarEspiaNuloTest() {
    	Grafo g = new Grafo();
    	g.agregarEspia(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarEspiasConMismoNombreTest() {
        Grafo g = new Grafo();
        g.agregarEspia("Lois");
        g.agregarEspia("Lois");
    }
 
    
    @Test(expected = IllegalArgumentException.class)
    public void agregarAristaEspiaVacioTest() {
    	Grafo g= new Grafo();
    	g.agregarArista("", "", 1);
    }
    
    @Test(expected = IllegalArgumentException.class) 
    public void agregarAristaConPesoNegativoTest() {
    	Grafo g= new Grafo();
    	g.agregarArista("Lois", "Ania", -2);
    	
    }
    
    @Test(expected = IllegalArgumentException.class) 
    public void agregarAristaConPesoMayorUnoTest() {
    	Grafo g= new Grafo();
    	g.agregarArista("Lois", "Ania", 191);
    }
    
    @Test 
    public void agregarAristasConPesoValidoTest() {
    	Grafo g= new Grafo();
    	g.agregarArista("Lois", "Ania", 50);
    	assertFalse(g.getAristas().isEmpty());
    }
    
    @Test 
    public void tamanioGrafoTest() {
    	Grafo g= new Grafo();
    	g.agregarEspia("Lois");
    	assertEquals(1,g.tamanio());
    }
    
    @Test
    public void espiaSinVecinosTest() {
    	Grafo g= new Grafo();
    	Set <String> vecinos= g.espiasVecinos("Lois");
    	assertEquals(0, vecinos.size());
    }
    
    @Test
    public void espiaConVecinosVerticeUnoTest() {
    	Grafo g= new Grafo();
    	g.agregarArista("Lois", "Ania", 54);
    	Set <String> vecinos= g.espiasVecinos("Lois");
    	assertEquals (1, vecinos.size());
    }
    
    @Test
    public void espiaConVecinosVerticeDosTest() {
    	Grafo g= new Grafo();
    	g.agregarArista("Lois", "Ania", 54);
    	Set <String> vecinos= g.espiasVecinos("Ania");
    	assertEquals (1, vecinos.size());
    }
    
    @Test
    public void obtenerVerticeAristaTest() {
    	Grafo g= new Grafo();
    	g.agregarArista("Lois", "Ania", 54);
    	Arista arista=g.getArista("Lois", "Ania");
    	assertEquals("Lois",arista.getVert1());
    }
    
    @Test
    public void obtenerVerticeAristaInvertidoTest() {
    	Grafo g= new Grafo();
    	g.agregarArista("Lois", "Ania", 54);
    	Arista arista=g.getArista("Ania", "Lois");
    	assertEquals("Lois",arista.getVert1());
    }
    
    @Test
    public void agregarAristasTest() {
    	Grafo g= new Grafo();
    	g.agregarArista("Lois","Ania", 20);
    	Arista arista= g.getArista("Lois", "Ania");
    	assertNotNull(arista);
    }
    
    
    @Test
    public void obtenerAristaInexistenteTest() {
        Grafo g = new Grafo();
        g.agregarEspia("Lois");
        g.agregarEspia("Ania");
        Arista arista = g.getArista("Lois", "Roberto");
        assertNull(arista);
    }
    
    
    @Test
    public void obtenerEspiaInicialTest() {
    	Grafo g= new Grafo();
    	g.agregarEspia("Lois");
    	assertEquals("Lois",g.getEspiaInicial());
    }
    
    @Test (expected=IllegalStateException.class)
    public void obtenerEspiaInicialVacioTest() {
    	Grafo g= new Grafo();
    	g.getEspiaInicial();
    }
    
    @Test	
    public void obtenerIndiceEspiaTest() {
    	Grafo g = new Grafo();
    	g.agregarEspia("Lois");
    	g.agregarEspia("Ania");
    	assertEquals(1,g.getIndiceEspia("Ania"),0.001);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void obtenerIndiceEspiaInexistenteTest() {
    	Grafo g = new Grafo();
    	g.agregarEspia("Lois");
    	g.agregarEspia("Ania");
    	g.getIndiceEspia("Yor");
    }
   
    @Test
    public void obtenerPesoAristaTest() {
    	Grafo g = new Grafo();
    	g.agregarEspia("Lois");
    	g.agregarEspia("Yor");
    	g.agregarArista("Lois", "Yor", 70);
    	assertEquals(70,g.getAristaPeso("Lois", "Yor"),0.1);
    	assertEquals(70,g.getAristaPeso("Yor", "Lois"),0.1);
    }
}
