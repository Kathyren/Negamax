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

import java.util.ArrayList;

/**
 *
 * @author Karen
 */
public class ClassTablero 
{
    
    /*
    *Devuelve el ganador, uno sera 1 y el otro -1
    */
    int[][] casillas=new int[7][7];;
    public int ganador;
    public int[][] hcasillas = new int[7][7];


    public ClassTablero(ClassTablero t){
        int a, b;
        if(t == null){
            for (a=0;a < 7; a++) {
                for (b=0;b < 7; b++) {
                    hcasillas[a][b] = 0;
                }
            }
        }
        else
        {
           for (a=0;a < 7; a++) {
                for (b=0;b < 7; b++) {
                    hcasillas[a][b] = t.casillas[a][b];
                }
            } 
        }
    }

    public int [][] muestra(){
        int a, b;
        int [][]texto= new int [7][7];
        for (a=0;a < 7; a++) {
            for (b=0;b < 7; b++) {
                texto[a][b]= hcasillas[a][b];
            }
        }
        return hcasillas;
    }

   // private int [][] hcasillas = new int[7][7];

    public  int [][] casillas_Read(){
        return hcasillas;
    }
    public void casillas_Write(int [][] hc)
    {
        hcasillas = hc;
    }

    public ClassTablero copia(){
        ClassTablero tcopia= null;
        tcopia= new ClassTablero(tcopia);
         int a, b;
        for (a=0;a < 7; a++) {
            for (b=0;b < 7; b++) {
                tcopia.casillas[a][b] = hcasillas[a][b];
            }
        }
        return tcopia;

    }
    public Boolean insertaficha(int columna, int jugador){
        Boolean ok = false;
        int i;
        for (i=0;i < 7; i++) {
            if(this.casillas[i][columna] == 0)
            {
                this.casillas[i][columna] = jugador;
                ok = true;
                break;
            }
        }
        return ok;

    }

    public void deshacer(int columna){

        for (int i = 0; i < 7; i++ ) {
            if (this.casillas[i][columna] != 0) 
            {
                this.casillas[i][columna]  = 0;
                break;
            }
        }
    }

    public Boolean GameOver(){
        Boolean notablas = false;
        int i, j;
        if(this.ganador().get(0) == 0)
        {
            for (i=0;i< 7; i--)
            {
                for (j=0;j < 7;j--) 
                {
                    if (this.casillas[i][j] == 0)
                    {
                        notablas= true;
                        return false;
                    }    
                }
            }
    }
        return true;
    }

    /**
     *
     * @return
     */
    public int[] jugadasPosibles()
    {
        int columna;
        int[] listaJugadas = {-1,-1,-1,-1,-1,-1,-1};
        //ArrayList<Integer> listaJugadas = new ArrayList<Integer> ();

        for (columna = 0; columna < 7; columna++) {
            if (this.casillas[0][columna] == 0) {
                listaJugadas[columna]=columna;
            }
            
        }
        return listaJugadas;
    }

    public ArrayList<Integer> ganador()
            {
                return comprueba_linea_Cuatro(4,0);
            }
    public ArrayList<Integer> comprueba_linea_Cuatro(int n, int jugador)
    {
        
        int i,j,a;
        //int ganador=0;
        int num_lineas=0;
        int lineas_posibles=8-n;
        ArrayList<Integer> cuaterna, cuaterna1, cuaterna2, cuaterna3,cuaterna4;
        ArrayList<Integer> linea, linea1, linea2, linea3, linea4;
        cuaterna=  new ArrayList<>();
        cuaterna1=  new ArrayList<>();
        cuaterna2=  new ArrayList<>();
        cuaterna3=  new ArrayList<>();
        cuaterna4=  new ArrayList<>();
        linea= new ArrayList<>();
        linea1= new ArrayList<>();
        linea2= new ArrayList<>();
        linea3= new ArrayList<>();
        linea4= new ArrayList<>();
        for (i=0; i<=6;i++)
                {
                    for (j=0; j<=lineas_posibles-1;j++)
                    {
                        cuaterna.clear();
                        linea.clear();
                        for (a=0; a<=n-1;a++)
                        {
                            cuaterna.add(this.casillas[i][j+a]);
                            linea.add(i);
                            linea.add(j+a);
                            
                        }
                        if (this.casillas[i][j]!=0 && 
                        comparaCuaternaElemento(cuaterna,this.casillas[i][j],n))
                        {
                            ganador=this.casillas[i][j];
                            return linea;
                        }
                        
                    }
                }
        for (i=0; i<=6;i++)
        {
            for (j=0;j<=lineas_posibles-1;j++)
            {
                cuaterna.clear();
                linea.clear();
                for (a=0; a<n;a++)
                {
                    cuaterna.add(this.casillas[j+a][i]);
                    linea.add(j+a);
                    linea.add(i);
                }
                if(this.casillas[j][i]!=0 && 
                        comparaCuaternaElemento(cuaterna, this.casillas[j][i],n))
                {
                    ganador= this.casillas[j][i];
                    return linea;
                }
                
            }
        }
        for (i=0; i<=3;i++)
        {
            for (j=0;j<=lineas_posibles-1;j++)
            {
                cuaterna1.clear();
                cuaterna2.clear();
                cuaterna3.clear();
                cuaterna4.clear();
                
                linea1.clear();
                linea2.clear();
                linea3.clear();
                linea4.clear();
                
                for (a=0; a<n;a++)
                {
                    cuaterna1.add(this.casillas[j+a][i]);
                    linea1.add(j+a);
                    linea1.add(i);
                    
                    
                    cuaterna2.add(this.casillas[j+a][i]);
                    linea2.add(j+a);
                    linea2.add(i);
                    
                    
                    
                    cuaterna3.add(this.casillas[j+a][i]);
                    linea3.add(j+a);
                    linea3.add(i);
                    
                    
                    cuaterna4.add(this.casillas[j+a][i]);
                    linea4.add(j+a);
                    linea4.add(i);
                }
                if (this.casillas[i+j][j]!=0)
                {
                    if(comparaCuaternaElemento(cuaterna1, cuaterna1.get(0),n))
                    {
                        ganador= cuaterna1.get(0);
                        return linea1;
                    }
                    else
                        if(comparaCuaternaElemento(cuaterna2, cuaterna2.get(0),n))
                        {
                            ganador= cuaterna2.get(0);
                            return linea2;
                        }
                    else
                            if(comparaCuaternaElemento(cuaterna3, cuaterna3.get(0),n))
                            {
                                ganador= cuaterna3.get(0);
                                return linea3;
                            }
                            else
                                if(comparaCuaternaElemento(cuaterna4, cuaterna4.get(0),n))
                            {
                                ganador= cuaterna4.get(0);
                                return linea4;
                            }
                }
            }
        }
        return null;
    }
    public Boolean comparaCuaternaElemento(ArrayList<Integer> cuaterna,int valor, int n)
    {
        int a;
        Boolean ok= true;
        if (n==4)
        {
            for (a=0; a<cuaterna.size(); a++)
            {
                if(cuaterna.get(a)!=valor)
                {
                    ok= false;
                }
            }
        }
        if (n==3)
        {
            
            if(cuaterna.get(0)!=valor && cuaterna.get(1)==valor && cuaterna.get(2)==valor )
            {
                return false;
            }
        }
        if (n==2)
        {
            
            if(cuaterna.get(0)!=valor && cuaterna.get(1)==valor )
            {
                return false;
            }
        }
        
        return ok;
    }
    public int[] comprueba_linea(int n, int jugador)
    {
        
        int i,j,a;
        //int ganador=0;
        int num_lineas=0;
        int lineas_posibles=8-n;
        ArrayList<Integer> cuaterna, cuaterna1, cuaterna2, cuaterna3,cuaterna4;
        
        cuaterna=  new ArrayList<>();
        cuaterna1=  new ArrayList<>();
        cuaterna2=  new ArrayList<>();
        cuaterna3=  new ArrayList<>();
        cuaterna4=  new ArrayList<>();
        
        for (i=0; i<=6;i++)
                {
                    for (j=0; j<=lineas_posibles-1;j++)
                    {
                        
                        cuaterna.clear();
                        for (a=0; a<=n-1;a++)
                        {
                            cuaterna.add(this.casillas[i][j+a]);
                            
                            
                        }
                        if (this.casillas[i][j]!=0 && 
                        comparaCuaternaElemento(cuaterna,this.casillas[i][j],n))
                        {
                            ganador=this.casillas[i][j];
                            if(ganador==jugador)
                                    {
                                        num_lineas++;
                                    }
                        }
                        
                    }
                }
        for (i=0; i<=6;i++)
        {
            for (j=0;j<=lineas_posibles-1;j++)
            {
                cuaterna.clear();
               
                for (a=0; a<n;a++)
                {
                    cuaterna.add(this.casillas[j+a][i]);
                    
                }
                if(this.casillas[j][i]!=0 && 
                        comparaCuaternaElemento(cuaterna, this.casillas[j][i],n))
                {
                    ganador= this.casillas[j][i];
                    if(ganador==jugador)
                                    {
                                        num_lineas++;
                                    }
                }
                
            }
        }
        for (i=0; i<=3;i++)
        {
            for (j=0;j<=lineas_posibles-1;j++)
            {
                cuaterna1.clear();
                cuaterna2.clear();
                cuaterna3.clear();
                cuaterna4.clear();
                

                
                for (a=0; a<n;a++)
                {
                    cuaterna1.add(this.casillas[j+a][i]);

                    cuaterna2.add(this.casillas[j+a][i]);
                    cuaterna3.add(this.casillas[j+a][i]);
                   cuaterna4.add(this.casillas[j+a][i]);
                   
                }
                if (this.casillas[i+j][j]!=0)
                {
                    if(comparaCuaternaElemento(cuaterna1, cuaterna1.get(0),n))
                    {
                        ganador= cuaterna1.get(0);
                        if(ganador==jugador)
                                    {
                                        num_lineas++;
                                    }
                    }
                    else
                        if(comparaCuaternaElemento(cuaterna2, cuaterna2.get(0),n))
                        {
                            ganador= cuaterna2.get(0);
                            if(ganador==jugador)
                                    {
                                        num_lineas++;
                                    }
                        }
                    else
                            if(comparaCuaternaElemento(cuaterna3, cuaterna3.get(0),n))
                            {
                                ganador= cuaterna3.get(0);
                               if(ganador==jugador)
                                    {
                                        num_lineas++;
                                    }
                            }
                            else
                                if(comparaCuaternaElemento(cuaterna4, cuaterna4.get(0),n))
                            {
                                ganador= cuaterna4.get(0);
                                if(ganador==jugador)
                                    {
                                        num_lineas++;
                                    }
                            }
                }
            }
        }
        return new int[]{ganador, num_lineas};
    }
    
}
