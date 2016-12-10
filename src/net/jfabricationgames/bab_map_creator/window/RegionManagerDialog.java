package net.jfabricationgames.bab_map_creator.window;

import java.awt.BorderLayout;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.bunkers_and_badasses.game_board.Region;
import net.miginfocom.swing.MigLayout;

public class RegionManagerDialog extends JDialog {
	
	private static final long serialVersionUID = -8826382967712070339L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtRegion;
	
	private DefaultListModel<Region> model;
	private JList<Region> list;
	
	public RegionManagerDialog(BunkersAndBadassesMapCreatorFrame callingFrame) {
		setTitle("Bunkers and Badasses - Region Manager");
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegionManagerDialog.class.getResource("/com/jfabricationgames/toolbox/images/icon.png")));
		setBounds(100, 100, 375, 400);
		setLocationRelativeTo(callingFrame);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[][grow][]", "[][grow][][][]"));
			{
				JLabel lblRegions = new JLabel("Regions:");
				lblRegions.setFont(new Font("Tahoma", Font.PLAIN, 16));
				panel.add(lblRegions, "cell 0 0 3 1");
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, "cell 0 1 3 1,grow");
				{
					model = new DefaultListModel<Region>();
					list = new JList<Region>(model);
					list.setFont(new Font("Tahoma", Font.PLAIN, 12));
					list.setBackground(Color.LIGHT_GRAY);
					scrollPane.setViewportView(list);
				}
			}
			{
				JLabel lblNewRegion = new JLabel("New Region:");
				lblNewRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
				panel.add(lblNewRegion, "cell 0 2,alignx trailing");
			}
			{
				txtRegion = new JTextField();
				txtRegion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addRegion();
					}
				});
				txtRegion.setBackground(Color.LIGHT_GRAY);
				panel.add(txtRegion, "cell 1 2,growx");
				txtRegion.setColumns(10);
			}
			{
				JButton btnAddRegion = new JButton("Add Region");
				btnAddRegion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addRegion();
					}
				});
				btnAddRegion.setBackground(Color.GRAY);
				panel.add(btnAddRegion, "cell 2 2,growx");
			}
			{
				JButton btnRemoveSelected = new JButton("Remove Selected");
				btnRemoveSelected.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int[] selected = list.getSelectedIndices();
						for (int i = 0; i < selected.length; i++) {
							model.remove(selected[i]-i);
						}
					}
				});
				btnRemoveSelected.setBackground(Color.GRAY);
				panel.add(btnRemoveSelected, "cell 2 3");
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.GRAY);
				panel.add(panel_1, "cell 0 4 3 1,grow");
				panel_1.setLayout(new MigLayout("", "[grow][][][grow]", "[]"));
				{
					JButton btnOk = new JButton("OK");
					btnOk.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							List<Region> regions = new ArrayList<Region>();
							Enumeration<Region> elements = model.elements();
							while (elements.hasMoreElements()) {
								regions.add(elements.nextElement());
							}
							callingFrame.setRegions(regions);
							dispose();
						}
					});
					btnOk.setBackground(Color.GRAY);
					panel_1.add(btnOk, "cell 1 0");
				}
				{
					JButton btnCancel = new JButton("Cancel");
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});
					btnCancel.setBackground(Color.GRAY);
					panel_1.add(btnCancel, "cell 2 0");
				}
			}
		}
	}
	
	private void addRegion() {
		if (!txtRegion.getText().isEmpty()) {
			Region region = new Region();
			region.setName(txtRegion.getText());
			model.addElement(region);
			txtRegion.setText("");
		}
	}
}