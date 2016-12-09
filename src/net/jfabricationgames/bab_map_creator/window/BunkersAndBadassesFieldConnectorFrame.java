package net.jfabricationgames.bab_map_creator.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;

public class BunkersAndBadassesFieldConnectorFrame extends JFrame {
	
	private static final long serialVersionUID = 4561226752735012591L;
	
	private JPanel contentPane;
	
	private List<Field> fields;
	
	private DefaultListModel<Field> fieldsModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> connectionsModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> allFieldsModel = new DefaultListModel<Field>();
	
	public BunkersAndBadassesFieldConnectorFrame(BunkersAndBadassesMapCreatorFrame callingFrame, Enumeration<Field> allFields) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(BunkersAndBadassesFieldConnectorFrame.class.getResource("/com/jfabricationgames/toolbox/images/icon.png")));
		setTitle("Bunkers And Badasses - Field Connector");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		setLocationRelativeTo(callingFrame);
		
		fields = new ArrayList<Field>();
		while(allFields.hasMoreElements()){
			fields.add(allFields.nextElement());
		}
		
		for (Field f : fields) {
			fieldsModel.addElement(f);
			allFieldsModel.addElement(f);
		}
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow][grow][grow]", "[][grow][][]"));
		
		JLabel lblFields = new JLabel("Fields:");
		lblFields.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblFields, "cell 0 0,alignx center");
		
		JLabel lblConnections = new JLabel("Connections:");
		lblConnections.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblConnections, "cell 1 0,alignx center");
		
		JLabel lblAllFields = new JLabel("All Fields:");
		lblAllFields.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblAllFields, "cell 2 0,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 0 1,grow");
		
		JList<Field> list_fields = new JList<Field>(fieldsModel);
		list_fields.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				connectionsModel.removeAllElements();
				for (Field field : list_fields.getSelectedValue().getNeighbours()) {
					connectionsModel.addElement(field);
				}
			}
		});
		list_fields.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fields.setBackground(Color.LIGHT_GRAY);
		list_fields.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(list_fields);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, "cell 1 1,grow");
		
		JList<Field> list_connections = new JList<Field>(connectionsModel);
		list_connections.setBackground(Color.LIGHT_GRAY);
		list_connections.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_1.setViewportView(list_connections);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel.add(scrollPane_2, "cell 2 1,grow");
		
		JList<Field> list_all_fields = new JList<Field>(allFieldsModel);
		list_all_fields.setBackground(Color.LIGHT_GRAY);
		list_all_fields.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_2.setViewportView(list_all_fields);
		
		JButton btnRemoveSelected = new JButton("Remove Selected");
		btnRemoveSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selected = list_connections.getSelectedIndices();
				for (int i = 0; i < selected.length; i++) {
					connectionsModel.remove(selected[i]-i);
					list_fields.getSelectedValue().getNeighbours().remove(selected[i]-i);
				}
			}
		});
		btnRemoveSelected.setBackground(Color.GRAY);
		panel.add(btnRemoveSelected, "cell 1 2,alignx center");
		
		JButton btnAddSelected = new JButton("Add Selected");
		btnAddSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Field field : list_all_fields.getSelectedValuesList()) {
					connectionsModel.addElement(field);
					list_fields.getSelectedValue().getNeighbours().add(field);
				}
			}
		});
		btnAddSelected.setBackground(Color.GRAY);
		panel.add(btnAddSelected, "cell 2 2,alignx center");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel.add(panel_1, "cell 0 3 3 1,grow");
		panel_1.setLayout(new MigLayout("", "[grow][grow]", "[]"));
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				callingFrame.setBoardFields(fields);
				dispose();
			}
		});
		btnOk.setBackground(Color.GRAY);
		panel_1.add(btnOk, "cell 0 0,alignx right");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBackground(Color.GRAY);
		panel_1.add(btnCancel, "cell 1 0");
	}
}