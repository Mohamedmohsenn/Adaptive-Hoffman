
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.Scanner;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class Gui2 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui2 frame = new Gui2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui2() {
		setBackground(new Color(240, 240, 240));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1265, 687);
		contentPane = new JPanel();
		contentPane.setForeground(Color.RED);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnCompression = new JButton("Compression");
		btnCompression.setBackground(new Color(255, 69, 0));
		btnCompression.setForeground(new Color(0, 0, 0));
		btnCompression.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String x = textField.getText().toString();
				AdaptHoffmanOperations b = new AdaptHoffmanOperations();
				try {
					for (int i = 0; i < x.length(); i++) {
						if (x.charAt(i) == '1' || x.charAt(i) == '2'
								|| x.charAt(i) == '3' || x.charAt(i) == '4'
								|| x.charAt(i) == '5' || x.charAt(i) == '6'
								|| x.charAt(i) == '7' || x.charAt(i) == '8'
								|| x.charAt(i) == '9') {
							Exception e = null;
							throw e;
						}
					}
					textField_1.setText(b.compression(x));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Enter valid characters");
				}
			}
		});
		btnCompression.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnCompression.setBounds(151, 239, 379, 136);
		contentPane.add(btnCompression);

		JButton btnDecompression = new JButton("Decompression");
		btnDecompression.setBackground(new Color(127, 255, 0));
		btnDecompression.setForeground(new Color(0, 0, 0));
		btnDecompression.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnDecompression.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String x = textField.getText().toString();
				try {
					AdaptHoffmanOperations b = new AdaptHoffmanOperations();
					b.deCompression(x);
					textField_1.setText(b.getDecompression());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Enter valid Numbers");
				}
			}
		});
		btnDecompression.setBounds(785, 239, 359, 136);
		contentPane.add(btnDecompression);

		JLabel lblDoYouWant = new JLabel(
				"Do you want to COMPRESS or DECOMPRESS?");
		lblDoYouWant.setForeground(new Color(65, 105, 225));
		lblDoYouWant.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblDoYouWant.setBounds(359, 153, 642, 58);
		contentPane.add(lblDoYouWant);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 22));
		textField.setForeground(new Color(128, 0, 0));
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		textField.setBounds(151, 44, 1062, 92);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblEnter = new JLabel("Enter : ");
		lblEnter.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblEnter.setBounds(47, 67, 131, 43);
		contentPane.add(lblEnter);

		JLabel lblAnswer = new JLabel("Answer :");
		lblAnswer.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblAnswer.setBounds(37, 485, 168, 58);
		contentPane.add(lblAnswer);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.BOLD, 23));
		textField_1.setForeground(new Color(25, 25, 112));
		textField_1.setBounds(151, 449, 1062, 136);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblCompressAndDecompress = new JLabel("Compress and Decompress using Adaptive Hoffman techniche.");
		lblCompressAndDecompress.setForeground(Color.GRAY);
		lblCompressAndDecompress.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCompressAndDecompress.setBounds(12, 0, 422, 16);
		contentPane.add(lblCompressAndDecompress);
	}
}
