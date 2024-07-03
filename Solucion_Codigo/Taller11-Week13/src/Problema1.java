
import java.util.Random;

abstract class Personaje {

    protected String nombre; //
    protected int salud; // puntos de vida
    protected int nivel; // nivel de experiencia
    protected int stamina; // atributo agregado

    public Personaje(String nombre, int salud, int nivel, int stamina) {
        this.nombre = nombre;
        this.salud = 100; // Todos inician con 100 de salud
        this.nivel = 1; // NIvel de starter 1. Nivel avanzado : 100
        this.stamina = stamina;
    }

    public int obtenerSalud() {
        return salud;
    }

    public int getNivel() {
        return nivel;
    }

    public String getCharacterName() {
        return nombre;
    }

    public abstract void atacar(Personaje atacado);

    public abstract void defender();

// Otros métodos como obtenerNombre(), obtenerSalud(), obtenerNivel(), etc.
}

class Guerrero extends Personaje {

    private Arma arma; // el guerrero ataca con una arma

    public Guerrero(String nombre, int stamina) {
        super(nombre, 100, 1, stamina);
        this.arma = new Espada();
    }

    @Override
    public void atacar(Personaje atacado) {
        arma.atacar();
        if (atacado instanceof Mago) {
            salud -= 3;
            atacado.defender();
        } else if (atacado instanceof Arquero) {
            salud -= 2;
            atacado.defender();
        } else if (atacado instanceof Guerrero) {
            atacado.defender();
            atacado.atacar(this);
        }
        stamina--;
    }

    @Override
    public void defender() {
        this.stamina -= 6;
    }

    public void recargar() {
        arma.recargar();
        this.stamina = 10;
    }

// Otros métodos como obtenerArma(), etc.
}

class Mago extends Personaje {

    private int mana; // El mago tiene reservas de mana para arrojar sus hechizos. El baculo arroja los hechisos
    private Arma arma;

    public Mago(int stamina) {
        super("Mago", 80, 1, stamina);
        this.mana = 10; // Todos los magos inician con la cantidad maxima de mana: 10
        this.arma = new Baculo();
    }

    public void lanzarHechizo() {
// Usar mana para lanzar un hechizo , tiene un costo de 3 puntos de mana. Se defiende solo con su mana, sin nada de stamina
        this.mana -= 3;
    }

    @Override
    public void atacar(Personaje atacado) { // usa el baculo manualmente para atacar
        arma.atacar();
        if (atacado instanceof Mago) {
            atacado.defender();
            atacado.atacar(this);
        } else if (atacado instanceof Arquero) {
            salud -= 3;
            atacado.defender();
        } else if (atacado instanceof Guerrero) {
            salud -= 4;
            atacado.defender();
        }
    }

    @Override
    public void defender() {
        this.mana -= 6;
// cada vez que se defiende pierde mas de la mitad su mana
    }

    public void recargar() {
        arma.recargar();
        this.stamina = 10;
    }
}

class Arquero extends Personaje {

    private int flechas; // Un arquero se defiende con flechas
    private Arma arma;

    public Arquero(String nombre, int flechas, int stamina) {
        super(nombre, 120, 1, stamina);
        this.flechas = flechas;
        this.arma = new Arco();
    }

    @Override
    public void atacar(Personaje atacado) {
        if (flechas > 0) {
            arma.atacar();
            flechas--;
        } else {
            System.out.println("No hay mas flechas!");
        }
        if (atacado instanceof Mago) { // instance of me dice Si fue atacado por esa clase
            atacado.defender();
            atacado.atacar(this);
        } else if (atacado instanceof Arquero) {
            atacado.defender();
            atacado.atacar(this);
        } else if (atacado instanceof Guerrero) {
            salud -= 4;
            atacado.defender();
        }
        stamina--;
    }

    public void recargar() {
        arma.recargar();
        flechas = 10; // recargamos las flechas
        this.stamina = 10;
    }

    public int obtenerFlechas() {

        return flechas;
    }

    @Override
    public void defender() {
        flechas--;
    }

}

interface Arma {

    void atacar();

    void recargar();
}

class Espada implements Arma {

    public void atacar() {
        System.out.println("El guerrero ataca con una espada!");
    }

    public void recargar() {
// No se necesita recargar una espada
    }

}

class Baculo implements Arma {

    Random r;

    public void atacar() {
        r = new Random(2-1);
        if (r.nextInt() == 0) {
            System.out.println("El mago lanza el mana!");
        } else {
            System.out.println("El mago pego con su baculo!");
        }
    }

    public void recargar() {
// No se necesita recargar un báculo
    // Otra implementacion para el mana
    }
}

class Arco implements Arma {

    public void atacar() {
        System.out.println("El arquero dispara una flecha!");
    }

    public void recargar() {
// No se necesita recargar un arco
    }
}

public class Problema1 {

    public static void main(String[] args) {
        Guerrero guerrero = new Guerrero("Guerrero educado", 10);
        Mago mago = new Mago(10);
        Arquero arquero = new Arquero("Arquero", 20, 10);

        System.out.println("Salud del guerrero antes de atacar: " + guerrero.obtenerSalud());
        guerrero.atacar(mago);
        System.out.println("Salud del mago despues de ser atacado por el guerrero: " + mago.obtenerSalud());

        mago.lanzarHechizo();
        mago.atacar(guerrero);
        System.out.println("Salud del guerrero despues de ser atacado por el mago: " + guerrero.obtenerSalud());

        arquero.atacar(guerrero);
        System.out.println("Salud del guerrero despues de ser atacado por el arquero: " + guerrero.obtenerSalud());

        guerrero.defender();
        guerrero.atacar(arquero);
        System.out.println("Salud del arquero despues de ser atacado por el guerrero: " + arquero.obtenerSalud());

        guerrero.recargar();
        mago.recargar();
        arquero.recargar();
    }
}
