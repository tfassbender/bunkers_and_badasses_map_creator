package net.jfabricationgames.bab_map_creator.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.jfabricationgames.toolbox.graphic.ImageLoader;
import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;
import java.awt.event.MouseAdapter;

public class BunkersAndBadassesMapCreatorFrame extends JFrame {
	
	private static final long serialVersionUID = 6355781685766591536L;
	
	private JPanel contentPane;
	private JTextField txtField;
	private JTextField txtPosition;
	
	private boolean overview = false;
	private ImagePanel panel_board_image;
	private JTextField txtTroupsNormal;
	private JTextField txtTroupsBadass;
	private JTextField txtBildingPosition;
	
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
		setBounds(100, 100, 1200, 600);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_board.add(scrollPane, BorderLayout.CENTER);
		
		ImageLoader loader = new ImageLoader();
		panel_board_image = new ImagePanel(loader.loadImage("net/jfabricationgames/bab_map_creator/window/board_the_badlands.png"));
		panel_board_image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtPosition.setText("(" + e.getPoint().getX() + " | " + e.getPoint().getY() + ")");
			}
		});
		panel_board_image.setBackground(Color.GRAY);
		scrollPane.setViewportView(panel_board_image);
		panel_board_image.setPreferredSize(new Dimension(2400, 1400));
		
		JPanel panel_settings = new JPanel();
		panel_settings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_settings.setBackground(Color.GRAY);
		panel.add(panel_settings, "cell 1 0,grow");
		panel_settings.setLayout(new MigLayout("", "[][][grow]", "[][10px][][200px][][grow]"));
		
		JLabel lblBoardSettings = new JLabel("Board Settings");
		lblBoardSettings.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_settings.add(lblBoardSettings, "cell 0 0 3 1,alignx center");
		
		JLabel lblFields = new JLabel("Fields");
		lblFields.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_settings.add(lblFields, "cell 0 2 3 1");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_settings.add(scrollPane_1, "cell 0 3 3 1,grow");
		
		JList<Field> list = new JList<Field>();
		list.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setViewportView(list);
		
		JButton btnAddConnections = new JButton("Add Connections");
		btnAddConnections.setBackground(Color.GRAY);
		panel_settings.add(btnAddConnections, "cell 0 4");
		
		JButton btnOverview = new JButton("Overview");
		btnOverview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				overview = !overview;
				if (overview) {
					panel_board_image.setPreferredSize(new Dimension(800, 500));					
				}
				else {
					panel_board_image.setPreferredSize(new Dimension(2400, 1400));
				}
				panel_board_image.setAdaptSizeKeepProportion(overview);
				panel_board_image.repaint();
			}
		});
		btnOverview.setBackground(Color.GRAY);
		panel_settings.add(btnOverview, "cell 1 4");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_settings.add(panel_1, "cell 0 5 3 1,growx,aligny center");
		panel_1.setLayout(new MigLayout("", "[][][grow][]", "[][][][][]"));
		
		JLabel lblFieldName = new JLabel("Field Name:");
		lblFieldName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblFieldName, "cell 0 0,alignx trailing");
		
		txtField = new JTextField();
		txtField.setBackground(Color.LIGHT_GRAY);
		panel_1.add(txtField, "cell 1 0 3 1,growx");
		txtField.setColumns(10);
		
		JLabel lblPosition = new JLabel("Position:");
		lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblPosition, "cell 0 1,alignx trailing");
		
		txtPosition = new JTextField();
		txtPosition.setBackground(Color.LIGHT_GRAY);
		txtPosition.setEditable(false);
		panel_1.add(txtPosition, "cell 1 1 3 1,growx");
		txtPosition.setColumns(10);
		
		JLabel lblTroupPositionNormal = new JLabel("Troup Position Normal:");
		lblTroupPositionNormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblTroupPositionNormal, "cell 0 2 2 1");
		
		txtTroupsNormal = new JTextField();
		txtTroupsNormal.setBackground(Color.LIGHT_GRAY);
		txtTroupsNormal.setEditable(false);
		panel_1.add(txtTroupsNormal, "cell 2 2,growx");
		txtTroupsNormal.setColumns(10);
		
		JButton btnSetCurrent = new JButton("Set Current");
		btnSetCurrent.setBackground(Color.GRAY);
		btnSetCurrent.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnSetCurrent, "cell 3 2");
		
		JLabel lblTroupPositionBadass = new JLabel("Troup Position Badass:");
		lblTroupPositionBadass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblTroupPositionBadass, "cell 0 3 2 1");
		
		txtTroupsBadass = new JTextField();
		txtTroupsBadass.setBackground(Color.LIGHT_GRAY);
		txtTroupsBadass.setEditable(false);
		panel_1.add(txtTroupsBadass, "cell 2 3,growx");
		txtTroupsBadass.setColumns(10);
		
		JButton btnSetCurrent_1 = new JButton("Set Current");
		btnSetCurrent_1.setBackground(Color.GRAY);
		btnSetCurrent_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnSetCurrent_1, "cell 3 3");
		
		JLabel lblBuildingPosition = new JLabel("Building Position:");
		lblBuildingPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblBuildingPosition, "cell 0 4 2 1");
		
		txtBildingPosition = new JTextField();
		txtBildingPosition.setBackground(Color.LIGHT_GRAY);
		txtBildingPosition.setEditable(false);
		txtBildingPosition.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(txtBildingPosition, "cell 2 4,growx");
		txtBildingPosition.setColumns(10);
		
		JButton btnSetCurrent_2 = new JButton("Set Current");
		btnSetCurrent_2.setBackground(Color.GRAY);
		btnSetCurrent_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnSetCurrent_2, "cell 3 4");
	}
}