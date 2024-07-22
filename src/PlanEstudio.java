import java.util.List;

public interface PlanEstudio {
    boolean cumpleRequisitos(Alumno alumno);

    List<Materia> obtenerMateriasCursables(Alumno alumno, Carrera carrera);

}
