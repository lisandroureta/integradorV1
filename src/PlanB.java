import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanB implements PlanEstudio {

    @Override
    public boolean cumpleRequisitos(Alumno alumno) {
        //verificar si el alumno aprobó las correlativas
        return alumno.getInscripciones().stream()
                .allMatch(inscripcion -> inscripcion.getEstadoMateria().equals("APROBADA") && cumpleCorrelativas(inscripcion.getMateria(), alumno));
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

    private boolean cumpleCorrelativas(Materia materia, Alumno alumno) {
        //implementar la lógica para verificar si el alumno aprobó las correlativas de la materia

        return true;
    }
}
