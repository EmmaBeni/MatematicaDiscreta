package Guia2Grafos;

//si me pide la matriz de adyacencia como un resultado a producur no puede ser la primer impl que toma la matriz de adyacencia como input
public class Ej2 {
    public static void main(String[] args) {
        char[] vertices = {'A', 'B', 'C', 'D', 'E'};
        char[][] aristas = {
                {'A', 'B'},
                {'B', 'C'},
                {'C', 'C'}, // lazo en C
                // D no tiene ninguna arista -> aislado
                // E tampoco -> aislado
        };

        Grafo g = new Ej1Mejorado(vertices, aristas);

        System.out.println("--- Mostrar grafo ---");
        g.mostrar();

        System.out.println("\n--- Cantidad de lazos ---");
        System.out.println(g.contarLazos()); // esperado: 1

        System.out.println("\n--- Vertices con lazos ---");
        for (char c : g.mostrarVerticeLazo()) System.out.print(c + " "); // C

        System.out.println("\n\n--- Es D aislado? ---");
        System.out.println(g.isVerticeAislado('D')); // true

        System.out.println("--- Es A aislado? ---");
        System.out.println(g.isVerticeAislado('A')); // false

        System.out.println("\n--- Cantidad de aislados ---");
        System.out.println(g.contarVerticesAislados()); // 2

        System.out.println("\n--- Vertices aislados ---");
        for (char c : g.returnVerticeAislado()) System.out.print(c + " "); // D E

        System.out.println("\n\n--- Grafo simplificado (sin lazos, sin aislados) ---");
        Grafo simplificado = g.simplificar();
        simplificado.mostrar(); // debería mostrar solo A-B, B-C (sin lazo, sin D ni E)

        System.out.println("\n--- Matriz de adyacencia ---");
        g.getMatrizAdyacencia();

        System.out.println("\n--- Matriz de incidencia ---");
        int[][] inc = g.getMatrizIncidencia();
        for (int[] fila : inc) {
            for (int val : fila) System.out.print(val + " ");
            System.out.println();
        }
    }
}