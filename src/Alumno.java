import java.util.*;

public class Alumno {
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String documento;
    private List<Inscripcion> inscripciones;
    private Carrera carreraInscrita;

    public Alumno(String nombre, String apellido, String fechaNacimiento, String documento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.documento = documento;
        this.carreraInscrita = null;
    }

    public String getNombre(){
        return nombre;
    }
    public String getApellido(){
        return apellido;
    }
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    public Carrera getCarreraInscrita() {
        return carreraInscrita;
    }
    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }
    public void setCarreraInscrita(Carrera carreraInscrita) {
        this.carreraInscrita = carreraInscrita;
    }

    public void inscribirCarrera(Carrera carrera) {
        this.carreraInscrita = carrera;
    }

    public void inscribirMateria(Materia materia) {
        if (materia != null) {
            Inscripcion nuevaInscripcion = new Inscripcion(this, materia);
            inscripciones.add(nuevaInscripcion);
        }
    }


    boolean puedeCursarMateria(Materia materia) {
        //implementar la lógica específica segun las correlatividades y el plan de estudio
        //retornar true si el alumno puede cursar la materia y false en caso contrario
        return true;
    }

    /*public boolean verificarGraduacion() {
        if (carreraInscrita != null) {
            return haAprobadoMateriasObligatorias() && tieneSuficientesMateriasOptativas();
        }
        return false;
    }
    private boolean haAprobadoMateriasObligatorias() {
        for (Materia materia : carreraInscrita.getMateriasObligatorias()) {
            if (!haAprobadoMateria(materia)) {
                return false;
            }
        }
        return true;
    }
    private boolean tieneSuficientesMateriasOptativas() {
        int optativasAprobadas = 0;
        for (Materia materia : carreraInscrita.getMateriasOptativas()) {
            if (haAprobadoMateria(materia)) {
                optativasAprobadas++;
            }
        }
        return optativasAprobadas >= carreraInscrita.getOptativasParaGraduarse();
    }*/
    private boolean haAprobadoMateria(Materia materia) {
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getMateria().equals(materia)) {
                return inscripcion.aprobada(); //verificar si existe una inscripcion y si la materia esta aprobada
            }
        }
        return false;
    }

    public void registrarNotaParcial(Materia materia, double notaParcial) {
        Inscripcion inscripcion = obtenerInscripcion(materia);

        if (inscripcion != null) {
            //verificar si la materia es promocionable y la nota parcial es suficiente
            if (materia.isPromocionable() && notaParcial >= 7.0) {
                inscripcion.registrarNotaParcial(notaParcial);
                inscripcion.registrarNotaFinal(notaParcial); //en caso de promoción, la nota final es igual a la nota parcial
                inscripcion.setEstadoMateria("APROBADO");
            } else {
                inscripcion.registrarNotaParcial(notaParcial);
                //no registrar nota final si la materia es promocionable
                if (!materia.isPromocionable()) {
                    inscripcion.registrarNotaFinal(-1.0); //marcar nota final como -1.0 para indicar que no se ha registrado todavia
                }
            }
        }
    }

    public void registrarNotaFinal(Materia materia, double notaFinal) {
        Inscripcion inscripcion = obtenerInscripcion(materia);

        if (inscripcion != null) {
            //verificar si la nota final es suficiente
            if (notaFinal >= 4.0) {
                inscripcion.registrarNotaFinal(notaFinal);
                inscripcion.setEstadoMateria("APROBADO");
            }
        }
    }

    private Inscripcion obtenerInscripcion(Materia materia) {
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getMateria().equals(materia)) {
                return inscripcion;
            }
        }
        return null;
    }

}
