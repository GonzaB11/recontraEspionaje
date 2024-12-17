package juego;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AristaTest {
	
	@Test
	public void constructorValoresValidosTest() {
		Arista arista = new Arista("Lois","German",1);
		assertEquals("Lois",arista.getVert1());
		assertEquals("German",arista.getVert2());
		assertEquals(100,arista.getPeso(), 0.0001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void probabilidadInvalidaMenor0Test() {
		Arista arista = new Arista("Lois","German",-2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void probabilidadInvalidaMayor1Test() {
		Arista arista = new Arista("Lois","German",2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nombreEspiaVacioTest() {
		Arista arista = new Arista("","German",0.97);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nombreEspia2VacioTest() {
		Arista arista = new Arista("Lois","",0.64);
	}
	
}
  