public class Arista {
    Vertice destino;
    int latencia;
    Arista siguiente; // siguiente arista en la lista del vertice origen

    public Arista(Vertice destino, int latencia) {
        this.destino = destino;
        this.latencia = latencia;
    }
}
