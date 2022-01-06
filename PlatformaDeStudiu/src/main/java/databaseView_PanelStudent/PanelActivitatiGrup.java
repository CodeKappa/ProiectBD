package databaseView_PanelStudent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import databaseModel.AdminSqlQueries;
import databaseModel.TreatException;
import main.MainClass;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class PanelActivitatiGrup extends JPanel {
	private JTextField textField_materie;
	private JLabel lblMaterie;
	public JButton btnActivitatiGrup = new JButton("Activitati Grupuri");
	public JButton btnInscrieActivitate = new JButton("Inscriere activitate");
	public JButton btnDescarcaActivitati = new JButton("Descarca Activitati");
	
	private JTable tableAfis = new JTable();
	private JScrollPane jsp = new JScrollPane(tableAfis);
	
	/**
	 * Create the panel.
	 */
	public PanelActivitatiGrup() {
		setLayout(null);
		
		setBorder(new LineBorder(Color.BLACK, 1));
		
		jsp.setBounds(2, 2, 959, 447);
		add(jsp);
		
		textField_materie = new JTextField();
		textField_materie.setBounds(1068, 157, 112, 19);
		add(textField_materie);
		textField_materie.setColumns(10);
		
		lblMaterie = new JLabel("Materie");
		lblMaterie.setBounds(987, 160, 45, 13);
		add(lblMaterie);
		
		btnActivitatiGrup.setBounds(971, 200, 220, 21);
		add(btnActivitatiGrup);
		
		setActionListeners();
		
		btnInscrieActivitate.setBounds(971, 225, 220, 21);
		add(btnInscrieActivitate);

		btnDescarcaActivitati.setBounds(971, 250, 220, 21);
		add(btnDescarcaActivitati);
	}

	public void setActionListeners()
	{	
		btnActivitatiGrup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					setTable(AdminSqlQueries.all_materie_data(MainClass.db.getCon()));
				} 
				catch (SQLException e1) { TreatException.printSQLException(e1); }
			}
		});
		btnInscrieActivitate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					setTable(AdminSqlQueries.cauta_materie(MainClass.db.getCon(), textField_materie.getText()));
				} 
				catch (SQLException e1) { TreatException.printSQLException(e1); }
			}
		});
		btnDescarcaActivitati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					setTable(AdminSqlQueries.studentiLaMaterie(MainClass.db.getCon(), textField_materie.getText()));
				} 
				catch (SQLException e1) { TreatException.printSQLException(e1); }
			}
		});
	}
	
	public void setTable(ArrayList<ArrayList<String>> a)
	{
		if (a == null) return;
		DefaultTableModel dtm = new DefaultTableModel();
		if(a.isEmpty() == false)
			dtm.setColumnCount(a.get(0).size());
		int i = 0, j = 0;
		for(ArrayList<String> arow : a)
		{
			dtm.setRowCount(dtm.getRowCount() + 1);
			j = 0;
			for(String s : arow)
			{		
				dtm.setValueAt(s, i, j);
				j++;
			}
			i++;
		}	
		tableAfis.setModel(dtm);
		repaint();
	}
	
	public void setData()
	{
		textField_materie.setText(null);
		try 
		{
			setTable(AdminSqlQueries.all_materie_data(MainClass.db.getCon()));
		} 
		catch (SQLException e1) { TreatException.printSQLException(e1); }
	}
}