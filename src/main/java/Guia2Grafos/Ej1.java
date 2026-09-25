package Guia2Grafos;

import java.util.ArrayList;
import java.util.HashMap;

public class Ej1 implements Grafo{

    private int[][] matrizAdyacencia;
    private HashMap<Character, Integer> letraAIndice;
    private char[] indiceALetra; // o un Map<Integer, Character>

    public Ej1(int[][] matrizAdyacencia, char[] vertices) {
        this.matrizAdyacencia = matrizAdyacencia;
        this.indiceALetra = vertices;
        this.letraAIndice = new HashMap<>();
        for (int i = 0; i < vertices.length; i++) {
            letraAIndice.put(vertices[i], i);
        }
    }

    @Override
    public void mostrar() {
        for (int i = 0; i < matrizAdyacencia.length; i++){
            for (int j = 0; j < matrizAdyacencia.length; j++){
                if (matrizAdyacencia[i][j] == 1){
                    System.out.println(indiceALetra[i] + "-->" + indiceALetra[j]);
                }
            }
        }
    }

    @Override
    public int contarLazos() {
        int counter = 0;
        for (int i = 0; i < matrizAdyacencia.length; i++){
            if(matrizAdyacencia[i][i]==1){
                counter++;
            }
        }
        return counter;
    }

    @Override
    public char[] mostrarVerticeLazo() {
        ArrayList<Character> results = new ArrayList<>();
        for (int i = 0; i < matrizAdyacencia.length; i++){
            if(matrizAdyacencia[i][i] == 1){
                results.add(indiceALetra[i]);
            }
        }
        //convertirlo a char par cumplor con la firma del método
        char[] res = new char[results.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = results.get(i);
        }
        return res;
    }

    @Override
    //mira la columna, pero no la fila, con unsa basta
    public boolean isVerticeAislado(char v) { //significa que la fila y columna de ese nodo es todo 0
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
        for (char letra : letraAIndice.keySet()) { //keyset devuelve un mapa con todas las keys
            if (isVerticeAislado(letra)) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public char[] returnVerticeAislado() {
        ArrayList<Character> results = new ArrayList<>();
        for (char letra : letraAIndice.keySet()) { //keyset devuelve un mapa con todas las keys
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

        // 3. armar matriz nueva y vertices nuevos, solo con los indices validos
        int nuevoN = indicesValidos.size();
        int[][] nuevaMatriz = new int[nuevoN][nuevoN];
        char[] nuevosVertices = new char[nuevoN];

        for (int i = 0; i < nuevoN; i++) {
            nuevosVertices[i] = indiceALetra[indicesValidos.get(i)];
            for (int j = 0; j < nuevoN; j++) {
                nuevaMatriz[i][j] = sinLazos[indicesValidos.get(i)][indicesValidos.get(j)];
            }
        }

        // 4. devolver grafo nuevo
        return new Ej1(nuevaMatriz, nuevosVertices);
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

        // 1. contar aristas
        ArrayList<int[]> aristas = new ArrayList<>(); // cada elemento: {i, j}
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) { // j=i para incluir lazos, sin repetir pares
                if (matrizAdyacencia[i][j] == 1) {
                    aristas.add(new int[]{i, j});
                }
            }
        }

        // 2. construir matriz n x m
        int m = aristas.size();
        int[][] incidencia = new int[n][m];

        // 3. llenar
        for (int k = 0; k < m; k++) {
            int i = aristas.get(k)[0];
            int j = aristas.get(k)[1];
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
