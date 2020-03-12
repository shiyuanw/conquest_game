package GameConsole.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * This class is the user interface for the beginning of the tournament mode where
 * the user can select maps, computer players, number of games and turns
 */
public class TournamentGamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JTextField textField;
	public JFileChooser fc1 = new JFileChooser();
	public JFileChooser fc2 = new JFileChooser();
	public JFileChooser fc3 = new JFileChooser();
	public JFileChooser fc4 = new JFileChooser();
	public JFileChooser fc5 = new JFileChooser();
	public String path1;
	public String path2;
	public String path3;
	public String path4;
	public String path5;
	
	
	

	public JButton startGameButt;
	public JButton CancelButt;

	public JLabel M1Label;
	public JLabel M2Label;
	public JLabel M3Label;
	public JLabel M4Label;
	public JLabel M5Label;

	public JComboBox<String> comboBox_6;
	public JComboBox<String> comboBox_7;
	public JComboBox<String> comboBox_8;
	public JComboBox<String> comboBox_9;
	public JComboBox<String> comboBox;

	/**
	 * Create the application.
	 */
	@SuppressWarnings("all")
	public TournamentGamePanel() {

		setLayout(null);
		setBounds(0, 0, 1200, 900);
		JLabel lblNewLabel = new JLabel("Player Selection ");
		lblNewLabel.setBackground(new Color(255, 255, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 22));
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBounds(93, 60, 182, 38);
		add(lblNewLabel);

		JLabel p1Label = new JLabel("Computer 1:");
		p1Label.setBounds(289, 152, 75, 15);
		add(p1Label);

		JLabel p2Label = new JLabel("Computer 2:");
		p2Label.setBounds(714, 152, 75, 15);
		add(p2Label);

		JLabel p3Label = new JLabel("Computer 3:");
		p3Label.setBounds(289, 202, 75, 15);
		add(p3Label);

		JLabel p4Label = new JLabel("Computer 4:");
		p4Label.setBounds(714, 199, 75, 15);
		add(p4Label);

		comboBox_6 = new JComboBox();
		comboBox_6.setModel(
				new DefaultComboBoxModel(new String[] { "none", "aggressive", "benevolent", "random", "cheater" }));
		comboBox_6.setBounds(504, 149, 102, 21);
		add(comboBox_6);

		comboBox_7 = new JComboBox();
		comboBox_7.setModel(
				new DefaultComboBoxModel(new String[] { "none", "aggressive", "benevolent", "random", "cheater" }));
		comboBox_7.setBounds(891, 149, 102, 21);
		add(comboBox_7);

		comboBox_8 = new JComboBox();
		comboBox_8.setModel(
				new DefaultComboBoxModel(new String[] { "none", "aggressive", "benevolent", "random", "cheater" }));
		comboBox_8.setBounds(504, 199, 102, 21);
		comboBox_8.setEnabled(false);
		add(comboBox_8);

		comboBox_9 = new JComboBox();
		comboBox_9.setModel(
				new DefaultComboBoxModel(new String[] { "none", "aggressive", "benevolent", "random", "cheater" }));
		comboBox_9.setBounds(891, 199, 102, 21);
		comboBox_9.setEnabled(false);
		add(comboBox_9);


		comboBox_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text7 = (String) comboBox_7.getSelectedItem();
				if (text7.equals("none")) {
					comboBox_8.setEnabled(false);
				} else {
					comboBox_8.setEnabled(true);
				}
			}
		});

		comboBox_8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text8 = (String) comboBox_8.getSelectedItem();
				if (text8.equals("none")) {
					comboBox_9.setEnabled(false);
				} else {
					comboBox_9.setEnabled(true);
				}
			}
		});

		startGameButt = new JButton("Start Game");
		startGameButt.setBackground(Color.WHITE);
		startGameButt.setBounds(434, 772, 102, 23);
		add(startGameButt);

		CancelButt = new JButton("Cancel");
		CancelButt.setBounds(707, 772, 93, 23);
		add(CancelButt);

		JLabel lblNewLabel_1 = new JLabel("Games Number");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(268, 261, 102, 15);
		add(lblNewLabel_1);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
		comboBox.setBounds(504, 255, 102, 21);
		add(comboBox);

		JLabel lblNewLabel_2 = new JLabel("Max Turns :");
		lblNewLabel_2.setBounds(714, 261, 75, 15);
		add(lblNewLabel_2);

		textField = new JTextField();
		textField.setBounds(891, 255, 102, 21);
		add(textField);
		textField.setColumns(10);

		JLabel lblNewM5Label = new JLabel("(input 10-50)");
		lblNewM5Label.setBounds(1018, 258, 84, 15);
		add(lblNewM5Label);

		JLabel lblNewLabel_4 = new JLabel("Map Selection");
		lblNewLabel_4.setFont(new Font("Calibri", Font.PLAIN, 22));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(93, 385, 182, 38);
		add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Map 1:");
		lblNewLabel_5.setBounds(284, 502, 54, 15);
		add(lblNewLabel_5);

		JLabel lblMap = new JLabel("Map 2:");
		lblMap.setBounds(284, 542, 54, 15);
		add(lblMap);

		JLabel lblMap_1 = new JLabel("Map 3:");
		lblMap_1.setBounds(284, 582, 54, 15);
		add(lblMap_1);

		JLabel lblMap_2 = new JLabel("Map 4:");
		lblMap_2.setBounds(284, 622, 54, 15);
		add(lblMap_2);

		JLabel lblMap_3 = new JLabel("Map 5:");
		lblMap_3.setBounds(284, 662, 54, 15);
		add(lblMap_3);

		JButton btnSelectM = new JButton("select M1");
		btnSelectM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc1.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc1.getSelectedFile();
					String path = file.getAbsolutePath();
					path1 = path;
					M1Label.setText(path);
				}
			}
		});
		btnSelectM.setBounds(504, 494, 93, 23);
		add(btnSelectM);

		JButton btnSelectM_1 = new JButton("select M2");
		btnSelectM_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc2.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc2.getSelectedFile();
					String path = file.getAbsolutePath();
					path2 = path;
					M2Label.setText(path);
				}
			}
		});
		btnSelectM_1.setBounds(504, 534, 93, 23);
		add(btnSelectM_1);

		JButton btnSelectM_2 = new JButton("select M3");
		btnSelectM_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc3.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc3.getSelectedFile();
					String path = file.getAbsolutePath();
					path3 = path;
					M3Label.setText(path);
				}
			}
		});
		btnSelectM_2.setBounds(504, 574, 93, 23);
		add(btnSelectM_2);

		JButton btnSelectM_3 = new JButton("select M4");
		btnSelectM_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc4.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc4.getSelectedFile();
					String path = file.getAbsolutePath();
					path4 = path;
					M4Label.setText(path);
				}
			}
		});
		btnSelectM_3.setBounds(504, 614, 93, 23);
		add(btnSelectM_3);

		JButton btnSelectM_4 = new JButton("select M5");
		btnSelectM_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc5.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc5.getSelectedFile();
					String path = file.getAbsolutePath();
					path5 = path;
					M5Label.setText(path);
				}
			}
		});
		btnSelectM_4.setBounds(504, 654, 93, 23);
		add(btnSelectM_4);

		M1Label = new JLabel("");
		M1Label.setHorizontalAlignment(SwingConstants.CENTER);
		M1Label.setForeground(Color.RED);
		M1Label.setBounds(815 - 140, 498, 400, 19);
		add(M1Label);

		M2Label = new JLabel("");
		M2Label.setHorizontalAlignment(SwingConstants.CENTER);
		M2Label.setForeground(Color.RED);
		M2Label.setBounds(815 - 140, 536, 400, 19);
		add(M2Label);

		M3Label = new JLabel("");
		M3Label.setHorizontalAlignment(SwingConstants.CENTER);
		M3Label.setForeground(Color.RED);
		M3Label.setBounds(815 - 140, 578, 400, 19);
		add(M3Label);

		M4Label = new JLabel("");
		M4Label.setHorizontalAlignment(SwingConstants.CENTER);
		M4Label.setForeground(Color.RED);
		M4Label.setBounds(815 - 140, 618, 400, 19);
		add(M4Label);

		M5Label = new JLabel("");
		M5Label.setHorizontalAlignment(SwingConstants.CENTER);
		M5Label.setForeground(Color.RED);
		M5Label.setBounds(815 - 140, 658, 400, 19);
		add(M5Label);
		setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame jFrame = new JFrame("");
		jFrame.getContentPane().setLayout(null);
		jFrame.setBounds(0, 0, 1200, 900);
		TournamentGamePanel sgp = new TournamentGamePanel();
		sgp.setBounds(0, 0, 1200, 900);
		jFrame.getContentPane().add(sgp);
		jFrame.setVisible(true);

	}
}
