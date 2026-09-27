package Guia2Grafos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Guia2Test {

    private final char[] vertices = {'A', 'B', 'C', 'D', 'E'};
    private final char[][] aristas = {
            {'A', 'B'},
            {'B', 'C'},
            {'C', 'C'}, // lazo en C
            // D y E aislados
    };

    @Test
    void testEj4ListasAdyacencia() {
        Grafo g = new Ej4(vertices, aristas);

        assertEquals(1, g.contarLazos());
        assertArrayEquals(new char[]{'C'}, g.mostrarVerticeLazo());

        assertTrue(g.isVerticeAislado('D'));
        assertTrue(g.isVerticeAislado('E'));
        assertFalse(g.isVerticeAislado('A'));
        assertFalse(g.isVerticeAislado('B'));
        assertFalse(g.isVerticeAislado('C'));

        assertEquals(2, g.contarVerticesAislados());
        assertArrayEquals(new char[]{'D', 'E'}, g.returnVerticeAislado());

        // Test simplificar
        Grafo simp = g.simplificar();
        assertNotNull(simp);
        assertEquals(0, simp.contarLazos());
        assertEquals(0, simp.contarVerticesAislados());

        // Test matriz de incidencia
        int[][] inc = g.getMatrizIncidencia();
        assertEquals(5, inc.length); // 5 vertices
        assertEquals(3, inc[0].length); // 3 aristas

        // Test mostrar y getMatrizAdyacencia sin lanzar excepción
        assertDoesNotThrow(g::mostrar);
        assertDoesNotThrow(g::getMatrizAdyacencia);
    }

    @Test
    void testEj5ListaAristas() {
        Grafo g = new Ej5(vertices, aristas);

        assertEquals(1, g.contarLazos());
        assertArrayEquals(new char[]{'C'}, g.mostrarVerticeLazo());

        assertTrue(g.isVerticeAislado('D'));
        assertTrue(g.isVerticeAislado('E'));
        assertFalse(g.isVerticeAislado('A'));
        assertFalse(g.isVerticeAislado('B'));
        assertFalse(g.isVerticeAislado('C'));

        assertEquals(2, g.contarVerticesAislados());
        assertArrayEquals(new char[]{'D', 'E'}, g.returnVerticeAislado());

        // Test simplificar
        Grafo simp = g.simplificar();
        assertNotNull(simp);
        assertEquals(0, simp.contarLazos());
        assertEquals(0, simp.contarVerticesAislados());

        // Test matriz de incidencia
        int[][] inc = g.getMatrizIncidencia();
        assertEquals(5, inc.length);
        assertEquals(3, inc[0].length);

        // Test mostrar y getMatrizAdyacencia sin lanzar excepción
        assertDoesNotThrow(g::mostrar);
        assertDoesNotThrow(g::getMatrizAdyacencia);
    }
}
