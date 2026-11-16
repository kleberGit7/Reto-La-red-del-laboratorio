public class Main {
    public static void main(String[] args) {
        Grafo g = new Grafo();

        System.out.println("===== 1. CONSTRUIR =====");
        String[] equipos = {"Router", "Switch", "PC1", "PC2", "Impresora", "Servidor"};
        for (String e : equipos) g.agregarVertice(e);
        g.agregarArista("Router", "Switch", 1);
        g.agregarArista("Router", "Servidor", 4);
        g.agregarArista("Switch", "PC1", 2);
        g.agregarArista("Switch", "PC2", 2);
        g.agregarArista("PC2", "Impresora", 3);
        g.agregarArista("Servidor", "PC2", 5);
        g.mostrar();
        System.out.println("agregar Router otra vez: " + g.agregarVertice("Router"));
        System.out.println("agregar PC1 -> Proyector (3): " + g.agregarArista("PC1", "Proyector", 3));

        System.out.println("\n===== 2. CONSULTAR =====");
        g.bfs("Router");
        for (String e : equipos) System.out.println("grado(" + e + ") = " + g.grado(e));
        System.out.println("grado(Proyector) = " + g.grado("Proyector"));
        g.cambiarLatencia("Router", "Servidor", 2);
        g.eliminarArista("Servidor", "PC2");
        System.out.println("-- despues de cambiar latencia y eliminar Servidor -> PC2 --");
        g.mostrar();

        System.out.println("\n===== 3. DECIDIR =====");
        g.eliminarVertice("Switch");
        g.mostrar();
        System.out.println("esConexo(Router) = " + g.esConexo("Router"));
        g.mostrarAislados("Router");

        System.out.println("-- agregando 2 enlaces nuevos --");
        g.agregarArista("Router", "PC1", 1);
        g.agregarArista("Router", "PC2", 1);
        g.mostrar();
        System.out.println("esConexo(Router) = " + g.esConexo("Router"));
    }
}
