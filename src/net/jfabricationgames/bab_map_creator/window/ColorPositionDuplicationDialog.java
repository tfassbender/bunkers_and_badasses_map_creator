package net.jfabricationgames.bab_map_creator.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;

public class ColorPositionDuplicationDialog extends JDialog {
	
	private static final long serialVersionUID = -157549768157295740L;
	
	private final JPanel contentPanel = new JPanel();
	
	private DefaultListModel<Field> colorDuplicationModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> positionDuplicationModel = new DefaultListModel<Field>();
	
	public ColorPositionDuplicationDialog(BunkersAndBadassesMapCreatorFrame callingFrame, List<Field> colorDuplications, List<Field> positionDuplications) {
		setTitle("Bunkers and Badasses - Color or Position Duplication");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ColorPositionDuplicationDialog.class.getResource("/com/jfabricationgames/toolbox/images/icon.png")));
		setBounds(100, 100, 550, 300);
		setLocationRelativeTo(callingFrame);
		
		for (Field field : colorDuplications) {
			colorDuplicationModel.addElement(field);			
		}
		for (Field field : positionDuplications) {
			positionDuplicationModel.addElement(field);
		}
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[300px,grow,center][300px,grow,center]", "[][grow][]"));
			
			JLabel lblColorDuplications = new JLabel("Color Duplications");
			lblColorDuplications.setFont(new Font("Tahoma", Font.PLAIN, 16));
			panel.add(lblColorDuplications, "cell 0 0");
			
			JLabel lblPositionDuplications = new JLabel("Position Duplications");
			lblPositionDuplications.setFont(new Font("Tahoma", Font.PLAIN, 16));
			panel.add(lblPositionDuplications, "cell 1 0");
			
			JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, "cell 0 1,grow");
			
			JList<Field> list = new JList<Field>(colorDuplicationModel);
			list.setBackground(Color.LIGHT_GRAY);
			list.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane.setViewportView(list);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			panel.add(scrollPane_1, "cell 1 1,grow");
			
			JList<Field> list_1 = new JList<Field>(positionDuplicationModel);
			list_1.setBackground(Color.LIGHT_GRAY);
			list_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane_1.setViewportView(list_1);
			
			JButton btnOk = new JButton("OK");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnOk.setBackground(Color.GRAY);
			panel.add(btnOk, "cell 0 2 2 1");
		}
	}
}