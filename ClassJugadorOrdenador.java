/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jugadores;

import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author topitzin
 */
public class ClassJugadorOrdenador extends ClassJugador{
    public ClassTablero juega(ClassTablero tablero, boolean[] ProducidoClick, int[] col, JLabel TextLabelmensajes, JTextArea area){
        int[] devolucion;
        ClassTablero tablerotmp;
        int jugada;
        String[][] colorJugador = new String[2][2];
        int[] profundidadArbol= {3, 4, 5, 6};
        
        colorJugador[0][0] = "Amarillo";
        colorJugador[0][1] = "-1";
        colorJugador[1][0] = "Naranja";
        colorJugador[1][1] = "1";
        
        tablerotmp = tablero.copia();
        devolucion = ia.negamax(tablerotmp, super.numero, profundidadArbol[super.profundidad - 1], - main.maximoInteger - 1, main.maximoInteger);
        jugada = devolucion[1];
        area.setText("Jugador: "+colorJugador[super.numero]+ "Columna: "+ jugada + 1);
        tablero.insertaficha(jugada, super.numero);
        return tablero;  
    }     
}
