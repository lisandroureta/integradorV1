import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlanE implements PlanEstudio {

    @Override
    public boolean cumpleRequisitos(Alumno alumno) {
        //verificar si el alumno aprobó las correlativas y aprobó todas las materias de 3 cuatrimestres previos
        return alumno.getInscripciones().stream()
                .allMatch(inscripcion -> inscripcion.getEstadoMateria().equals("APROBADA") && cumpleCorrelativas(inscripcion.getMateria(), alumno))
                && cumplioFinalesPrevios(alumno, 3);
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

        //obtener las inscripciones del alumno
        List<Inscripcion> inscripcionesCursadas = alumno.getInscripciones().stream()
                .filter(inscripcion -> inscripcion.getMateria().getCuatrimestre() < cuatrimestreActual)
                .collect(Collectors.toList());

        //logica medio rara para contar la cantidad de cuatrimestres que cursó el alumno
        long cuatrimestresUnicos = inscripcionesCursadas.stream()
                .map(Inscripcion::getMateria)
                .map(Materia::getCuatrimestre)
                .distinct()
                .count();

        return cuatrimestresUnicos >= cuatrimestresPrevios; //verificar si el alumno cumplio con los cuatrimestres previos requeridos
    }

    private boolean cumpleCorrelativas(Materia materia, Alumno alumno) {
        //verificar si el alumno es regular en las correlativas
        return materia.getCorrelativas().stream()
                .allMatch(correlativa -> alumno.getInscripciones().stream()
                        .filter(inscripcion -> inscripcion.getMateria().equals(correlativa))
                        .allMatch(inscripcion -> inscripcion.getEstadoMateria().equals("APROBADO")));
    }
}
