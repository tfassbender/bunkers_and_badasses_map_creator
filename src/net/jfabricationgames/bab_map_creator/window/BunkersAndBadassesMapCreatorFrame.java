package net.jfabricationgames.bab_map_creator.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardLoader;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_board.Region;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class BunkersAndBadassesMapCreatorFrame extends JFrame {
	
	private static final long serialVersionUID = 6355781685766591536L;
	
	private JPanel contentPane;
	private JTextField txtField;
	private JTextField txtClickedX;
	
	private boolean overview = false;
	
	private ImagePanel panel_board_image;
	private JScrollPane scrollPane;
	
	private JTextField txtTroupsNormal;
	private JTextField txtTroupsBadass;
	private JTextField txtBuildingPosition;
	private JTextField txtFieldPosition;
	private JTextField txtNewFieldName;
	private JTextField txtColor;
	private JTextField txtCurrentColor;
	private JTextField txtFieldColor;
	private JTextField txtClickedY;
	private JTextField txtCurrentBoard;
	private JTextField txtCurrentField;
	
	private Robot robot;
	private JFileChooser fileChooser;
	private BoardLoader boardLoader = new BoardLoader();
	private DefaultComboBoxModel<Region> model;
	
	private Board currentBoard;
	private Field currentField;
	private Point clickedPosition;
	private Color clickedColor;
	private Color mouseColor;
	
	private DefaultListModel<Field> listModel = new DefaultListModel<Field>();
	private JList<Field> list;
	private JComboBox<Region> comboBox;
	private JTextField txtCommandPosition;
	private JTextField txtPosition;
	private JTextField txtMarkerposition;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BunkersAndBadassesMapCreatorFrame frame = new BunkersAndBadassesMapCreatorFrame();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public BunkersAndBadassesMapCreatorFrame() {
		setTitle("Bunkers And Badasses - Map Creator");
		setIconImage(Toolkit.getDefaultToolkit().getImage(BunkersAndBadassesMapCreatorFrame.class.getResource("/com/jfabricationgames/toolbox/images/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 900);
		setMinimumSize(new Dimension(1300, 800));
		setLocationRelativeTo(null);
		
		try {
			robot = new Robot();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	       //e.printStackTrace();
	    }
		
		fileChooser = new JFileChooser(new File("."));
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[800px,grow][400px,grow]", "[grow]"));
		
		JPanel panel_board = new JPanel();
		panel_board.setBackground(Color.GRAY);
		panel.add(panel_board, "cell 0 0,grow");
		panel_board.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_board.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		
		panel_board_image = new ImagePanel();
		panel_board_image.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int[] pos;
				Point point = MouseInfo.getPointerInfo().getLocation();
				Color color = robot.getPixelColor((int) point.getX(), (int) point.getY());
				mouseColor = color;
				txtCurrentColor.setText("(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")");
				if (panel_board_image.getImage() != null) {
					pos = calculatePosition(e.getPoint());
					txtPosition.setText("(" + Integer.toString(pos[0]) + "|" + Integer.toString(pos[1]) + ")");
				}
			}
		});
		panel_board_image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] pos = calculatePosition(e.getPoint());
				clickedColor = mouseColor;
				clickedPosition = e.getPoint();
				txtClickedX.setText(Integer.toString(pos[0]));
				txtClickedY.setText(Integer.toString(pos[1]));
				txtColor.setText("(" + clickedColor.getRed() + ", " + clickedColor.getGreen() + ", " + clickedColor.getBlue() + ")");
			}
		});
		panel_board_image.setBackground(Color.GRAY);
		scrollPane.setViewportView(panel_board_image);
		
		JPanel panel_settings = new JPanel();
		panel_settings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_settings.setBackground(Color.GRAY);
		panel.add(panel_settings, "cell 1 0,grow");
		panel_settings.setLayout(new MigLayout("", "[grow][grow][]", "[][10px][][200px][][grow][grow][grow][100px:n,grow][grow][grow]"));
		
		JLabel lblBoardSettings = new JLabel("Board Settings");
		lblBoardSettings.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_settings.add(lblBoardSettings, "cell 0 0 3 1,alignx center");
		
		JLabel lblFields = new JLabel("Fields:");
		lblFields.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_settings.add(lblFields, "cell 0 2 3 1");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_settings.add(scrollPane_1, "cell 0 3 3 1,grow");
		
		list = new JList<Field>(listModel);
		list.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Field selectedField = list.getSelectedValue();
				if (selectedField != null) {
					Point fieldPos = selectedField.getFieldPosition();
					Point normalTroupPos = selectedField.getNormalTroopsPosition();
					Point badassTroupPos = selectedField.getBadassTroopsPosition();
					Point buildingPos = selectedField.getBuildingPosition();
					Point commandPos = selectedField.getCommandMarkerPosition();
					Point markerPos = selectedField.getPlayerMarkerPosition();
					Color fieldColor = selectedField.getFieldColor();
					txtField.setText(selectedField.getName());
					if (fieldPos != null) {
						txtFieldPosition.setText("(" + fieldPos.getX() + " | " + fieldPos.getY() + ")");
					}
					else {
						txtFieldPosition.setText("");
					}
					if (normalTroupPos != null) {
						txtTroupsNormal.setText("(" + normalTroupPos.getX() + " | " + normalTroupPos.getY() + ")");
					}
					else {
						txtTroupsNormal.setText("");
					}
					if (badassTroupPos != null) {
						txtTroupsBadass.setText("(" + badassTroupPos.getX() + " | " + badassTroupPos.getY() + ")");
					}
					else {
						txtTroupsBadass.setText("");
					}
					if (buildingPos != null) {
						txtBuildingPosition.setText("(" + buildingPos.getX() + " | " + buildingPos.getY() + ")");
					}
					else {
						txtBuildingPosition.setText("");
					}
					if (fieldColor != null) {
						txtFieldColor.setText("(" + fieldColor.getRed() + ", " + fieldColor.getGreen() + ", " + fieldColor.getBlue() + ")");
					}
					else {
						txtFieldColor.setText("");
					}
					if (commandPos != null) {
						txtCommandPosition.setText("(" + commandPos.getX() + " | " + commandPos.getY() + ")");
					}
					else {
						txtCommandPosition.setText("");
					}
					if (markerPos != null) {
						txtMarkerposition.setText("(" + markerPos.getX() + " | " + markerPos.getY() + ")");
					}
					else {
						txtMarkerposition.setText("");
					}
					comboBox.setSelectedItem(selectedField.getRegion());
					setField(selectedField);
				}
			}
		});
		list.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setViewportView(list);
		
		JButton btnOverview = new JButton("Overview");
		btnOverview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				overview = !overview;
				BufferedImage img = panel_board_image.getImage();
				if (overview) {
					panel_board_image.setPreferredSize(new Dimension(scrollPane.getWidth()-25, scrollPane.getHeight()-25));
				}
				else {
					panel_board_image.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
				}
				panel_board_image.setAdaptSizeKeepProportion(overview);
				panel_board_image.revalidate();
				panel_board_image.repaint();
			}
		});
		btnOverview.setBackground(Color.GRAY);
		panel_settings.add(btnOverview, "cell 0 4");
		
		JButton btnLoadBoardImage = new JButton("Load Board Image");
		btnLoadBoardImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoadBoardDialog(BunkersAndBadassesMapCreatorFrame.this).setVisible(true);
			}
		});
		btnLoadBoardImage.setBackground(Color.GRAY);
		panel_settings.add(btnLoadBoardImage, "cell 1 4,alignx center");
		
		JButton btnAddConnections = new JButton("Add Connections");
		btnAddConnections.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FieldConnectorFrame(BunkersAndBadassesMapCreatorFrame.this, listModel.elements()).setVisible(true);
			}
		});
		btnAddConnections.setBackground(Color.GRAY);
		panel_settings.add(btnAddConnections, "cell 2 4,alignx right");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel_settings.add(panel_4, "cell 0 5 3 1,grow");
		panel_4.setLayout(new MigLayout("", "[][grow][][grow]", "[]"));
		
		JLabel lblCurrentBoard = new JLabel("Current Board:");
		panel_4.add(lblCurrentBoard, "cell 0 0,alignx trailing");
		
		txtCurrentBoard = new JTextField();
		txtCurrentBoard.setText("null");
		txtCurrentBoard.setBackground(Color.LIGHT_GRAY);
		txtCurrentBoard.setEditable(false);
		panel_4.add(txtCurrentBoard, "cell 1 0,growx");
		txtCurrentBoard.setColumns(10);
		
		JLabel lblCurrentField = new JLabel("Current Field:");
		panel_4.add(lblCurrentField, "cell 2 0,alignx trailing");
		
		txtCurrentField = new JTextField();
		txtCurrentField.setText("null");
		txtCurrentField.setBackground(Color.LIGHT_GRAY);
		txtCurrentField.setEditable(false);
		panel_4.add(txtCurrentField, "cell 3 0,growx");
		txtCurrentField.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.GRAY);
		panel_settings.add(panel_6, "cell 0 6 3 1,grow");
		panel_6.setLayout(new MigLayout("", "[grow][][50px][10px][][50px][grow]", "[]"));
		
		JLabel lblPlayersmin = new JLabel("Players (min):");
		panel_6.add(lblPlayersmin, "cell 1 0");
		
		JSpinner spinner_players_min = new JSpinner();
		spinner_players_min.setModel(new SpinnerNumberModel(2, 2, 8, 1));
		spinner_players_min.setBackground(Color.LIGHT_GRAY);
		panel_6.add(spinner_players_min, "cell 2 0,growx");
		
		JLabel lblPlayersmax = new JLabel("Players (max):");
		panel_6.add(lblPlayersmax, "cell 4 0");
		
		JSpinner spinner_players_max = new JSpinner();
		spinner_players_max.setModel(new SpinnerNumberModel(2, 2, 8, 1));
		spinner_players_max.setBackground(Color.LIGHT_GRAY);
		panel_6.add(spinner_players_max, "cell 5 0,growx");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel_settings.add(panel_2, "cell 0 7 3 1,growx,aligny center");
		panel_2.setLayout(new MigLayout("", "[][grow][fill]", "[][5px][][]"));
		
		JLabel lblNewFieldName = new JLabel("New Field Name:");
		lblNewFieldName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2.add(lblNewFieldName, "cell 0 0,alignx trailing");
		
		txtNewFieldName = new JTextField();
		txtNewFieldName.setBackground(Color.LIGHT_GRAY);
		panel_2.add(txtNewFieldName, "cell 1 0,growx");
		txtNewFieldName.setColumns(10);
		
		JButton btnNewField = new JButton("Create New Field");
		btnNewField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Field field = new Field();
				field.setName(txtNewFieldName.getText());
				setField(field);
				txtField.setText(txtNewFieldName.getText());
				txtNewFieldName.setText("");
				txtFieldPosition.setText("");
				txtTroupsNormal.setText("");
				txtTroupsBadass.setText("");
				txtBuildingPosition.setText("");
				txtCommandPosition.setText("");
				txtMarkerposition.setText("");
				txtFieldColor.setText("");
			}
		});
		panel_2.add(btnNewField, "cell 2 0");
		btnNewField.setBackground(Color.GRAY);
		
		JLabel lblCurrentColor = new JLabel("Current Color:");
		lblCurrentColor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2.add(lblCurrentColor, "cell 0 2,alignx trailing");
		
		txtCurrentColor = new JTextField();
		txtCurrentColor.setBackground(Color.LIGHT_GRAY);
		txtCurrentColor.setEditable(false);
		panel_2.add(txtCurrentColor, "cell 1 2,growx");
		txtCurrentColor.setColumns(10);
		
		JButton btnDeleteSelectedField = new JButton("Delete Selected Field");
		btnDeleteSelectedField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listModel.remove(list.getSelectedIndex());
			}
		});
		btnDeleteSelectedField.setBackground(Color.GRAY);
		panel_2.add(btnDeleteSelectedField, "cell 2 2");
		
		JLabel lblCurrentPosition = new JLabel("Current Position:");
		lblCurrentPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2.add(lblCurrentPosition, "cell 0 3,alignx trailing");
		
		txtPosition = new JTextField();
		txtPosition.setEditable(false);
		txtPosition.setBackground(Color.LIGHT_GRAY);
		panel_2.add(txtPosition, "cell 1 3,growx");
		txtPosition.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_settings.add(panel_3, "cell 0 8 3 1,growx,aligny center");
		panel_3.setLayout(new MigLayout("", "[][50px][10px][50px][][grow]", "[][][][]"));
		
		JLabel lblFieldName = new JLabel("Field Name:");
		panel_3.add(lblFieldName, "cell 0 0");
		lblFieldName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtField = new JTextField();
		panel_3.add(txtField, "cell 1 0 5 1,growx");
		txtField.setEditable(false);
		txtField.setBackground(Color.LIGHT_GRAY);
		txtField.setColumns(10);
		
		JLabel lblFieldRegion = new JLabel("Field Region:");
		lblFieldRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_3.add(lblFieldRegion, "cell 0 1");
		
		model = new DefaultComboBoxModel<Region>();
		comboBox = new JComboBox<Region>(model);
		comboBox.setBackground(Color.GRAY);
		panel_3.add(comboBox, "cell 1 1 4 1,growx");
		
		JButton btnManageRegions = new JButton("Manage Regions");
		btnManageRegions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegionManagerDialog(BunkersAndBadassesMapCreatorFrame.this).setVisible(true);
			}
		});
		btnManageRegions.setBackground(Color.GRAY);
		panel_3.add(btnManageRegions, "cell 5 1");
		
		JLabel lblPosition = new JLabel("Clicked Position:");
		panel_3.add(lblPosition, "cell 0 2");
		lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtClickedX = new JTextField();
		panel_3.add(txtClickedX, "cell 1 2");
		txtClickedX.setBackground(Color.LIGHT_GRAY);
		txtClickedX.setColumns(10);
		
		txtClickedY = new JTextField();
		txtClickedY.setBackground(Color.LIGHT_GRAY);
		panel_3.add(txtClickedY, "cell 3 2,growx");
		txtClickedY.setColumns(10);
		
		JButton btnUpdatePosition = new JButton("Update Position");
		btnUpdatePosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = -1;
				int y = -1;
				try {
					x = Integer.parseInt(txtClickedX.getText());
					y = Integer.parseInt(txtClickedY.getText());
				}
				catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}
				clickedPosition = new Point(x, y);
			}
		});
		btnUpdatePosition.setBackground(Color.GRAY);
		panel_3.add(btnUpdatePosition, "cell 5 2");
		
		JLabel lblClickedColor = new JLabel("Clicked Color:");
		panel_3.add(lblClickedColor, "cell 0 3");
		lblClickedColor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtColor = new JTextField();
		panel_3.add(txtColor, "cell 1 3 5 1,growx");
		txtColor.setEditable(false);
		txtColor.setBackground(Color.LIGHT_GRAY);
		txtColor.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_settings.add(panel_1, "cell 0 9 3 1,growx,aligny center");
		panel_1.setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][10px][]"));
		
		JLabel lblFieldPosition = new JLabel("Field Position:");
		lblFieldPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblFieldPosition, "cell 0 0");
		
		txtFieldPosition = new JTextField();
		txtFieldPosition.setEditable(false);
		txtFieldPosition.setBackground(Color.LIGHT_GRAY);
		panel_1.add(txtFieldPosition, "cell 1 0,growx");
		txtFieldPosition.setColumns(10);
		
		JButton btnSetCurrent_3 = new JButton("Set Current");
		btnSetCurrent_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentField.setFieldPosition(clickedPosition);
				txtFieldPosition.setText("(" + clickedPosition.getX() + " | " + clickedPosition.getY() + ")");
			}
		});
		btnSetCurrent_3.setBackground(Color.GRAY);
		panel_1.add(btnSetCurrent_3, "cell 2 0");
		
		JLabel lblTroupPositionNormal = new JLabel("Troup Position Normal:");
		lblTroupPositionNormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblTroupPositionNormal, "cell 0 1");
		
		txtTroupsNormal = new JTextField();
		txtTroupsNormal.setBackground(Color.LIGHT_GRAY);
		txtTroupsNormal.setEditable(false);
		panel_1.add(txtTroupsNormal, "cell 1 1,growx");
		txtTroupsNormal.setColumns(10);
		
		JButton btnSetCurrent = new JButton("Set Current");
		btnSetCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentField.setNormalTroopsPosition(clickedPosition);
				txtTroupsNormal.setText("(" + clickedPosition.getX() + " | " + clickedPosition.getY() + ")");
			}
		});
		btnSetCurrent.setBackground(Color.GRAY);
		btnSetCurrent.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnSetCurrent, "cell 2 1");
		
		JLabel lblTroupPositionBadass = new JLabel("Troup Position Badass:");
		lblTroupPositionBadass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblTroupPositionBadass, "cell 0 2");
		
		txtTroupsBadass = new JTextField();
		txtTroupsBadass.setBackground(Color.LIGHT_GRAY);
		txtTroupsBadass.setEditable(false);
		panel_1.add(txtTroupsBadass, "cell 1 2,growx");
		txtTroupsBadass.setColumns(10);
		
		JButton btnSetCurrent_1 = new JButton("Set Current");
		btnSetCurrent_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentField.setBadassTroopsPosition(clickedPosition);
				txtTroupsBadass.setText("(" + clickedPosition.getX() + " | " + clickedPosition.getY() + ")");
			}
		});
		btnSetCurrent_1.setBackground(Color.GRAY);
		btnSetCurrent_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnSetCurrent_1, "cell 2 2");
		
		JLabel lblBuildingPosition = new JLabel("Building Position:");
		lblBuildingPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblBuildingPosition, "cell 0 3");
		
		txtBuildingPosition = new JTextField();
		txtBuildingPosition.setBackground(Color.LIGHT_GRAY);
		txtBuildingPosition.setEditable(false);
		txtBuildingPosition.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(txtBuildingPosition, "cell 1 3,growx");
		txtBuildingPosition.setColumns(10);
		
		JButton btnSetCurrent_2 = new JButton("Set Current");
		btnSetCurrent_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentField.setBuildingPosition(clickedPosition);
				txtBuildingPosition.setText("(" + clickedPosition.getX() + " | " + clickedPosition.getY() + ")");
			}
		});
		btnSetCurrent_2.setBackground(Color.GRAY);
		btnSetCurrent_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnSetCurrent_2, "cell 2 3");
		
		JLabel lblCommandPosition = new JLabel("Command Position:");
		lblCommandPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblCommandPosition, "cell 0 4");
		
		txtCommandPosition = new JTextField();
		txtCommandPosition.setEditable(false);
		txtCommandPosition.setBackground(Color.LIGHT_GRAY);
		txtCommandPosition.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(txtCommandPosition, "cell 1 4,growx");
		txtCommandPosition.setColumns(10);
		
		JButton btnSetCurrent_5 = new JButton("Set Current");
		btnSetCurrent_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentField.setCommandMarkerPosition(clickedPosition);
				txtCommandPosition.setText("(" + clickedPosition.getX() + " | " + clickedPosition.getY() + ")");
			}
		});
		btnSetCurrent_5.setBackground(Color.GRAY);
		panel_1.add(btnSetCurrent_5, "cell 2 4");
		
		JLabel lblPlayerMarkerPosition = new JLabel("Player Marker Position:");
		lblPlayerMarkerPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblPlayerMarkerPosition, "cell 0 5,alignx trailing");
		
		txtMarkerposition = new JTextField();
		txtMarkerposition.setBackground(Color.LIGHT_GRAY);
		txtMarkerposition.setEditable(false);
		panel_1.add(txtMarkerposition, "cell 1 5,growx");
		txtMarkerposition.setColumns(10);
		
		JButton btnSetCurrent_6 = new JButton("Set Current");
		btnSetCurrent_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentField.setPlayerMarkerPosition(clickedPosition);
				txtMarkerposition.setText("(" + clickedPosition.getX() + " | " + clickedPosition.getY() + ")");
			}
		});
		btnSetCurrent_6.setBackground(Color.GRAY);
		panel_1.add(btnSetCurrent_6, "cell 2 5");
		
		JLabel lblFieldColor = new JLabel("Field Color:");
		lblFieldColor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblFieldColor, "cell 0 6");
		
		txtFieldColor = new JTextField();
		txtFieldColor.setEditable(false);
		txtFieldColor.setBackground(Color.LIGHT_GRAY);
		panel_1.add(txtFieldColor, "cell 1 6,growx");
		txtFieldColor.setColumns(10);
		
		JButton btnSetCurrent_4 = new JButton("Set Current");
		btnSetCurrent_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentField.setFieldColor(clickedColor);
				txtFieldColor.setText("(" + clickedColor.getRed() + ", " + clickedColor.getGreen() + ", " + clickedColor.getBlue() + ")");
			}
		});
		btnSetCurrent_4.setBackground(Color.GRAY);
		panel_1.add(btnSetCurrent_4, "cell 2 6");
		
		JButton btnAddField = new JButton("Add Field");
		btnAddField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedValue() == null || !list.getSelectedValue().getName().equals(currentField.getName())) {
					currentField.setRegion((Region) comboBox.getSelectedItem());
					listModel.addElement(currentField);	
				}
				else if (list.getSelectedValue().getName().equals(currentField.getName())) {
					//update the values
					Field field = list.getSelectedValue();
					field.setRegion((Region) comboBox.getSelectedItem());
				}
			}
		});
		panel_1.add(btnAddField, "cell 0 8 3 1,alignx center");
		btnAddField.setBackground(Color.GRAY);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		panel_settings.add(panel_5, "cell 0 10 3 1,grow");
		panel_5.setLayout(new MigLayout("", "[grow][][][grow]", "[]"));
		
		JButton btnLoadBoard = new JButton("Load Board");
		btnLoadBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(BunkersAndBadassesMapCreatorFrame.this) == JFileChooser.APPROVE_OPTION) {
					currentBoard = boardLoader.loadBoard(fileChooser.getSelectedFile());
					for (Field field : currentBoard.getFields()) {
						listModel.addElement(field);
					}
					for (Region region : currentBoard.getRegions()) {
						model.addElement(region);
					}
					int playersMin = currentBoard.getPlayersMin();
					int playersMax = currentBoard.getPlayersMax();
					spinner_players_min.setValue(playersMin < 2 ? 2 : playersMin > 8 ? 8 : playersMin);
					spinner_players_max.setValue(playersMax < 2 ? 2 : playersMax > 8 ? 8 : playersMax);
					txtCurrentBoard.setText(currentBoard.getName());
					panel_board_image.setImage(currentBoard.getBaseImage());
				}
			}
		});
		btnLoadBoard.setBackground(Color.GRAY);
		panel_5.add(btnLoadBoard, "cell 1 0");
		
		JButton btnStoreBoard = new JButton("Store Board");
		panel_5.add(btnStoreBoard, "cell 2 0");
		btnStoreBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentBoard.getFields() == null) {
					new FieldConnectorFrame(BunkersAndBadassesMapCreatorFrame.this, listModel.elements()).setVisible(true);
					return;
				}
				try {
					checkForDuplications();
					if (fileChooser.showSaveDialog(BunkersAndBadassesMapCreatorFrame.this) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						for (Region region : currentBoard.getRegions()) {//add the fields to the regions
							region.setFields(new ArrayList<Field>());
						}
						for (Field field : currentBoard.getFields()) {
							field.getRegion().addField(field);
						}
						currentBoard.setPlayersMin((Integer) spinner_players_min.getValue());
						currentBoard.setPlayersMax((Integer) spinner_players_max.getValue());
						currentBoard.setStoreImage(true);
						boardLoader.storeBoard(currentBoard, file);
					}
				}
				catch (Exception ex) {
					//ex.printStackTrace();
				}
			}
		});
		btnStoreBoard.setBackground(Color.GRAY);
	}
	
	public void checkForDuplications() throws Exception {
		List<Point> allPoints = new ArrayList<Point>(currentBoard.getFields().size()*4);
		List<Color> allColors = new ArrayList<Color>(currentBoard.getFields().size());
		List<Field> duplicatePoints = new ArrayList<Field>();
		List<Field> duplicateColors = new ArrayList<Field>();
		for (Field field : currentBoard.getFields()) {
			if (allPoints.contains(field.getFieldPosition())) {
				duplicatePoints.add(field);
			}
			allPoints.add(field.getFieldPosition());
			if (allPoints.contains(field.getNormalTroopsPosition())) {
				duplicatePoints.add(field);
			}
			allPoints.add(field.getNormalTroopsPosition());
			if (allPoints.contains(field.getBadassTroopsPosition())) {
				duplicatePoints.add(field);
			}
			allPoints.add(field.getBadassTroopsPosition());
			if (allPoints.contains(field.getBuildingPosition())) {
				duplicatePoints.add(field);
			}
			allPoints.add(field.getBuildingPosition());
		}
		for (Field field : currentBoard.getFields()) {
			if (allColors.contains(field.getFieldColor())) {
				duplicateColors.add(field);
			}
			allColors.add(field.getFieldColor());
		}
		if (!duplicatePoints.isEmpty() || !duplicateColors.isEmpty()) {
			new ColorPositionDuplicationDialog(this, duplicateColors, duplicatePoints).setVisible(true);
			throw new Exception();//doesn't need to be specified
		}
	}
	
	public Board getBoard() {
		return currentBoard;
	}
	public void setBoard(Board board) {
		currentBoard = board;
		currentField = null;
		//listModel.removeAllElements();
		if (board != null) {
			txtCurrentBoard.setText(board.getName());
			panel_board_image.setImage(board.getBaseImage());
			scrollPane.setPreferredSize(new Dimension(board.getBaseImage().getWidth(), board.getBaseImage().getHeight()));
			
			/*if (currentBoard.getFields() != null) {
				for (Field field : currentBoard.getFields()) {
					listModel.addElement(field);
				}
			}*/
			
			panel_board_image.revalidate();
			panel_board_image.repaint();
		}
		else {
			txtCurrentBoard.setText("null");
		}
	}
	private void setField(Field field) {
		currentField = field;
		if (field != null) {
			txtCurrentField.setText(field.getName());
		}
		else {
			txtCurrentField.setText("null");
		}
	}
	
	public void setBoardFields(List<Field> fields) {
		if (currentBoard != null) {
			currentBoard.setFields(fields);
		}
	}
	
	public void setRegions(List<Region> regions) {
		model.removeAllElements();
		for (Region r : regions) {
			model.addElement(r);
		}
		if (currentBoard != null) {
			currentBoard.setRegions(regions);
		}
	}
	
	private int[] calculatePosition(Point p) {
		BufferedImage img = panel_board_image.getImage();
		double imgW = img.getWidth();
		double imgH = img.getHeight();
		double maxW = scrollPane.getWidth()-25;
		double maxH = scrollPane.getHeight()-25;
		double clickX;
		double clickY;
		if (overview) {
			double proportion = imgW/imgH;
			double newProportion = maxW/maxH;
			if (newProportion > proportion) {
				//Height is significant
				maxW = (int) (maxH * proportion);
			}
			else {
				//Width is significant
				maxH = (int) (maxW/ proportion);
			}
			clickX = p.getX() * imgW / maxW;
			clickY = p.getY() * imgH / maxH;
			return new int[] {(int) clickX, (int) clickY};
		}
		else {
			return new int[] {(int) p.getX(), (int) p.getY()};
		}
	}
}