public class Grafo {
    private Vertice primero;

    // ---------- Busquedas auxiliares ----------
    private Vertice buscar(String id) {
        for (Vertice v = primero; v != null; v = v.siguiente)
            if (v.id.equals(id)) return v;
        return null;
    }

    private Arista buscarArista(Vertice o, Vertice d) {
        for (Arista a = o.primeraArista; a != null; a = a.siguiente)
            if (a.destino == d) return a;
        return null;
    }

    // ---------- Construir ----------
    public boolean agregarVertice(String id) {
        if (buscar(id) != null) return false; // repetido
        Vertice nuevo = new Vertice(id);
        if (primero == null) primero = nuevo;
        else {
            Vertice ult = primero;
            while (ult.siguiente != null) ult = ult.siguiente;
            ult.siguiente = nuevo;
        }
        return true;
    }

    public boolean agregarArista(String origen, String destino, int latencia) {
        Vertice o = buscar(origen), d = buscar(destino);
        if (o == null || d == null) return false;      // no existe alguno
        if (buscarArista(o, d) != null) return false;  // arista repetida
        Arista nueva = new Arista(d, latencia);
        if (o.primeraArista == null) o.primeraArista = nueva;
        else {
            Arista ult = o.primeraArista;
            while (ult.siguiente != null) ult = ult.siguiente;
            ult.siguiente = nueva;
        }
        return true;
    }

    public void mostrar() {
        for (Vertice v = primero; v != null; v = v.siguiente) {
            System.out.print(v.id + " -> ");
            if (v.primeraArista == null) System.out.print("(sin enlaces)");
            for (Arista a = v.primeraArista; a != null; a = a.siguiente)
                System.out.print("[" + a.destino.id + ", " + a.latencia + "ms] ");
            System.out.println();
        }
    }

    // ---------- Consultar ----------
    private void limpiarVisitas() {
        for (Vertice v = primero; v != null; v = v.siguiente) {
            v.visitado = false;
            v.siguienteCola = null;
        }
    }

    // BFS con cola enlazada usando el campo siguienteCola. Imprime el recorrido y devuelve cuantos visito.
    public int bfs(String inicio) {
        Vertice s = buscar(inicio);
        if (s == null) return 0;
        limpiarVisitas();
        Vertice frente = s, fin = s;
        s.visitado = true;
        int cont = 0;
        StringBuilder sb = new StringBuilder();
        while (frente != null) {
            Vertice actual = frente;
            frente = frente.siguienteCola;
            if (frente == null) fin = null;
            sb.append(actual.id).append(" ");
            cont++;
            for (Arista a = actual.primeraArista; a != null; a = a.siguiente) {
                Vertice w = a.destino;
                if (!w.visitado) {
                    w.visitado = true;
                    w.siguienteCola = null;
                    if (fin == null) { frente = w; fin = w; }
                    else { fin.siguienteCola = w; fin = w; }
                }
            }
        }
        System.out.println("BFS desde " + inicio + ": " + sb.toString().trim());
        return cont;
    }

    // Grado total = aristas salientes + aristas entrantes. -1 si no existe.
    public int grado(String id) {
        Vertice v = buscar(id);
        if (v == null) return -1;
        int g = 0;
        for (Arista a = v.primeraArista; a != null; a = a.siguiente) g++; // salida
        for (Vertice u = primero; u != null; u = u.siguiente)
            for (Arista a = u.primeraArista; a != null; a = a.siguiente)
                if (a.destino == v) g++;                                   // entrada
        return g;
    }

    public boolean cambiarLatencia(String origen, String destino, int nueva) {
        Vertice o = buscar(origen), d = buscar(destino);
        if (o == null || d == null) return false;
        Arista a = buscarArista(o, d);
        if (a == null) return false;
        a.latencia = nueva;
        return true;
    }

    public boolean eliminarArista(String origen, String destino) {
        Vertice o = buscar(origen), d = buscar(destino);
        if (o == null || d == null) return false;
        Arista prev = null;
        for (Arista a = o.primeraArista; a != null; prev = a, a = a.siguiente) {
            if (a.destino == d) {
                if (prev == null) o.primeraArista = a.siguiente;
                else prev.siguiente = a.siguiente;
                return true;
            }
        }
        return false;
    }

    // ---------- Decidir ----------
    public boolean eliminarVertice(String id) {
        Vertice v = buscar(id);
        if (v == null) return false;
        // 1) quitar todas las aristas que llegan a v
        for (Vertice u = primero; u != null; u = u.siguiente)
            eliminarArista(u.id, id);
        // 2) sacar v de la lista de vertices (sus aristas salientes se van con el)
        if (primero == v) primero = v.siguiente;
        else {
            Vertice p = primero;
            while (p.siguiente != v) p = p.siguiente;
            p.siguiente = v.siguiente;
        }
        return true;
    }

    // true si desde 'inicio' se alcanzan TODOS los vertices
    public boolean esConexo(String inicio) {
        Vertice s = buscar(inicio);
        if (s == null) return false;
        int total = 0;
        for (Vertice v = primero; v != null; v = v.siguiente) total++;
        int alcanzados = contarAlcanzables(s);
        return alcanzados == total;
    }

    // Imprime los equipos NO alcanzables desde 'inicio' (aislados)
    public void mostrarAislados(String inicio) {
        Vertice s = buscar(inicio);
        if (s == null) return;
        contarAlcanzables(s); // deja marcado 'visitado'
        System.out.print("Aislados desde " + inicio + ": ");
        for (Vertice v = primero; v != null; v = v.siguiente)
            if (!v.visitado) System.out.print(v.id + " ");
        System.out.println();
    }

    private int contarAlcanzables(Vertice s) {
        limpiarVisitas();
        Vertice frente = s, fin = s;
        s.visitado = true;
        int cont = 0;
        while (frente != null) {
            Vertice actual = frente;
            frente = frente.siguienteCola;
            if (frente == null) fin = null;
            cont++;
            for (Arista a = actual.primeraArista; a != null; a = a.siguiente) {
                Vertice w = a.destino;
                if (!w.visitado) {
                    w.visitado = true;
                    w.siguienteCola = null;
                    if (fin == null) { frente = w; fin = w; }
                    else { fin.siguienteCola = w; fin = w; }
                }
            }
        }
        return cont;
    }
}
