
import java.text.DecimalFormat;

// Clase abstracta para Menu
abstract class Menu {

    protected String nombrePlato;
    protected double valorMenu;
    protected double valorInicialMenu;

    public abstract double calcularValorTotal();

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return "Nombre del Plato: " + nombrePlato + ", Valor del Menu: $" + df.format(valorMenu);
    }
}

// Menu a la Carta
class MenuAlaCarta extends Menu {

    private double valorPorcionGuarnicion;
    private double valorBebida;
    private double porcentajeServicioAdicional;

    public MenuAlaCarta(String nombrePlato, double valorMenu, double valorInicialMenu, double valorPorcionGuarnicion, double valorBebida, double porcentajeServicioAdicional) {
        this.nombrePlato = nombrePlato;
        this.valorMenu = valorMenu;
        this.valorInicialMenu = valorInicialMenu;
        this.valorPorcionGuarnicion = valorPorcionGuarnicion;
        this.valorBebida = valorBebida;
        this.porcentajeServicioAdicional = porcentajeServicioAdicional;
    }

    @Override
    public double calcularValorTotal() {
        return valorInicialMenu + valorPorcionGuarnicion + valorBebida + (valorInicialMenu * (porcentajeServicioAdicional / 100));
    }
}

// Menu del Dia
class MenuDelDia extends Menu {

    private double valorPostre;
    private double valorBebida;

    public MenuDelDia(String nombrePlato, double valorMenu, double valorInicialMenu, double valorPostre, double valorBebida) {
        this.nombrePlato = nombrePlato;
        this.valorMenu = valorMenu;
        this.valorInicialMenu = valorInicialMenu;
        this.valorPostre = valorPostre;
        this.valorBebida = valorBebida;
    }

    @Override
    public double calcularValorTotal() {
        return valorInicialMenu + valorPostre + valorBebida;
    }
}

// Menu de Ninos
class MenuDeNinos extends Menu {

    private double valorPorcionHelado;
    private double valorPorcionPastel;

    public MenuDeNinos(String nombrePlato, double valorMenu, double valorInicialMenu, double valorPorcionHelado, double valorPorcionPastel) {
        this.nombrePlato = nombrePlato;
        this.valorMenu = valorMenu;
        this.valorInicialMenu = valorInicialMenu;
        this.valorPorcionHelado = valorPorcionHelado;
        this.valorPorcionPastel = valorPorcionPastel;
    }

    @Override
    public double calcularValorTotal() {
        return valorInicialMenu + valorPorcionHelado + valorPorcionPastel;
    }
}

// Menu Economico
class MenuEconomico extends Menu {

    private double porcentajeDescuento;

    public MenuEconomico(String nombrePlato, double valorMenu, double valorInicialMenu, double porcentajeDescuento) {
        this.nombrePlato = nombrePlato;
        this.valorMenu = valorMenu;
        this.valorInicialMenu = valorInicialMenu;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double calcularValorTotal() {
        return valorInicialMenu - (valorInicialMenu * (porcentajeDescuento / 100));
    }
}

// Cuenta
class Cuenta {

    private String nombreCliente;
    private Menu[] menus;
    private double subTotal;
    private double impuesto;
    private double totalAPagar;

    public Cuenta(String nombreCliente, Menu[] menus) {
        this.nombreCliente = nombreCliente;
        this.menus = menus;
        calcularCuenta();
    }

    private void calcularCuenta() {
        subTotal = 0;
        for (Menu menu : menus) {
            subTotal += menu.calcularValorTotal();
        }
        impuesto = subTotal * 0.19;
        totalAPagar = subTotal + impuesto;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public Menu[] getMenus() {
        return menus;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public double getTotalAPagar() {
        return totalAPagar;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre del Cliente: ").append(nombreCliente).append("\n");
        sb.append("Menus Ordenados:\n");
        for (Menu menu : menus) {
            sb.append("- ").append(menu).append("\n");
        }
        sb.append("Subtotal: $").append(df.format(subTotal)).append("\n");
        sb.append("Impuesto: $").append(df.format(impuesto)).append("\n");
        sb.append("Total a Pagar: $").append(df.format(totalAPagar));
        return sb.toString();
    }
}

public class Problema2 {

    public static void main(String[] args) {
        // Crear algunos menus
        Menu menuAlaCarta = new MenuAlaCarta("Bistec", 25.0, 20.0, 5.0, 3.0, 10.0);
        Menu menuDelDia = new MenuDelDia("Sopa de Pollo", 15.0, 12.0, 3.0, 2.0);
        Menu menuDeNinos = new MenuDeNinos("Nuggets de Pollo", 10.0, 8.0, 2.0, 1.5);
        Menu menuEconomico = new MenuEconomico("Plato de Verduras", 8.0, 10.0, 20.0);

        // Crear una cuenta
        Menu[] menus = {menuAlaCarta, menuDelDia, menuDeNinos, menuEconomico};
        Cuenta cuenta = new Cuenta("Juan Perez", menus);
        System.out.println("Cliente "+cuenta.getNombreCliente()+" ordenara un plato de todos los menus existentes.");
        // Imprimir la cuenta
        System.out.println(cuenta);
    }

}
