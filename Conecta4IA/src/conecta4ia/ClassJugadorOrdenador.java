package conecta4ia;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Karen
 */
import javax.swing.JLabel;


/**
 *
 * @author topitzin
 */
public class ClassJugadorOrdenador extends ClassJugador{
    @Override
    public ClassTablero juega(ClassTablero tablero, boolean ProducidoClick, int col, JLabel TextLabelmensajes, JLabel area){
        int[] devolucion;
        ClassTablero tablerotmp;
        int jugada;
        String[][] colorJugador = new String[2][2];
        int[] profundidadArbol= {3, 4, 5, 6};
        ///PREGUNTAR A TOPI
        nega ia = new nega();
        
        colorJugador[0][0] = "Amarillo";
        colorJugador[0][1] = "-1";
        colorJugador[1][0] = "Naranja";
        colorJugador[1][1] = "1";
        
        tablerotmp = tablero.copia();
        //PROBLEMAS CON maximoIntger, que demonios es eso?! aparece con Eloy y con Topi
        //devolucion = ia.negamax(tablerotmp, super.numero, profundidadArbol[super.profundidad - 1], - main.maximoInteger - 1, main.maximoInteger);
        devolucion = ia.negamax(tablerotmp, super.numero, profundidadArbol[super.profundidad - 1], (-2^30+1), 2^30);
        jugada = devolucion[1];
        area.setText("Jugador: "+colorJugador[super.numero]+ "Columna: "+ jugada + 1);
        tablero.insertaficha(jugada, super.numero);
        return tablero;  
    }     
}
