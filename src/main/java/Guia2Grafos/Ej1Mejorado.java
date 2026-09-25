package Guia2Grafos;

import java.util.ArrayList;
import java.util.HashMap;

public class Ej1Mejorado implements Grafo {

    private int[][] matrizAdyacencia;    // se calcula, no se recibe
    private HashMap<Character, Integer> letraAIndice;
    private char[] indiceALetra;

    // input real: vertices + aristas (pares de vertices), no la matriz ya armada
    public Ej1Mejorado(char[] vertices, char[][] aristas) {
        this.indiceALetra = vertices;
        this.letraAIndice = new HashMap<>();
        for (int i = 0; i < vertices.length; i++) {
            letraAIndice.put(vertices[i], i);
        }
        this.matrizAdyacencia = calcularMatrizAdyacencia(aristas);
    }

    private int[][] calcularMatrizAdyacencia(char[][] aristas) {
        int n = indiceALetra.length;
        int[][] m = new int[n][n];
        for (char[] arista : aristas) {
            int i = letraAIndice.get(arista[0]);
            int j = letraAIndice.get(arista[1]);
            m[i][j] = 1;
            m[j][i] = 1; // simetrico; si i==j (lazo) es redundante pero no molesta
        }
        return m;
    }

    @Override
    public void mostrar() {
        for (int i = 0; i < matrizAdyacencia.length; i++) {
            for (int j = 0; j < matrizAdyacencia.length; j++) {
                if (matrizAdyacencia[i][j] == 1) {
                    System.out.println(indiceALetra[i] + "-->" + indiceALetra[j]);
                }
            }
        }
    }

    @Override
    public int contarLazos() {
        int counter = 0;
        for (int i = 0; i < matrizAdyacencia.length; i++) {
            if (matrizAdyacencia[i][i] == 1) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public char[] mostrarVerticeLazo() {
        ArrayList<Character> results = new ArrayList<>();
        for (int i = 0; i < matrizAdyacencia.length; i++) {
            if (matrizAdyacencia[i][i] == 1) {
                results.add(indiceALetra[i]);
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
        int indice = letraAIndice.get(v);
        for (int j = 0; j < matrizAdyacencia.length; j++) {
            if (matrizAdyacencia[indice][j] == 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int contarVerticesAislados() {
        int counter = 0;
        for (char letra : letraAIndice.keySet()) {
            if (isVerticeAislado(letra)) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public char[] returnVerticeAislado() {
        ArrayList<Character> results = new ArrayList<>();
        for (char letra : letraAIndice.keySet()) {
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
        int n = matrizAdyacencia.length;

        // 1. copiar matriz sin lazos
        int[][] sinLazos = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sinLazos[i][j] = (i == j) ? 0 : matrizAdyacencia[i][j];
            }
        }

        // 2. detectar aislados sobre la matriz sin lazos
        ArrayList<Integer> indicesValidos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            boolean aislado = true;
            for (int j = 0; j < n; j++) {
                if (sinLazos[i][j] == 1) {
                    aislado = false;
                    break;
                }
            }
            if (!aislado) {
                indicesValidos.add(i);
            }
        }

        // 3. armar vertices nuevos y aristas nuevas (sin lazos, sin aislados)
        int nuevoN = indicesValidos.size();
        char[] nuevosVertices = new char[nuevoN];
        for (int i = 0; i < nuevoN; i++) {
            nuevosVertices[i] = indiceALetra[indicesValidos.get(i)];
        }

        ArrayList<char[]> nuevasAristas = new ArrayList<>();
        for (int a = 0; a < nuevoN; a++) {
            for (int b = a + 1; b < nuevoN; b++) { // b=a+1: sin lazos, sin repetir pares
                int i = indicesValidos.get(a);
                int j = indicesValidos.get(b);
                if (matrizAdyacencia[i][j] == 1) {
                    nuevasAristas.add(new char[]{indiceALetra[i], indiceALetra[j]});
                }
            }
        }
        char[][] aristasArray = nuevasAristas.toArray(new char[0][]);

        // 4. devolver grafo nuevo, construido igual que el original (vertices + aristas)
        return new Ej1Mejorado(nuevosVertices, aristasArray);
    }

    @Override
    public void getMatrizAdyacencia() {
        for (int i = 0; i < matrizAdyacencia.length; i++) {
            for (int j = 0; j < matrizAdyacencia.length; j++) {
                System.out.print(matrizAdyacencia[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int[][] getMatrizIncidencia() {
        int n = matrizAdyacencia.length;

        ArrayList<int[]> aristas = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (matrizAdyacencia[i][j] == 1) {
                    aristas.add(new int[]{i, j});
                }
            }
        }

        int m = aristas.size();
        int[][] incidencia = new int[n][m];

        for (int k = 0; k < m; k++) {
            int i = aristas.get(k)[0];
            int j = aristas.get(k)[1];
            if (i == j) {
                incidencia[i][k] = 2;
            } else {
                incidencia[i][k] = 1;
                incidencia[j][k] = 1;
            }
        }

        return incidencia;
    }
}

//EN GENERAL ES O(n^2)