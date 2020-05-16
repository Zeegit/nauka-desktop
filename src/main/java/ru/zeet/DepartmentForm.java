package ru.zeet;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

public class DepartmentForm extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepartmentForm frame = new DepartmentForm();
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
	public DepartmentForm() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelTop = new JPanel();
		contentPane.add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel = new JLabel("\u0421\u043F\u0438\u0441\u043E\u043A \u0434\u0435\u043F\u0430\u0440\u0442\u0430\u043C\u0435\u043D\u0442\u043E\u0432");
		panelTop.add(lblNewLabel);
		
		JPanel panelBottom = new JPanel();
		FlowLayout fl_panelBottom = (FlowLayout) panelBottom.getLayout();
		fl_panelBottom.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Добавить");
		panelBottom.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Изменить");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DeparntamentEdit dialog = new DeparntamentEdit();
				dialog.setModal(true);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		panelBottom.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Удалить");
		panelBottom.add(btnNewButton_2);
		
		JPanel panelMain = new JPanel();
		contentPane.add(panelMain, BorderLayout.CENTER);
		
		table = new JTable();
		panelMain.add(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		panelMain.add(scrollPane);
	}

}
