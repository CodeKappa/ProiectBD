package databaseView_PanelProfesor;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import databaseModel.AdminSqlQueries;
import databaseModel.PersoaneSqlQueries;
import databaseModel.ProfesorSqlQueries;
import databaseModel.TreatException;
import main.MainClass;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class PanelCalendar extends JPanel {
	private JTextField textFieldDataInceput;
	public JButton btnProgramareCalendar = new JButton("Programare");
	public JButton btnDescarcareCalendar = new JButton("Descarcare");
	public JComboBox comboBoxCategorie = new JComboBox();
	
	private JTable tableAfis = new JTable();
	private JScrollPane jsp = new JScrollPane(tableAfis);
	private JTextField textFieldDataFinal;
	private JTextField textFieldDurata;
	private JTextField textFieldIdMaterie;
	private JTextField textFieldNrMaxElevi;

	
	/**
	 * Create the panel.
	 */
	public PanelCalendar() {
		setLayout(null);
		
		setBorder(new LineBorder(Color.BLACK, 1));
		
		jsp.setBounds(2, 2, 959, 450);
		add(jsp);
		
		textFieldDataInceput = new JTextField();
		textFieldDataInceput.setBounds(1073, 40, 112, 19);
		add(textFieldDataInceput);
		textFieldDataInceput.setColumns(10);
		
		JLabel lblId = new JLabel("Data/ora inceput");
		lblId.setBounds(965, 41, 105, 19);
		add(lblId);

		btnProgramareCalendar.setBounds(1086, 415, 105, 21);
		add(btnProgramareCalendar);
		
		textFieldDataFinal = new JTextField();
		textFieldDataFinal.setColumns(10);
		textFieldDataFinal.setBounds(1073, 71, 112, 19);
		add(textFieldDataFinal);
		
		textFieldDurata = new JTextField();
		textFieldDurata.setColumns(10);
		textFieldDurata.setBounds(1073, 101, 112, 19);
		add(textFieldDurata);
		
		textFieldIdMaterie = new JTextField();
		textFieldIdMaterie.setColumns(10);
		textFieldIdMaterie.setBounds(1073, 131, 112, 19);
		add(textFieldIdMaterie);
		
		textFieldNrMaxElevi = new JTextField();
		textFieldNrMaxElevi.setColumns(10);
		textFieldNrMaxElevi.setBounds(1073, 161, 112, 19);
		add(textFieldNrMaxElevi);
		
		comboBoxCategorie.setModel(new DefaultComboBoxModel(new String[] {"Curs", "Seminar", "Laborator"}));
		comboBoxCategorie.setBounds(1073, 191, 112, 19);
		add(comboBoxCategorie);
		
		JLabel lblId_1 = new JLabel("Data/ora final");
		lblId_1.setBounds(965, 72, 87, 19);
		add(lblId_1);
		
		JLabel lblId_2 = new JLabel("Durata");
		lblId_2.setBounds(965, 101, 87, 19);
		add(lblId_2);
		
		JLabel lblId_3 = new JLabel("ID Materie");
		lblId_3.setBounds(965, 131, 87, 19);
		add(lblId_3);
		
		JLabel lblId_4 = new JLabel("Nr max elevi");
		lblId_4.setBounds(965, 161, 87, 19);
		add(lblId_4);
		
		JLabel lblCategorie = new JLabel("Categorie");
		lblCategorie.setBounds(965, 191, 87, 19);
		add(lblCategorie);
		
		btnDescarcareCalendar.setBounds(971, 415, 105, 21);
		add(btnDescarcareCalendar);
		
		setActionListeners();
	}
	
	public void setActionListeners()
	{
		btnProgramareCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					ProfesorSqlQueries.programare_calendar(MainClass.db.getCon(), getData()); //TODO
					setTable(PersoaneSqlQueries.vizualizare_calendar(MainClass.db.getCon(), false));
				} 
				catch (SQLException e1) { TreatException.printSQLException(e1); }
			}
		});
		

		btnDescarcareCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					ProfesorSqlQueries.descarcare_calendar(MainClass.db.getCon(), false);
				} 
				catch (SQLException e1) { TreatException.printSQLException(e1); }
			}
		});
	}
	
	public void setTable(ArrayList<ArrayList<String>> a)
	{
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
	
	public ArrayList<String> getData()
	{
		ArrayList<String> data = new ArrayList<>();
		data.add(textFieldDataInceput.getText());
		data.add(textFieldDataFinal.getText());
		data.add(textFieldDurata.getText());
		data.add(textFieldIdMaterie.getText());
		if(comboBoxCategorie.getSelectedIndex() == 0) {
			data.add("Curs");
		}
		else if(comboBoxCategorie.getSelectedIndex() == 1) {
			data.add("Seminar");
		}
		else {
			data.add("Laborator");
		}
		data.add(textFieldNrMaxElevi.getText());
		return data;
	}
	
	public void setData(ArrayList<String> arr)
	{
		if(arr == null)
		{
			textFieldDataInceput.setText(null);
		}
		else
		{
			textFieldDataInceput.setText(arr.get(0));
		}	
	}
	
    private void tableAfisMouseClicked(MouseEvent evt) {
        String id = tableAfis.getValueAt(tableAfis.getSelectedRow(), 0).toString();
        try 
        {
        	setData(AdminSqlQueries.read_grup(MainClass.db.getCon(), id));
		} 
        catch (SQLException e) { TreatException.printSQLException(e); }
    }
}
