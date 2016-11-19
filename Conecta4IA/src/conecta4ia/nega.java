package conecta4ia;


import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Karen
 * 
 * 
 * aunque esta parte en realidad la hizo eloy
 */
public  class nega
{
public int[] negamax(ClassTablero tablero, int jugador, int profundidad, int alfa, int beta) //as varianr[]
{
	int alfa_local, max_puntuacion, jugada, puntuacion;
	ClassTablero tableroaux;
	int jugada_max=0;
	int[] JugadasPosibles;
	int contador, opcion;
	int valorJugadorContrario=0, valorposicionContrario=0;


	//max_puntuacion= -main.maximoInteger -1;///PREGUNTAR A ELOY
    max_puntuacion=-(2^30)-1;
	alfa_local=alfa;
	//Arreglo de las jugadas que se pueden hacer segun el tablero
	JugadasPosibles=tablero.jugadasPosibles();

	//Repite por cada jugada posible
	tableroaux = new ClassTablero(tablero);
	for(contador=0;contador<JugadasPosibles.length; contador++)
	{
		tableroaux.insertaficha(JugadasPosibles[contador],jugador);
		//tableroaux.muestra
		if(tableroaux.GameOver() || profundidad==0)
		{
			return new int[]{EvaluaJugadaMov(tableroaux,jugador),JugadasPosibles[contador]};
		}
		else
		{
			puntuacion= -negamax(tableroaux,jugador*(-1),profundidad -1,-beta,-alfa_local)[0];
			tableroaux.deshacer(JugadasPosibles[contador]);
			if(puntuacion> max_puntuacion)
			{
				max_puntuacion=puntuacion;
				jugada_max= JugadasPosibles[contador];
			}
			else if (puntuacion==max_puntuacion) {
				//De un modo aleatorio se eligee una de las dos para que las partidas no sean iguales
				Random rnd = new Random();
                                opcion=(int)(rnd.nextDouble() * 2 + 0);
				if (opcion==0) {
					max_puntuacion=puntuacion;
					jugada_max=JugadasPosibles[contador];
				}
				if(max_puntuacion>alfa_local)
				{
					alfa_local=puntuacion;
				}
				if (alfa_local>=beta) {
					return new int[]{alfa_local,jugada_max};
				}
			}
		}
	}
	return new int[]{max_puntuacion,jugada_max};

}

public int EvaluaJugadaMov(ClassTablero tablero, int jugador)
{
    ////PREGUNTAR A ELOY
	int valor_jugada, valor, n2=0, n3=0, n4, j2, j3, j4;
	int[] eva;

	eva= tablero.comprueba_linea(4,jugador);
	n4=eva[1];
	j4=eva[0];

	if (n4==0) {
            //mejora de rendimiento (version 0.0.4)
            eva=tablero.comprueba_linea(2,jugador);
            n2=eva[1];
            j2=eva[0];

            eva = tablero.comprueba_linea(3, jugador);
            n3 = eva[1];
            j3 = eva[0];
	}	
	valor_jugada=(4*n2*9*n3+100000*n4);
	return valor_jugada;
}
}
