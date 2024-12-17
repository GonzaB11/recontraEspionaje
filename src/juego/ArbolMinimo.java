package juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

public class ArbolMinimo extends JFrame {
	private static final long serialVersionUID = 1L;
	private Grafo grafo;
	private JPanel contentPane;
	private JMapViewer mapViewer;
	private HashMap<String, MapMarkerDot> listaVertices;

	/**
	 * Create the application.
	 */
	public ArbolMinimo(Grafo grafo, HashMap<String, MapMarkerDot> listaVertices) {
		this.grafo = grafo;
		this.listaVertices = listaVertices;
		getContentPane().setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout()); 
		setContentPane(contentPane);

		mapViewer = new JMapViewer();
		contentPane.add(mapViewer, BorderLayout.CENTER); 

		Coordinate coordUngs = new Coordinate(-34.52297360066141, -58.70043468818399);
		mapViewer.setDisplayPosition(coordUngs, 10);
		mapViewer.setZoomControlsVisible(false);
		
		JButton botonArchivo = new JButton("Obtener información del espía");
		botonArchivo.setBackground(Color.BLACK);
		botonArchivo.setForeground(Color.WHITE);
		botonArchivo.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				String elegirEspia = JOptionPane.showInputDialog("La información de que espía quiere solicitar: ");
				File carpeta = new File("src/Archivos");
				File archivo = new File(carpeta, elegirEspia +  ".txt");
				escribirInformacionAlArchivo(archivo, elegirEspia);
				imprimirInformacionArchivo(archivo, elegirEspia);
			}

		});
		contentPane.add(botonArchivo, BorderLayout.SOUTH);
	}
	
	private void escribirInformacionAlArchivo(File archivo, String espia) {
		try {				
			FileOutputStream fos = new FileOutputStream(archivo);
			OutputStreamWriter out = new OutputStreamWriter(fos);
			for(String a : grafo.espiasVecinos(espia)) {
				out.write("El espía " + espia  + " puede comunicarse con el espia " + a + " y la probabilidad de que intercepten el mensaje es del " + grafo.getAristaPeso(espia, a) + "%" + "\n");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void imprimirInformacionArchivo(File archivo, String espia) {
		try {
			FileInputStream fis = new FileInputStream(archivo);
			Scanner lectorArchivo = new Scanner(fis);
			StringBuilder contenidoCompletoArchivo = new StringBuilder();
			while(lectorArchivo.hasNext()) {
				String linea = lectorArchivo.nextLine();
				contenidoCompletoArchivo.append(linea).append("\n");	
			}
			JOptionPane.showMessageDialog(null, "Información sobre el espia: " + espia + "\n" + contenidoCompletoArchivo.toString());
			lectorArchivo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Map.Entry<String, MapMarkerDot> circulo : listaVertices.entrySet()) {
			String nombreEspias = circulo.getKey();
			MapMarker pos = circulo.getValue();
			dibujarVertice(nombreEspias, pos.getLat(), pos.getLon());
		}
		for (Arista arista : grafo.getAristas()) {
			MapMarker coordVert1 = listaVertices.get(arista.getVert1());
			MapMarker coordVert2 = listaVertices.get(arista.getVert2());
			if (coordVert1 != null && coordVert2 != null) {
				dibujarArista(coordVert1, coordVert2, String.valueOf(arista.getPeso()));
			}
		}
	}

	private void dibujarArista(MapMarker coordVert1, MapMarker coordVert2, String peso) {
		List<Coordinate> vertices = new ArrayList<>();
		vertices.add(coordVert1.getCoordinate());
		vertices.add(coordVert2.getCoordinate());
		vertices.add(coordVert2.getCoordinate());

		MapPolygonImpl mapPolygon = new MapPolygonImpl(vertices);
		mapPolygon.getStyle().setBackColor(Color.blue);
		mapViewer.addMapPolygon(mapPolygon);

		double centroX = (coordVert1.getLat() + coordVert2.getLat()) / 2;
		double centroY = (coordVert1.getLon() + coordVert2.getLon()) / 2;
		MapMarker mapMarker = new MapMarkerDot(peso + "%", new Coordinate(centroX, centroY));
		mapMarker.getStyle().setFont(new Font("Arial", Font.BOLD, 15));
		mapMarker.getStyle().setBackColor(new Color(0, 0, 0, 0));
		mapMarker.getStyle().setColor(new Color(0, 0, 0, 0));

		mapViewer.addMapMarker(mapMarker);
	}

	private void dibujarVertice(String nombre, double x, double y) {
		MapMarkerDot mapMarkerDot = new MapMarkerDot(nombre, new Coordinate(x, y));
		mapMarkerDot.getStyle().setFont(new Font("Arial", Font.BOLD, 18));
		mapMarkerDot.getStyle().setBackColor(Color.GREEN);
		mapViewer.addMapMarker(mapMarkerDot);
		listaVertices.put(nombre, mapMarkerDot);
	}
}