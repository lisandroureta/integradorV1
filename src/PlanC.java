import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanC implements PlanEstudio {

    @Override
    public boolean cumpleRequisitos(Alumno alumno) {
        //verificar si el alumno es regular en las correlativas y aprobó todas las materias de 5 cuatrimestres previos
        return alumno.getInscripciones().stream()
                .allMatch(inscripcion -> inscripcion.getEstadoMateria().equals("REGULAR") && cumpleCorrelativas(inscripcion.getMateria(), alumno))
                && cumplioFinalesPrevios(alumno, 5);
    }

    @Override
    public List<Materia> obtenerMateriasCursables(Alumno alumno, Carrera carrera) {
        List<Materia> materiasCursables = new ArrayList<>();

        for (Materia materia : carrera.getMaterias()) {
            if (alumno.puedeCursarMateria(materia)) {
                materiasCursables.add(materia);
            }
        }

        return materiasCursables;
    }

    private boolean cumplioFinalesPrevios(Alumno alumno, int cuatrimestresPrevios) {
        //verificar si el alumno aprobó los finales de todas las materias de los cuatrimestres previos indicados
        return alumno.getInscripciones().stream()
                .filter(inscripcion -> cumplioCuatrimestresPrevios(inscripcion.getMateria(), alumno, cuatrimestresPrevios))
                .allMatch(inscripcion -> inscripcion.getEstadoMateria().equals("APROBADA"));
    }

    private boolean cumplioCuatrimestresPrevios(Materia materia, Alumno alumno, int cuatrimestresPrevios) {
        int cuatrimestreActual = materia.getCuatrimestre(); //obtener el cuatrimestre actual

        //verificar si el alumno curso las materias de los cuatrimestres previos indicados
        for (Inscripcion inscripcion : alumno.getInscripciones()) {
            if (inscripcion.getMateria().getCuatrimestre() > cuatrimestreActual - cuatrimestresPrevios) {
                return false;
            }
        }

        return true;
    }

    private boolean cumpleCorrelativas(Materia materia, Alumno alumno) {
        //verificar si el alumno es regular en las correlativas
        return materia.getCorrelativas().stream()
                .allMatch(correlativa -> alumno.getInscripciones().stream()
                        .filter(inscripcion -> inscripcion.getMateria().equals(correlativa))
                        .allMatch(inscripcion -> inscripcion.getEstadoMateria().equals("REGULAR")));
    }
}
