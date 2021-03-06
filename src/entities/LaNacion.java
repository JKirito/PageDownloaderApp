package entities;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Utils.Utils;

public class LaNacion extends DiarioDigital {

	private static final String LINK_LANACION = "http://servicios.lanacion.com.ar/archivo-f";// 04/09/2014-c30"
	private static final String CHARSETNAME_LANACION = "utf-8";
	private static final String NOMBREPREFIJO_AGUARDAR = "LaNacion";
	public static final String NOMBRE_DIARIO = "La Nación";
	public static final String NOMBRE_GRUPO_NOTICIAS = "archivo-notas-272";

	public LaNacion() {
		super.setCharsetName(CHARSETNAME_LANACION);
		super.setLINK(LINK_LANACION);
		super.setNombrePrefijoAGuardar(NOMBREPREFIJO_AGUARDAR);
		super.setNombreGrupoNoticias(NOMBRE_GRUPO_NOTICIAS);
		super.setNombreDiario(NOMBRE_DIARIO);
	}

	@Override
	public String armarLinkActual(String fecha, Seccion seccion) {
		return LINK_LANACION + fecha + seccion.getCodigoSeccion();
	}

	@Override
	public String getNombreArchivo(String fecha) {
		return this.nombrePrefijoAGuardar + "_" + (fecha.contains("/") ? fecha.replace("/", "-") : fecha);
	}

	@Override
	public boolean esValido(Document doc) {
		return doc.getElementById(this.getNombreGrupoNoticias()) != null;
	}

	@Override
	public Element getSoloGrupoNoticias(Document page) {
		return page.getElementById(this.getNombreGrupoNoticias());
	}

	@Override
	// formato es dd/MM/yyyy
	public String getFechaConFormato(Date fechaDate) {
		return Utils.dtoDD_MM_YYYY(fechaDate);
	}

	@Override
	public Elements getElementNotasABuscar(File file) {
		Element notasABuscar = null;
		try {
			// notasABuscar = Jsoup.parse(file,
			// this.getCharsetName()).getElementById(this.getNombreGrupoNoticias());
			notasABuscar = Jsoup.parse(file, this.getCharsetName()).body();
		} catch (IOException e) {
			return null;
		}
		Elements notas = notasABuscar.getElementsByTag("a").select("[href]");
		return notas;
	}

	@Override
	public boolean isPagina12() {
		return false;
	}

	@Override
	public boolean isLaNacion() {
		return true;
	}

	@Override
	public Note getNotaProcesadaFromDocument(Document doc) {
		if (doc.getElementById("encabezado") == null) {
			System.out.println("No tiene encabezado");
			return null;
		}
		Element encabezado = doc.getElementById("encabezado");
		// Elements firma = encabezado.getElementsByAttributeValue("class",
		// "firma");
		encabezado.getElementsByClass("firma").remove();
		// encabezado.getElementsByClass("bajada").remove();
		Elements volanta = encabezado.getElementsByAttributeValue("class", "volanta");
		Elements titulo = encabezado.getAllElements().select("h1");
		Elements descripcion = encabezado.getAllElements().select("p");
		descripcion.removeAll(volanta);
		Element cuerpo = doc.getElementById("cuerpo");

		if (cuerpo.getElementsByClass("foto-encolumnada") != null) {
			cuerpo.getElementsByClass("foto-encolumnada").remove();
		}
		// Elements archRel = cuerpo.getElementsByAttributeValue("class",
		// "archivos-relacionados");
		// Elements fin = cuerpo.getElementsByAttributeValue("class", "fin");

		return new Note(volanta.text(), titulo.text(), descripcion.text(), cuerpo.text(), "", null);
	}

	public Document getNotaPreProcesadaFromDocument(Document doc) {
		if (doc.getElementById("encabezado") == null) {
			System.out.println("No tiene encabezado");
			return null;
		}
		Element encabezado = doc.getElementById("encabezado");
		Element cuerpo = doc.getElementById("cuerpo");

		// Eliminar datos innecesarios
		if (cuerpo.getElementsByClass("archivos-relacionados") != null) {
			cuerpo.getElementsByClass("archivos-relacionados").remove();
		}
		if (cuerpo.getElementsByClass("fin") != null) {
			cuerpo.getElementsByClass("fin").remove();
		}
		if (encabezado.getElementById("archivoPDF") != null) {
			encabezado.getElementById("archivoPDF").remove();
		}

		Document nd = new Document("");
		nd.appendChild(encabezado).appendChild(cuerpo);

		return nd;
	}

	@Override
	public String getlinkNota(String link) {
		return link;
	}
}
