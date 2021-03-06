package entities;

import java.io.File;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class DiarioDigital {

	protected String LINK;
	protected String charsetName;
	protected String nombrePrefijoAGuardar;
	protected String nombreGrupoNoticias;
	protected String nombreDiario;

	public String getLINK() {
		return LINK;
	}

	public void setLINK(String lINK) {
		LINK = lINK;
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public String getNombrePrefijoAGuardar() {
		return nombrePrefijoAGuardar;
	}

	public void setNombrePrefijoAGuardar(String nombrePrefijoAGuardar) {
		this.nombrePrefijoAGuardar = nombrePrefijoAGuardar;
	}

	public String getNombreGrupoNoticias() {
		return nombreGrupoNoticias;
	}

	public void setNombreGrupoNoticias(String grupoNoticias) {
		this.nombreGrupoNoticias = grupoNoticias;
	}

	public String getNombreDiario() {
		return nombreDiario;
	}

	public void setNombreDiario(String nombreDiario) {
		this.nombreDiario = nombreDiario;
	}

	public abstract String armarLinkActual(String fecha, Seccion seccion);

	public abstract String getNombreArchivo(String fecha);

	public abstract boolean esValido(Document doc);

	public abstract Element getSoloGrupoNoticias(Document page);

	public abstract String getFechaConFormato(Date fechaDate);

	public abstract Elements getElementNotasABuscar(File file);

	public abstract boolean isPagina12();

	public abstract boolean isLaNacion();

	public abstract Note getNotaProcesadaFromDocument(Document doc);

	public abstract Document getNotaPreProcesadaFromDocument(Document doc);

	public abstract String getlinkNota(String attr);
}
