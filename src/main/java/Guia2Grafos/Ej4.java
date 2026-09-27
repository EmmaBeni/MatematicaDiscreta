package Guia2Grafos;

import java.util.ArrayList;
import java.util.HashMap;

// LISTA DE ADYACENCIA
// Representación mediante mapa/arreglo de listas de adyacentes
public class Ej4 implements Grafo {

    private HashMap<Integer, int[]> listaAdyacencia;
    private HashMap<Character, Integer> letraAIndice;
    private char[] indiceALetra;

    public Ej4(char[] vertices, char[][] aristas) {
        this.indiceALetra = vertices;
        this.letraAIndice = new HashMap<>();
        for (int i = 0; i < vertices.length; i++) {
            letraAIndice.put(vertices[i], i);
        }

        // Construcción de listas de adyacentes usando listas temporales
        HashMap<Integer, ArrayList<Integer>> temp = new HashMap<>();
        for (int i = 0; i < vertices.length; i++) {
            temp.put(i, new ArrayList<>());
        }

        for (char[] arista : aristas) {
            int u = letraAIndice.get(arista[0]);
            int v = letraAIndice.get(arista[1]);

            if (!temp.get(u).contains(v)) {
                temp.get(u).add(v);
            }
            // Si no es un lazo, agregamos la conexión inversa (grafo no dirigido)
            if (u != v && !temp.get(v).contains(u)) {
                temp.get(v).add(u);
            }
        }

        // Convertir a HashMap<Integer, int[]>
        this.listaAdyacencia = new HashMap<>();
        for (int i = 0; i < vertices.length; i++) {
            ArrayList<Integer> vecinos = temp.get(i);
            int[] arr = new int[vecinos.size()];
            for (int k = 0; k < vecinos.size(); k++) {
                arr[k] = vecinos.get(k);
            }
            this.listaAdyacencia.put(i, arr);
        }
    }

    public Ej4(char[] vertices, HashMap<Integer, int[]> listaAdyacencia) {
        this.indiceALetra = vertices;
        this.letraAIndice = new HashMap<>();
        for (int i = 0; i < vertices.length; i++) {
            letraAIndice.put(vertices[i], i);
        }
        this.listaAdyacencia = listaAdyacencia;
    }

    @Override
    public void mostrar() {
        for (int i = 0; i < indiceALetra.length; i++) {
            System.out.print(indiceALetra[i] + ": ");
            int[] vecinos = listaAdyacencia.get(i);
            if (vecinos != null) {
                for (int vecino : vecinos) {
                    System.out.print(indiceALetra[vecino] + " ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public int contarLazos() {
        int counter = 0;
        for (int i = 0; i < indiceALetra.length; i++) {
            int[] vecinos = listaAdyacencia.get(i);
            if (vecinos != null) {
                for (int vecino : vecinos) {
                    if (vecino == i) {
                        counter++;
                        break; // Ya encontramos el lazo en este vértice
                    }
                }
            }
        }
        return counter;
    }

    @Override
    public char[] mostrarVerticeLazo() {
        ArrayList<Character> results = new ArrayList<>();
        for (int i = 0; i < indiceALetra.length; i++) {
            int[] vecinos = listaAdyacencia.get(i);
            if (vecinos != null) {
                for (int vecino : vecinos) {
                    if (vecino == i) {
                        results.add(indiceALetra[i]);
                        break;
                    }
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
        int[] vecinos = listaAdyacencia.get(indice);
        return vecinos == null || vecinos.length == 0;
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
        int n = indiceALetra.length;

        // 1. Detectar vértices válidos (que tengan aristas con otros vértices, sin contar lazos)
        ArrayList<Integer> indicesValidos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[] vecinos = listaAdyacencia.get(i);
            boolean tieneVecinoNoLazo = false;
            if (vecinos != null) {
                for (int vecino : vecinos) {
                    if (vecino != i) {
                        tieneVecinoNoLazo = true;
                        break;
                    }
                }
            }
            if (tieneVecinoNoLazo) {
                indicesValidos.add(i);
            }
        }

        // 2. Construir nuevo arreglo de vértices
        int nuevoN = indicesValidos.size();
        char[] nuevosVertices = new char[nuevoN];
        for (int i = 0; i < nuevoN; i++) {
            nuevosVertices[i] = indiceALetra[indicesValidos.get(i)];
        }

        // 3. Obtener aristas entre vértices válidos sin lazos (vecino > i para no duplicar)
        ArrayList<char[]> nuevasAristas = new ArrayList<>();
        for (int a = 0; a < nuevoN; a++) {
            int i = indicesValidos.get(a);
            int[] vecinos = listaAdyacencia.get(i);
            if (vecinos != null) {
                for (int vecino : vecinos) {
                    if (vecino > i && indicesValidos.contains(vecino)) {
                        nuevasAristas.add(new char[]{indiceALetra[i], indiceALetra[vecino]});
                    }
                }
            }
        }

        return new Ej4(nuevosVertices, nuevasAristas.toArray(new char[0][]));
    }

    @Override
    public void getMatrizAdyacencia() {
        int n = indiceALetra.length;
        int[][] matriz = new int[n][n];
        for (int i = 0; i < n; i++) {
            int[] vecinos = listaAdyacencia.get(i);
            if (vecinos != null) {
                for (int vecino : vecinos) {
                    matriz[i][vecino] = 1;
                }
            }
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

        // 1. Obtener aristas únicas (j >= i para incluir lazos una sola vez y no duplicar no dirigidas)
        ArrayList<int[]> aristas = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[] vecinos = listaAdyacencia.get(i);
            if (vecinos != null) {
                for (int j : vecinos) {
                    if (j >= i) {
                        aristas.add(new int[]{i, j});
                    }
                }
            }
        }

        // 2. Construir matriz n x m
        int m = aristas.size();
        int[][] incidencia = new int[n][m];

        // 3. Llenar valores de incidencia
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
