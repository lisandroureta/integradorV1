public class Inscripcion {
    private Alumno alumno;
    private Materia materia;
    private double notaParcial;
    private double notaFinal;
    private String estadoMateria;

    public Inscripcion(Alumno alumno, Materia materia) {
        this.alumno = alumno;
        this.materia = materia;
        this.notaParcial = -1.0; //valores por defecto, indicando que no se han registrado todavia
        this.notaFinal = -1.0;
        this.estadoMateria = "REGULAR";
    }


    public Materia getMateria() {
        return materia;
    }
    public double getNotaParcial(){
        return notaParcial;
    }
    public double getNotaFinal(){
        return notaFinal;
    }

   public boolean aprobada(){
        return notaFinal >= 4;
   }


    public void registrarNotaParcial(double notaParcial) {
        this.notaParcial = notaParcial;
    }

    public void registrarNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getEstadoMateria() {
        return estadoMateria;
    }

    public void setEstadoMateria(String estadoMateria) {
        this.estadoMateria = estadoMateria;
    }

}
