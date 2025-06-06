package View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Model.Gasto;

public class GastoView {
    private Scanner scanner;
    private SimpleDateFormat dateFormat;

    public GastoView() {
        scanner = new Scanner(System.in);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
    }

    public String promptTipoGasto() {
        System.out.println("Seleccione tipo de gasto (o presione ENTER para omitir):");
        for (int i = 0; i < Gasto.TIPOS_VALIDOS.size(); i++) {
            System.out.println((i + 1) + ". " + Gasto.TIPOS_VALIDOS.get(i));
        }
        System.out.print("Opción: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null; // devuelve null si el usuario no quiere filtrar
        } else {
            int opcion = Integer.parseInt(input);
            if (opcion >= 1 && opcion <= Gasto.TIPOS_VALIDOS.size()) {
                return Gasto.TIPOS_VALIDOS.get(opcion - 1);
            } else {
                System.out.print("Opción inválida. Intente de nuevo: ");
                return promptTipoGasto(); // Recursive call for valid input
            }
        }
    }


    public int promptKilometraje() {
        System.out.print("Introduzca el kilometraje actual: ");
        while (true) {
            try {
                int km = Integer.parseInt(scanner.nextLine().trim());
                if (km >= 0) return km;
                else System.out.print("Kilometraje no puede ser negativo. Intente de nuevo: ");
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Intente de nuevo: ");
            }
        }
    }

    public Date promptFecha() {
        System.out.print("Introduzca la fecha (YYYY-MM-DD): ");
        while (true) {
            try {
                String fechaStr = scanner.nextLine().trim();
                return dateFormat.parse(fechaStr);
            } catch (ParseException e) {
                System.out.print("Fecha inválida. Intente nuevamente (YYYY-MM-DD): ");
            }
        }
    }

    public double promptImporte() {
        System.out.print("Introduzca el importe: ");
        while (true) {
            try {
                double imp = Double.parseDouble(scanner.nextLine().trim());
                if (imp >= 0) return imp;
                else System.out.print("Importe no puede ser negativo. Intente de nuevo: ");
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Intente de nuevo: ");
            }
        }
    }

    public String promptDescripcion() {
        System.out.print("Introduzca descripción (opcional, ENTER para omitir): ");
        return scanner.nextLine().trim();
    }

    public void showGastos(List<Gasto> gastos) {
        if (gastos.isEmpty()) {
            System.out.println("No hay gastos para mostrar.");
            return;
        }
        System.out.println("Listado de gastos:");
        System.out.printf("%-15s %-12s %-12s %-10s %-30s\n",
                "Tipo", "Kilometraje", "Fecha", "Importe", "Descripción");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Gasto g : gastos) {
            System.out.printf("%-15s %-12d %-12s %-10.2f %-30s\n",
                    g.getTipo(), g.getKilometraje(), sdf.format(g.getFecha()), g.getImporte(),
                    g.getDescripcion() != null ? g.getDescripcion() : "");
        }
    }

    public Map<String, Object> promptFiltros() {
        Map<String, Object> filtros = new HashMap<>();
        System.out.print("Filtrar por año (YYYY) o ENTER para todos: ");
        String anioStr = scanner.nextLine().trim();
        if (!anioStr.isEmpty()) {
            try {
                Integer anio = Integer.parseInt(anioStr);
                filtros.put("anio", anio);
            } catch (NumberFormatException e) {
                System.out.println("Año inválido, no se aplicará filtro por año.");
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        System.out.print("Filtrar desde fecha (YYYY-MM-DD) o ENTER para omitir: ");
        String desdeStr = scanner.nextLine().trim();
        if (!desdeStr.isEmpty()) {
            try {
                Date desde = sdf.parse(desdeStr);
                filtros.put("fechaInicio", desde);
            } catch (ParseException e) {
                System.out.println("Fecha inicio inválida, no se aplicará filtro por fecha inicio.");
            }
        }

        System.out.print("Filtrar hasta fecha (YYYY-MM-DD) o ENTER para omitir: ");
        String hastaStr = scanner.nextLine().trim();
        if (!hastaStr.isEmpty()) {
            try {
                Date hasta = sdf.parse(hastaStr);
                filtros.put("fechaFin", hasta);
            } catch (ParseException e) {
                System.out.println("Fecha fin inválida, no se aplicará filtro por fecha fin.");
            }
        }

        System.out.print("Filtrar kilometraje mínimo o ENTER para omitir: ");
        String kmMinStr = scanner.nextLine().trim();
        if (!kmMinStr.isEmpty()) {
            try {
                Integer kmMin = Integer.parseInt(kmMinStr);
                filtros.put("kmMin", kmMin);
            } catch (NumberFormatException e) {
                System.out.println("Kilometraje mínimo inválido, no se aplicará filtro de km min.");
            }
        }

        System.out.print("Filtrar kilometraje máximo o ENTER para omitir: ");
        String kmMaxStr = scanner.nextLine().trim();
        if (!kmMaxStr.isEmpty()) {
            try {
                Integer kmMax = Integer.parseInt(kmMaxStr);
                filtros.put("kmMax", kmMax);
            } catch (NumberFormatException e) {
                System.out.println("Kilometraje máximo inválido, no se aplicará filtro de km max.");
            }
        }
        return filtros;
    }
}
