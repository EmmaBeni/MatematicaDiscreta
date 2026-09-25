package Guia2Grafos;

public interface Grafo {
    void mostrar();
    int contarLazos();
    char[] mostrarVerticeLazo();
    boolean isVerticeAislado(char v);
    int contarVerticesAislados();
    char[] returnVerticeAislado();
    Grafo simplificar();
    void getMatrizAdyacencia();
    int[][] getMatrizIncidencia();
}
