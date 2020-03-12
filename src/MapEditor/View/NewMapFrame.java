package MapEditor.View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import MapEditor.Model.ConquestMap;
import MapEditor.Util.MyStringUtil;

/**
 * this class is the GUI for Creating a new conquest map.
 */
public class NewMapFrame {
	private ConquestMap map;
	private JFrame frame;
	private JTextField tAuthor;
	private JButton imgBtn, confirmBtn, cancelBtn;
	private JFileChooser fc;
	private JLabel errMsg = new JLabel();
	private JLabel pathMsg = new JLabel();

	/**
	 * Create the application.
	 */
	public NewMapFrame(ConquestMap map) {
		this.map = map;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		map.clear();
		fc = new JFileChooser();
		frame = new JFrame();
		frame.setBounds(400, 100, 450, 370 - 100);
		frame.setResizable(false);
		frame.setTitle("New Map");

		tAuthor = new JTextField();
		tAuthor.setBounds(140, 35, 100, 21);
		tAuthor.setColumns(10);


		JLabel lblNewLabel_1 = new JLabel("ImagePath:");
		lblNewLabel_1.setBounds(40, 247, 100, 15);

		pathMsg.setBounds(40, 210 - 100, 410, 15);
		pathMsg.setForeground(Color.RED);

		errMsg.setBounds(40, 10, 300, 15);
		errMsg.setForeground(Color.RED);

		
		imgBtn = new JButton("Please select a image!");// ImagePath button no Name needed
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

		confirmBtn = new JButton("Create");
		confirmBtn.setBounds(70, 150 + 40, 100, 23);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errMsg.setText("");
				if (validateInfo()) {
					mapNew();
					frame.setVisible(false);
				}
			}
		});
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(220 + 30, 150 + 40, 100, 23);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogPanel.addLog("New Map command cancelled by user.");
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

		JLabel lblNewLabel = new JLabel("Author:");
		lblNewLabel.setBounds(40, 40, 54, 15);
		frame.getContentPane().add(lblNewLabel);

		frame.setVisible(true);
	}

	/**
	 * method to check the validation of input.
	 * 
	 * @return true if the author name and file path are valid, otherwise false
	 *         and show the error message.
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
	 * once a new map is created set the author name.
	 */
	public void mapNew() {
		map.setAuthor(tAuthor.getText().trim());
		map.setScroll(ConquestMap.ScrollOptions.NONE);
		map.setWarn(true);
		map.setWrap(false);
	}

}
