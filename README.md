# La red del laboratorio — Grafo dirigido con multilistas

Reto práctico en **Java**: representar la red de un laboratorio como un grafo dirigido y ponderado (latencia en ms) usando únicamente las clases `Vertice`, `Arista` y `Grafo`. **No** se usa `HashMap`, `ArrayList` ni librerías de grafos.

## Red modelada

Equipos: `Router`, `Switch`, `PC1`, `PC2`, `Impresora`, `Servidor`

| Origen | Destino | Latencia (ms) |
|---|---|---|
| Router | Switch | 1 |
| Router | Servidor | 4 |
| Switch | PC1 | 2 |
| Switch | PC2 | 2 |
| PC2 | Impresora | 3 |
| Servidor | PC2 | 5 |

## Estructura del proyecto

```
.
├── Vertice.java   # equipo + lista de aristas salientes + campos auxiliares para BFS
├── Arista.java    # destino, latencia y siguiente arista
├── Grafo.java     # lista de vértices y todas las operaciones
├── Main.java      # ejecuta las partes 1, 2 y 3 del reto
├── capturas/      # capturas de la consola
└── Informe_Reto_Red_Laboratorio.docx
```

## Cómo funciona la multilista

- `Grafo` guarda solo el primer `Vertice`.
- Cada `Vertice` apunta al siguiente vértice y a su primera `Arista`.
- Cada `Arista` apunta a su vértice destino y a la siguiente arista del mismo origen.
- El **BFS** usa una cola enlazada construida con el campo `siguienteCola` de `Vertice`, por lo que no necesita estructuras externas.

## Operaciones principales

| Método | Descripción |
|---|---|
| `agregarVertice(id)` | Inserta un equipo; `false` si ya existe |
| `agregarArista(o, d, ms)` | Inserta un enlace; `false` si falta un equipo o el enlace ya existe |
| `mostrar()` | Imprime la lista de adyacencia |
| `bfs(inicio)` | Recorrido en anchura desde un equipo |
| `grado(id)` | Aristas de salida + entrada; `-1` si el equipo no existe |
| `cambiarLatencia(o, d, ms)` | Modifica la latencia de un enlace |
| `eliminarArista(o, d)` | Elimina un enlace |
| `eliminarVertice(id)` | Elimina un equipo y todos sus enlaces (entrantes y salientes) |
| `esConexo(inicio)` | `true` si desde `inicio` se alcanzan todos los equipos |

## Compilar y ejecutar

Requiere JDK 8 o superior.

```bash
javac *.java
java Main
```

## Resultados

### 1. Construir
Se insertan los equipos y enlaces. Agregar `Router` otra vez y agregar `PC1 → Proyector (3)` devuelven `false`.


### 2. Consultar
BFS desde Router: `Router Switch Servidor PC1 PC2 Impresora`. Luego se cambia la latencia Router → Servidor a 2 ms y se elimina Servidor → PC2.


```java
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
```

### 3. Decidir
Se elimina el Switch y `esConexo("Router")` devuelve `false`.

- **Equipos aislados:** `PC1`, `PC2` e `Impresora`.
- **Enlaces nuevos mínimos:** **2**. PC1 queda solo y `PC2 → Impresora` forma otro grupo; se necesita un enlace hacia cada uno (por ejemplo `Router → PC1` y `Router → PC2`).
- Tras agregarlos, `esConexo("Router")` devuelve `true`.

# La red del laboratorio — Grafo dirigido con multilistas

Reto práctico en **Java**: representar la red de un laboratorio como un grafo dirigido y ponderado (latencia en ms) usando únicamente las clases `Vertice`, `Arista` y `Grafo`. **No** se usa `HashMap`, `ArrayList` ni librerías de grafos.

## Red modelada

Equipos: `Router`, `Switch`, `PC1`, `PC2`, `Impresora`, `Servidor`

| Origen | Destino | Latencia (ms) |
|---|---|---|
| Router | Switch | 1 |
| Router | Servidor | 4 |
| Switch | PC1 | 2 |
| Switch | PC2 | 2 |
| PC2 | Impresora | 3 |
| Servidor | PC2 | 5 |

## Estructura del proyecto

```
.
├── Vertice.java   # equipo + lista de aristas salientes + campos auxiliares para BFS
├── Arista.java    # destino, latencia y siguiente arista
├── Grafo.java     # lista de vértices y todas las operaciones
├── Main.java      # ejecuta las partes 1, 2 y 3 del reto
├── capturas/      # capturas de la consola
└── Informe_Reto_Red_Laboratorio.docx
```

## Cómo funciona la multilista

- `Grafo` guarda solo el primer `Vertice`.
- Cada `Vertice` apunta al siguiente vértice y a su primera `Arista`.
- Cada `Arista` apunta a su vértice destino y a la siguiente arista del mismo origen.
- El **BFS** usa una cola enlazada construida con el campo `siguienteCola` de `Vertice`, por lo que no necesita estructuras externas.

## Operaciones principales

| Método | Descripción |
|---|---|
| `agregarVertice(id)` | Inserta un equipo; `false` si ya existe |
| `agregarArista(o, d, ms)` | Inserta un enlace; `false` si falta un equipo o el enlace ya existe |
| `mostrar()` | Imprime la lista de adyacencia |
| `bfs(inicio)` | Recorrido en anchura desde un equipo |
| `grado(id)` | Aristas de salida + entrada; `-1` si el equipo no existe |
| `cambiarLatencia(o, d, ms)` | Modifica la latencia de un enlace |
| `eliminarArista(o, d)` | Elimina un enlace |
| `eliminarVertice(id)` | Elimina un equipo y todos sus enlaces (entrantes y salientes) |
| `esConexo(inicio)` | `true` si desde `inicio` se alcanzan todos los equipos |

## Compilar y ejecutar

Requiere JDK 8 o superior.

```bash
javac *.java
java Main
```

## Resultados

### 1. Construir
Se insertan los equipos y enlaces. Agregar `Router` otra vez y agregar `PC1 → Proyector (3)` devuelven `false`.


### 2. Consultar
BFS desde Router: `Router Switch Servidor PC1 PC2 Impresora`. Luego se cambia la latencia Router → Servidor a 2 ms y se elimina Servidor → PC2.


```java
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
```

### 3. Decidir
Se elimina el Switch y `esConexo("Router")` devuelve `false`.

- **Equipos aislados:** `PC1`, `PC2` e `Impresora`.
- **Enlaces nuevos mínimos:** **2**. PC1 queda solo y `PC2 → Impresora` forma otro grupo; se necesita un enlace hacia cada uno (por ejemplo `Router → PC1` y `Router → PC2`).
- Tras agregarlos, `esConexo("Router")` devuelve `true`.

# Reto-La-red-del-laboratorio
