package Guia2Grafos;

import java.util.ArrayList;
import java.util.HashMap;

// LISTA DE ARISTAS
// Representación mediante lista de pares (aristas)
public class Ej5 implements Grafo {

    private ArrayList<int[]> listaAristas;
    private HashMap<Character, Integer> letraAIndice;
    private char[] indiceALetra;

    public Ej5(char[] vertices, char[][] aristas) {
        this.indiceALetra = vertices;
        this.letraAIndice = new HashMap<>();
        for (int i = 0; i < vertices.length; i++) {
            letraAIndice.put(vertices[i], i);
        }

        this.listaAristas = new ArrayList<>();
        for (char[] arista : aristas) {
            int u = letraAIndice.get(arista[0]);
            int v = letraAIndice.get(arista[1]);
            // Guardar arista normalizada (menor a la izquierda para consistencia, salvo que sea lazo)
            if (u <= v) {
                this.listaAristas.add(new int[]{u, v});
            } else {
                this.listaAristas.add(new int[]{v, u});
            }
        }
    }

    public Ej5(char[] vertices, ArrayList<int[]> listaAristas) {
        this.indiceALetra = vertices;
        this.letraAIndice = new HashMap<>();
        for (int i = 0; i < vertices.length; i++) {
            letraAIndice.put(vertices[i], i);
        }
        this.listaAristas = listaAristas;
    }

    @Override
    public void mostrar() {
        for (int[] arista : listaAristas) {
            System.out.println(indiceALetra[arista[0]] + " -- " + indiceALetra[arista[1]]);
        }
    }

    @Override
    public int contarLazos() {
        int counter = 0;
        for (int[] arista : listaAristas) {
            if (arista[0] == arista[1]) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public char[] mostrarVerticeLazo() {
        ArrayList<Character> results = new ArrayList<>();
        for (int i = 0; i < indiceALetra.length; i++) {
            for (int[] arista : listaAristas) {
                if (arista[0] == i && arista[1] == i) {
                    results.add(indiceALetra[i]);
                    break;
                }
            }
        }
        char[] res = new char[results.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = results.get(i);
        }
        return res;
    }

    @Override
    public boolean isVerticeAislado(char v) {
        if (!letraAIndice.containsKey(v)) {
            return false;
        }
        int indice = letraAIndice.get(v);
        for (int[] arista : listaAristas) {
            if (arista[0] == indice || arista[1] == indice) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int contarVerticesAislados() {
        int counter = 0;
        for (char letra : indiceALetra) {
            if (isVerticeAislado(letra)) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public char[] returnVerticeAislado() {
        ArrayList<Character> results = new ArrayList<>();
        for (char letra : indiceALetra) {
            if (isVerticeAislado(letra)) {
                results.add(letra);
            }
        }
        char[] res = new char[results.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = results.get(i);
        }
        return res;
    }

    @Override
    public Grafo simplificar() {
        // 1. Filtrar aristas sin lazos
        ArrayList<int[]> aristasSinLazos = new ArrayList<>();
        for (int[] arista : listaAristas) {
            if (arista[0] != arista[1]) {
                aristasSinLazos.add(arista);
            }
        }

        // 2. Detectar vértices no aislados tras remover lazos
        ArrayList<Integer> indicesValidos = new ArrayList<>();
        for (int i = 0; i < indiceALetra.length; i++) {
            boolean tieneArista = false;
            for (int[] arista : aristasSinLazos) {
                if (arista[0] == i || arista[1] == i) {
                    tieneArista = true;
                    break;
                }
            }
            if (tieneArista) {
                indicesValidos.add(i);
            }
        }

        // 3. Crear nuevo arreglo de vértices
        int nuevoN = indicesValidos.size();
        char[] nuevosVertices = new char[nuevoN];
        for (int i = 0; i < nuevoN; i++) {
            nuevosVertices[i] = indiceALetra[indicesValidos.get(i)];
        }

        // 4. Crear nuevas aristas entre los vértices válidos
        ArrayList<char[]> nuevasAristas = new ArrayList<>();
        for (int[] arista : aristasSinLazos) {
            if (indicesValidos.contains(arista[0]) && indicesValidos.contains(arista[1])) {
                nuevasAristas.add(new char[]{indiceALetra[arista[0]], indiceALetra[arista[1]]});
            }
        }

        return new Ej5(nuevosVertices, nuevasAristas.toArray(new char[0][]));
    }

    @Override
    public void getMatrizAdyacencia() {
        int n = indiceALetra.length;
        int[][] matriz = new int[n][n];
        for (int[] arista : listaAristas) {
            int u = arista[0];
            int v = arista[1];
            matriz[u][v] = 1;
            matriz[v][u] = 1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int[][] getMatrizIncidencia() {
        int n = indiceALetra.length;
        int m = listaAristas.size();
        int[][] incidencia = new int[n][m];

        for (int k = 0; k < m; k++) {
            int i = listaAristas.get(k)[0];
            int j = listaAristas.get(k)[1];
            if (i == j) {
                incidencia[i][k] = 2; // lazo
            } else {
                incidencia[i][k] = 1;
                incidencia[j][k] = 1;
            }
        }

        return incidencia;
    }
}
