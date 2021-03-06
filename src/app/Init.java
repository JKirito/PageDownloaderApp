package app;

import vista.VistaDescargas;
import vista.VistaResultadoDescarga;
import controlador.ControladorDescargas;

public class Init {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
				try {
					VistaDescargas vistaDescarga = new VistaDescargas();
					VistaResultadoDescarga resultadoDescarga = new VistaResultadoDescarga();
					new ControladorDescargas(vistaDescarga, resultadoDescarga);
					vistaDescarga.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

}
