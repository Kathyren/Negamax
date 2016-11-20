public class Imagen extends javax.swing.JPanel {
 
	public Imagen() {
	this.setSize(300, 400); //se selecciona el tamaño del panel
	}
	 
	//Se crea un método cuyo parámetro debe ser un objeto Graphics
	 
	public void paint(Graphics grafico, String ruta) {
	Dimension height = getSize();
	 
	//Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
	 
	//ImageIcon Img = new ImageIcon(getClass().getResource("/Images/Diagrama.png")); 
	ImageIcon Img = new ImageIcon(getClass().getResource(ruta)); 

	//se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
	 
	grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);
	 
	setOpaque(false);
	super.paintComponent(grafico);
	}
}