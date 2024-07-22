import java.util.*;

public class Materia {
    private String nombre;
    private String codigo;
    private int cuatrimestre;
    private String tipo; //obligatoria u optativa
    private boolean promocionable;
    private int cargaHoraria;
    private List<Materia> correlativas;

    public String getNombre(){
        return nombre;
    }
    public int getCuatrimestre() {
        return cuatrimestre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Materia(String nombre, String codigo, int cuatrimestre, String tipo, boolean promocionable, int cargaHoraria, List<Materia> correlativas) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.cuatrimestre = cuatrimestre;
        this.tipo = tipo;
        this.promocionable = promocionable;
        this.cargaHoraria = cargaHoraria;
        this.correlativas = correlativas;
    }

    public boolean isPromocionable() {
        return promocionable;
    }

    public boolean isOptativa() {
        switch (tipo){
            case "obligatoria":
                return false;
            case "optativa":
                return true;
            default:
                throw new IllegalArgumentException("No ha ingresado tipo");
        }
    }


    public List<Materia> getCorrelativas() {
        return correlativas;
    }
}
