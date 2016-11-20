package conecta4ia;

import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Karen
 */

/**
 *
 * @author topitzin
 */
public class ClassJugador {
    private int hnumero;
    public int profundidad;
    private int hprofundidad;

    /*(?)*/public int numero; //' jugador primero ("Amarillo", "-1") y jugador segundo("Naranja", "1")

    private int numero_Read(){  return hnumero; }
    private void numero_Write(int value){ hnumero = value; }

    private int profundidad_Read(){  return hprofundidad; }
    private void profundidad_Write(int value){ hprofundidad = value; }

    ClassTablero juega(ClassTablero tablero, boolean ProducidoClick, int col, JLabel TextLabelmensajes, JLabel area) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
