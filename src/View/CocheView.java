package View;


import java.util.List;
import java.util.Scanner;

import Model.Coche;

public class CocheView {
    private Scanner scanner;

    public CocheView() {
        scanner = new Scanner(System.in);
    }

    public String promptMarca() {
        System.out.print("Introduzca la marca del coche: ");
        return scanner.nextLine().trim();
    }

    public String promptModelo() {
        System.out.print("Introduzca el modelo del coche: ");
        return scanner.nextLine().trim();
    }

    public String promptMatricula() {
        System.out.print("Introduzca la matrícula: ");
        return scanner.nextLine().trim();
    }

    public int promptAnio() {
        System.out.print("Introduzca el año del coche: ");
        while (true) {
            try {
                int anio = Integer.parseInt(scanner.nextLine().trim());
                return anio;
            } catch (NumberFormatException e) {
                System.out.print("Año inválido. Introduzca un número válido: ");
            }
        }
    }

    public void showCoches(List<Coche> coches) {
        if (coches.isEmpty()) {
            System.out.println("No tiene coches asociados.");
            return;
        }
        System.out.println("Lista de coches:");
        int i = 1;
        for (Coche c : coches) {
            System.out.println(i + ". " + c);
            i++;
        }
    }

    public int promptSeleccionCoche(List<Coche> coches, String mensaje) {
        System.out.println(mensaje);
        for (int i = 0; i < coches.size(); i++) {
            System.out.println((i + 1) + ". " + coches.get(i).getMarca() + " " + coches.get(i).getModelo());
        }
        System.out.print("Seleccione opción (número): ");
        while (true) {
            try {
                int sel = Integer.parseInt(scanner.nextLine().trim());
                if (sel >= 1 && sel <= coches.size()) {
                    return sel - 1;
                } else {
                    System.out.print("Opción no válida. Intente nuevamente: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Introduzca un número: ");
            }
        }
    }

    public void showMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
