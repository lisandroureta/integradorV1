import javax.swing.*;
import java.util.*;

public class Carrera {
    private String nombre;
    private int duracion;
    private int optativasParaGraduarse;
    private List<Materia> materias;
    private PlanEstudio planEstudio;

    public Carrera(String nombre, int duracion, int optativasParaGraduarse, List<Materia> materias, PlanEstudio planEstudio) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.optativasParaGraduarse = optativasParaGraduarse;
        this.materias = materias;
        this.planEstudio = planEstudio;
    }

    public String getNombre() {
        return nombre;
    }

    public PlanEstudio getPlanEstudio() {
        return planEstudio;
    }

    public List<Materia> getMaterias() {
        return this.materias;
    }


    public List<Materia> obtenerMateriasCursables(Alumno alumno) {
        return planEstudio.obtenerMateriasCursables(alumno, this);
    }
    public boolean verificarGraduacion(Alumno alumno) {
        try {
            //verificar si el alumno aprobó todas las materias obligatorias
            boolean aproboObligatorias = alumno.getInscripciones().stream()
                    .filter(inscripcion -> !inscripcion.getMateria().isOptativa())
                    .allMatch(inscripcion -> inscripcion.getEstadoMateria().equals("APROBADA"));

            //verificar si el alumno tiene suficientes materias optativas aprobadas
            long materiasOptativasAprobadas = alumno.getInscripciones().stream()
                    .filter(inscripcion -> inscripcion.getMateria().isOptativa() && inscripcion.getEstadoMateria().equals("APROBADA"))
                    .count();

            boolean aproboOptativas = materiasOptativasAprobadas >= optativasParaGraduarse;

            //informar el estado de graduacion del alumno
            if (aproboObligatorias && aproboOptativas) {
                JOptionPane.showMessageDialog(null, "El alumno cumple con los requisitos para graduarse.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no cumple con los requisitos para graduarse.");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al verificar la graduación del alumno: " + e.getMessage());
            return false;
        }
    }

}
