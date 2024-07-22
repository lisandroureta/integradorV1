import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //asegurar que la creaciOn de la interfaz grAfica se realice en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InterfazGrafica interfaz = new InterfazGrafica();
                interfaz.setVisible(true);
            }
        });
    }
}