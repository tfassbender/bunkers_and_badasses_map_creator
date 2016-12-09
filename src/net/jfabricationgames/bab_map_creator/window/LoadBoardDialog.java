package net.jfabricationgames.bab_map_creator.window;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.miginfocom.swing.MigLayout;

public class LoadBoardDialog extends JDialog {
	
	private static final long serialVersionUID = -7669527538028461342L;
	
	private JFileChooser fileChooser = new JFileChooser(new File("."));
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtBoardImage;
	private JTextField txtBoardName;
	
	private ImageLoader loader = new ImageLoader();
	
	private File image;
	
	public LoadBoardDialog(BunkersAndBadassesMapCreatorFrame callingFrame) {
		setAlwaysOnTop(true);
		setTitle("Bunkers and Badasses - Load Board");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoadBoardDialog.class.getResource("/com/jfabricationgames/toolbox/images/icon.png")));
		setBounds(100, 100, 400, 150);
		setLocationRelativeTo(callingFrame);
		contentPanel.setBackground(Color.DARK_GRAY);
		
		setContentPane(contentPanel);
		contentPanel.setLayout(new MigLayout("", "[240px,grow]", "[102px,grow]"));
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPanel.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[][grow][grow][]", "[][][10px][grow]"));
		{
			JLabel lblBoardImage = new JLabel("Board Image:");
			panel.add(lblBoardImage, "cell 0 0,alignx trailing");
		}
		{
			txtBoardImage = new JTextField();
			txtBoardImage.setEditable(false);
			txtBoardImage.setBackground(Color.LIGHT_GRAY);
			panel.add(txtBoardImage, "cell 1 0 2 1,growx");
			txtBoardImage.setColumns(10);
		}
		{
			JButton btnBrowse = new JButton("Browse");
			btnBrowse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (fileChooser.showOpenDialog(LoadBoardDialog.this) == JFileChooser.APPROVE_OPTION) {
						txtBoardImage.setText(fileChooser.getSelectedFile().getAbsolutePath());
						image = fileChooser.getSelectedFile();
					}
				}
			});
			btnBrowse.setBackground(Color.GRAY);
			panel.add(btnBrowse, "cell 3 0");
		}
		{
			JLabel lblBoardName = new JLabel("Board Name:");
			panel.add(lblBoardName, "cell 0 1,alignx trailing");
		}
		{
			txtBoardName = new JTextField();
			txtBoardName.setBackground(Color.LIGHT_GRAY);
			panel.add(txtBoardName, "cell 1 1 2 1,growx");
			txtBoardName.setColumns(10);
		}
		{
			JButton btnOk = new JButton("OK");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!txtBoardImage.getText().isEmpty() && !txtBoardName.getText().isEmpty()) {
						Board board = new Board();
						BufferedImage img = loader.loadImage(image);
						if (img != null) {
							board.setBaseImage(img);
							board.setName(txtBoardName.getText());
							callingFrame.setBoard(board);
						}
					}
					dispose();
				}
			});
			btnOk.setBackground(Color.GRAY);
			panel.add(btnOk, "cell 0 3 2 1,alignx right");
		}
		{
			JButton btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancel.setBackground(Color.GRAY);
			panel.add(btnCancel, "cell 2 3 2 1");
		}
	}
}