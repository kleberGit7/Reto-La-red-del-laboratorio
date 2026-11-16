public class Vertice {
    String id;
    Vertice siguiente;      // multilista: siguiente vertice del grafo
    Arista primeraArista;   // multilista: lista de aristas salientes

    // Auxiliares para BFS (sin usar ArrayList ni colas de la libreria)
    boolean visitado;
    Vertice siguienteCola;

    public Vertice(String id) {
        this.id = id;
    }
}
