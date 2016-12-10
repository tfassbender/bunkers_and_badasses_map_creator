package net.jfabricationgames.bab_map_creator.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;

public class ConnectionConsistenceDialog extends JDialog {
	
	private static final long serialVersionUID = -3441752381381356528L;
	
	private final JPanel contentPanel = new JPanel();
	
	public ConnectionConsistenceDialog(FieldConnectorFrame callingFrame, boolean consistent, List<Field> inconsistentFields) {
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConnectionConsistenceDialog.class.getResource("/com/jfabricationgames/toolbox/images/icon.png")));
		setBounds(100, 100, 300, 300);
		setLocationRelativeTo(callingFrame);
		
		if (consistent) {
			setTitle("Connections Consistent");
		}
		else {
			setTitle("Connections Inconsistent");
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
			panel.setLayout(new MigLayout("", "[grow,center]", "[][grow][]"));
			{
				JLabel lblInconsistentFields = new JLabel("Inconsistent Fields:");
				lblInconsistentFields.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblInconsistentFields, "cell 0 0");
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, "cell 0 1,grow");
				{
					DefaultListModel<Field> model = new DefaultListModel<Field>();
					for (Field field : inconsistentFields) {
						model.addElement(field);
					}
					JList<Field> list = new JList<Field>(model);
					list.setBackground(Color.LIGHT_GRAY);
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					list.setFont(new Font("Tahoma", Font.PLAIN, 12));
					scrollPane.setViewportView(list);
				}
			}
			{
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnOk.setBackground(Color.GRAY);
				panel.add(btnOk, "cell 0 2");
			}
		}
	}
}