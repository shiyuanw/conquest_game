package MapEditor.View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import MapEditor.Model.ConquestMap;
import MapEditor.Util.MyStringUtil;

/**
 * this class is GUI for setting conquest map.
 */
public class SettingsFrame {
	private ConquestMap map;
	private JFrame frame;
	private JTextField tAuthor;
	private JButton imgBtn, confirmBtn, cancelBtn;
	private JFileChooser fc;
	private JLabel errMsg = new JLabel();
	private JLabel pathMsg = new JLabel();
	private JCheckBox warnCheckBox;

	/**
	 * Create the application.
	 */
	public SettingsFrame(ConquestMap map) {
		this.map = map;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fc = new JFileChooser();
		frame = new JFrame();
		frame.setBounds(400, 100, 450, 370 - 100);
		frame.setResizable(false);
		frame.setTitle("Settings");

		tAuthor = new JTextField();
		tAuthor.setBounds(140, 35, 100, 21);
		tAuthor.setColumns(10);
		tAuthor.setText(map.getAuthor());

		warnCheckBox = new JCheckBox("");
		warnCheckBox.setBounds(140, 121 - 50, 21, 21);
		warnCheckBox.setSelected(map.isWarn());

		JLabel lblNewLabel_1 = new JLabel("ImagePath:");
		lblNewLabel_1.setBounds(40, 247, 100, 15);

		pathMsg.setBounds(40, 210 - 100, 410, 15);
		pathMsg.setForeground(Color.RED);
		pathMsg.setText(map.getImageFilePath());

		errMsg.setBounds(40, 10, 300, 15);
		errMsg.setForeground(Color.RED);

		
		imgBtn = new JButton("Click if you want to change a background!");// ImagePath button no Name needed
		imgBtn.setBounds(140, 243 - 100, 165, 23);
		imgBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File path = fc.getSelectedFile();
					if (MyStringUtil.checkType(path)) {
						map.setImageFilePath(path.getAbsolutePath());
						map.setMapFilePath(MyStringUtil.getMapPath(path));
						System.out.println(path.getAbsolutePath());
						LogPanel.addLog("You have chose a image in the path:" + path.getAbsolutePath());
						pathMsg.setText(path.getAbsolutePath());
					} else {
						LogPanel.addLog("Please choose a validate type of image!");
						errMsg.setText("Please choose a validate type of image!");
					}
				}
			}
		});

		confirmBtn = new JButton("Change");
		confirmBtn.setBounds(70, 150 + 40, 100, 23);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errMsg.setText("");
				if (validateInfo()) {
					changeSettings();
					frame.setVisible(false);
				}
			}
		});
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(220 + 30, 150 + 40, 100, 23);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogPanel.addLog("Settings change command cancelled by user.");
				frame.setVisible(false);
			}
		});

		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblNewLabel_1);
		frame.getContentPane().add(tAuthor);
		frame.getContentPane().add(pathMsg);
		frame.getContentPane().add(errMsg);
		frame.getContentPane().add(imgBtn);
		frame.getContentPane().add(confirmBtn);
		frame.getContentPane().add(cancelBtn);
		frame.getContentPane().add(warnCheckBox);

		JLabel lblNewLabel = new JLabel("Author:");
		lblNewLabel.setBounds(40, 40, 54, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_3 = new JLabel("Warn:");
		lblNewLabel_3.setBounds(40, 126 - 50, 54, 15);
		frame.getContentPane().add(lblNewLabel_3);

		frame.setVisible(true);
	}

	/**
	 * method to check a changed author name is valid.
	 * 
	 * @return return true if a author name is not blank and image path is
	 *         valid, otherwise false and showing the error message.
	 */
	private boolean validateInfo() {
		if (tAuthor.getText().trim().equals("") || tAuthor.getText() == null) {
			errMsg.setText("Author cannot be blank!");
			return false;
		}
		if (map.getImageFilePath() == null) {
			errMsg.setText("Please choose a valid image path!");
			return false;
		}
		return true;
	}
	
	/**
	 * once the edition is finished, set new author.
	 */
	public void changeSettings() {
		map.setAuthor(tAuthor.getText().trim());
		map.setWarn(warnCheckBox.isSelected());
	}

}
