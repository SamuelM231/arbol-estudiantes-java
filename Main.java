import java.util.Scanner;

// Clase Estudiante
class Estudiante {
    int matricula;
    String nombre;

    public Estudiante(int matricula, String nombre) {
        this.matricula = matricula;
        this.nombre = nombre;
    }
}

// Nodo del árbol
class NodoABB {
    Estudiante estudiante;
    NodoABB izquierda, derecha;

    public NodoABB(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}

// Árbol Binario de Búsqueda
class ArbolEstudiantes {
    private NodoABB raiz;

    // INSERTAR (sin duplicados)
    public boolean insertar(Estudiante estudiante) {
        if (buscar(estudiante.matricula) != null) {
            return false; // ya existe
        }
        raiz = insertarRec(raiz, estudiante);
        return true;
    }

    private NodoABB insertarRec(NodoABB actual, Estudiante estudiante) {
        if (actual == null) return new NodoABB(estudiante);

        if (estudiante.matricula < actual.estudiante.matricula)
            actual.izquierda = insertarRec(actual.izquierda, estudiante);
        else
            actual.derecha = insertarRec(actual.derecha, estudiante);

        return actual;
    }

    // BUSCAR
    public Estudiante buscar(int matricula) {
        return buscarRec(raiz, matricula);
    }

    private Estudiante buscarRec(NodoABB actual, int matricula) {
        if (actual == null) return null;

        if (matricula == actual.estudiante.matricula)
            return actual.estudiante;

        if (matricula < actual.estudiante.matricula)
            return buscarRec(actual.izquierda, matricula);
        else
            return buscarRec(actual.derecha, matricula);
    }

    // ELIMINAR
    public void eliminar(int matricula) {
        raiz = eliminarRec(raiz, matricula);
    }

    private NodoABB eliminarRec(NodoABB actual, int matricula) {
        if (actual == null) return null;

        if (matricula < actual.estudiante.matricula) {
            actual.izquierda = eliminarRec(actual.izquierda, matricula);
        } else if (matricula > actual.estudiante.matricula) {
            actual.derecha = eliminarRec(actual.derecha, matricula);
        } else {
            if (actual.izquierda == null) return actual.derecha;
            if (actual.derecha == null) return actual.izquierda;

            NodoABB sucesor = minimo(actual.derecha);
            actual.estudiante = sucesor.estudiante;
            actual.derecha = eliminarRec(actual.derecha, sucesor.estudiante.matricula);
        }
        return actual;
    }

    private NodoABB minimo(NodoABB nodo) {
        while (nodo.izquierda != null) nodo = nodo.izquierda;
        return nodo;
    }

    // MOSTRAR
    public void mostrar() {
        if (raiz == null) {
            System.out.println("No hay estudiantes.");
        } else {
            inOrden(raiz);
        }
    }

    private void inOrden(NodoABB nodo) {
        if (nodo != null) {
            inOrden(nodo.izquierda);
            System.out.println("Matricula: " + nodo.estudiante.matricula +
                    " | Nombre: " + nodo.estudiante.nombre);
            inOrden(nodo.derecha);
        }
    }

    // CONTAR NODOS
    public int contar() {
        return contarRec(raiz);
    }

    private int contarRec(NodoABB nodo) {
        if (nodo == null) return 0;
        return 1 + contarRec(nodo.izquierda) + contarRec(nodo.derecha);
    }

    // ALTURA DEL ÁRBOL
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(NodoABB nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(alturaRec(nodo.izquierda), alturaRec(nodo.derecha));
    }
}

// MAIN
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArbolEstudiantes arbol = new ArbolEstudiantes();
        int opcion;

        do {
            System.out.println("\n========= MENU =========");
            System.out.println("1. Insertar estudiante");
            System.out.println("2. Buscar estudiante");
            System.out.println("3. Eliminar estudiante");
            System.out.println("4. Mostrar estudiantes");
            System.out.println("5. Contar estudiantes");
            System.out.println("6. Altura del arbol");
            System.out.println("7. Salir");
            System.out.print("Opcion: ");

            opcion = sc.nextInt();

            switch (opcion) {

                case 1:
                    System.out.print("Matricula: ");
                    int m = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nombre: ");
                    String n = sc.nextLine();

                    if (arbol.insertar(new Estudiante(m, n))) {
                        System.out.println("Insertado correctamente");
                    } else {
                        System.out.println("Error: matricula ya existe");
                    }
                    break;

                case 2:
                    System.out.print("Matricula: ");
                    int b = sc.nextInt();

                    Estudiante e = arbol.buscar(b);
                    if (e != null)
                        System.out.println("Encontrado: " + e.nombre);
                    else
                        System.out.println("No existe");
                    break;

                case 3:
                    System.out.print("Matricula: ");
                    arbol.eliminar(sc.nextInt());
                    System.out.println("Eliminado");
                    break;

                case 4:
                    arbol.mostrar();
                    break;

                case 5:
                    System.out.println("Total estudiantes: " + arbol.contar());
                    break;

                case 6:
                    System.out.println("Altura del árbol: " + arbol.altura());
                    break;

                case 7:
                    System.out.println("Adios");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 7);

        sc.close();
    }
}