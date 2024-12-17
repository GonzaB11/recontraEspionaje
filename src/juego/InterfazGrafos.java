package juego;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;

public class InterfazGrafos extends JFrame {
	private static final long serialVersionUID = 1L;
	private Grafo grafo;
	private JPanel contentPane;
	private HashMap<String, MapMarkerDot> listaVertices;
	private JMapViewer mapViewer;
	private Image image;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazGrafos window = new InterfazGrafos();
					window.setVisible(true);
					window.setLocationRelativeTo(null);
					window.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfazGrafos() {
		this.grafo = new Grafo();
		this.listaVertices = new HashMap<>();
		getContentPane().setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {

		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				image = new ImageIcon(getClass().getResource("/fondoPanel.jpg")).getImage();
				g.drawImage(image, 0, 0, 171, 561, this);
			}
		};
		panel.setBounds(0, 0, 171, 561);
		getContentPane().add(panel);
		panel.setLayout(null);

		JButton btnCrearArista = new JButton("Conectar Espias");
		btnCrearArista.setFocusPainted(false);
		btnCrearArista.setForeground(Color.WHITE);
		btnCrearArista.setBackground(Color.BLACK);
		btnCrearArista.setBounds(10, 126, 141, 48);
		panel.add(btnCrearArista);

		JButton btnPrim = new JButton("Enviar mensaje");
		btnPrim.setFocusPainted(false);
		btnPrim.setBackground(Color.BLACK);
		btnPrim.setForeground(Color.WHITE);
		btnPrim.setBounds(10, 288, 141, 48);
		panel.add(btnPrim);

		JPanel panelMapa = new JPanel();
		panelMapa.setBounds(169, 0, 615, 561);
		getContentPane().add(panelMapa);
		panelMapa.setLayout(null);

		mapViewer = new JMapViewer();
		mapViewer.setBounds(0, 0, 615, 561);
		panelMapa.add(mapViewer);

		Coordinate coordUngs = new Coordinate(-34.52297360066141, -58.70043468818399);
		mapViewer.setDisplayPosition(coordUngs, 10);
		mapViewer.setZoomControlsVisible(false);
		mapViewer.setZoomControlsVisible(false);

		mapViewer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nombre = JOptionPane.showInputDialog("Ingresa nombre del espía: ");
				if (nombre == null || nombre.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Error, no ingreso ningún nombre.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (grafo.contieneEspia(nombre)) {
					JOptionPane.showMessageDialog(null, "Error, el espia ya fue ingresado.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Coordinate coordClic = (Coordinate) mapViewer.getPosition(e.getPoint());
				agregarEspia(nombre, coordClic);

			}
		});
		
		btnCrearArista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = JOptionPane.showInputDialog("Ingresa nombre del espía: ");
				String nombre2 = JOptionPane.showInputDialog("Ingrese el nombre del otro espía: ");
				String peso = JOptionPane.showInputDialog("Ingresa peso: ");
				if (verificarExisteArista(nombre, nombre2, peso) && existeVerticeEnGrafo(nombre, nombre2)
						&& verificarCampoVacio(nombre, nombre2, peso)) {
					dibujarArista(nombre, nombre2, peso);
				}

			}
		});
		
		btnPrim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!grafo.esConexo()) {
					JOptionPane.showMessageDialog(null, "Los espias no pudieron encontrarse, se murieron todos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				ArbolMinimo arbolMinimo = new ArbolMinimo(grafo.arbolGeneradorMinimo(), listaVertices);
				arbolMinimo.setVisible(true);
				arbolMinimo.setLocationRelativeTo(null);
				arbolMinimo.setResizable(false);
				dispose();
			}
		});
	}

	public void agregarEspia(String nombre, Coordinate coordClic) {
		grafo.agregarEspia(nombre);

		MapMarkerDot markerDot = new MapMarkerDot(nombre, coordClic);
		markerDot.getStyle().setFont(new Font("Arial", Font.BOLD, 20));
		markerDot.getStyle().setBackColor(Color.GREEN);
		mapViewer.addMapMarker(markerDot);
		listaVertices.put(nombre, markerDot);
	}

	public void dibujarArista(String nombre, String nombre2, String peso) {
		if (nombre != null && !nombre.isEmpty() && nombre2 != null && !nombre2.isEmpty() && peso != null
				&& !peso.isEmpty()) {
			grafo.agregarArista(nombre, nombre2, Double.valueOf(peso));

			MapMarker coordVert1 = listaVertices.get(nombre);
			MapMarker coordVert2 = listaVertices.get(nombre2);

			if (coordVert1 != null && coordVert2 != null) {
				dibujarArista(coordVert1, coordVert2, peso);
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
		MapMarker mapMarker = new MapMarkerDot(Double.valueOf(peso) + "%", new Coordinate(centroX, centroY));
		mapMarker.getStyle().setFont(new Font("Arial", Font.BOLD, 15));
		mapMarker.getStyle().setBackColor(new Color(0, 0, 0, 0));
		mapMarker.getStyle().setColor(new Color(0, 0, 0, 0));

		mapViewer.addMapMarker(mapMarker);
	}

	public boolean verificarExisteArista(String nombre, String nombre2, String peso) {
		if (grafo.getArista(nombre, nombre2) != null) {
			JOptionPane.showMessageDialog(null, "Error, ya existe esa arista.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean existeVerticeEnGrafo(String nombre, String nombre2) {
		if (!grafo.contieneEspia(nombre) || !grafo.contieneEspia(nombre2)) {
			JOptionPane.showMessageDialog(null, "Error, no existe ese espia.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean verificarCampoVacio(String nombre, String nombre2, String peso) {
		if (peso.isEmpty() || nombre.isEmpty() || nombre2.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error, dejo algún campo vacío.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}