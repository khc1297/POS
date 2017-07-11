

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class New extends JDialog{
	
	private JTextField nameField;
	private JTextField phoneField;
	private javax.swing.JSpinner spinner;
	private javax.swing.SpinnerModel model;
	private	DefaultTableModel model2;
	private JTable table;
	private javax.swing.JComboBox<String> rm_jComboBox_day;
	private javax.swing.JComboBox<String> rm_jComboBox_month;
	private javax.swing.JComboBox<String> rm_jComboBox_year;
	String[] field = {"�ڸ�NO.","������Ȳ"};
	String year;
	String month;
	String day;
	private String Datestring;
	
	public New()
	{

		rm_jComboBox_year = new javax.swing.JComboBox<String>();
		rm_jComboBox_month = new javax.swing.JComboBox<String>();
		rm_jComboBox_day = new javax.swing.JComboBox<String>();

		initvalue();
		setTitle("����ȭ��-����ڸ��");
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(null);
		Date now = new Date();
		model = new SpinnerDateModel(now,null,now,Calendar.DAY_OF_WEEK);
		spinner = new JSpinner(model);

		rm_jComboBox_year.setBounds(70, 45, 70, 30);
		getContentPane().add(rm_jComboBox_year);
		JLabel rm_jLabel1 = new JLabel("��");
		rm_jLabel1.setBounds(140,45,70,30);
		getContentPane().add(rm_jLabel1);
		rm_jComboBox_month.setBounds(150, 45, 70, 30);
		getContentPane().add(rm_jComboBox_month);
		JLabel rm_jLabel2 = new JLabel("��");
		rm_jLabel2.setBounds(220,45,70,30);
		getContentPane().add(rm_jLabel2);
		rm_jComboBox_day.setBounds(230, 45, 70, 30);
		getContentPane().add(rm_jComboBox_day);
		JLabel rm_jLabel3 = new JLabel("��");
		rm_jLabel3.setBounds(300,45,70,30);
		getContentPane().add(rm_jLabel3);
	
		JLabel dateLabel = new JLabel("��¥:");
		dateLabel.setBounds(30, 45, 450, 25);
		getContentPane().add(dateLabel);
		
		JButton ressrchBtn = new JButton("������ȸ");
		ressrchBtn.addActionListener(new searchreservation());
		ressrchBtn.setBounds(350, 45, 150, 25);
		getContentPane().add(ressrchBtn);
		
		model2 = new DefaultTableModel(field, 0);
		table = new JTable(model2);
		JScrollPane pane = new JScrollPane(table);
		getContentPane().add(pane);
		pane.setBounds(40,80,500,200);
		
		JLabel nameLabel = new JLabel("�����ڸ�");
		nameLabel.setBounds(30, 300, 100, 25);
		getContentPane().add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(99, 300, 100, 25);
		getContentPane().add(nameField);
		
		JLabel phoneLabel = new JLabel("����ó : ");
		phoneLabel.setBounds(300, 300, 100, 25);
		getContentPane().add(phoneLabel);
		
		phoneField = new JTextField();
		phoneField.setBounds(350, 300, 100, 25);
		getContentPane().add(phoneField);
		
		JButton doresBtn = new JButton("�����ϱ�");
		doresBtn.addActionListener(new Doreservation());
		doresBtn.setBounds(45, 350, 150, 30);
		getContentPane().add(doresBtn);
		
		JButton canresBtn = new JButton("�������");
		canresBtn.addActionListener(new Canselreservation());
		canresBtn.setBounds(200, 350, 150, 30);
		getContentPane().add(canresBtn);
	}
	//������ȸ ��ư
	private  class searchreservation implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				model2.setNumRows(0);
				String year = (String) rm_jComboBox_year.getSelectedItem();
				String month = (String) rm_jComboBox_month.getSelectedItem();
				String day = (String) rm_jComboBox_day.getSelectedItem();
				Data data = new Data();
				ArrayList<Data> tmp3 = data.getInfo();
				for(int i = 0 ; i < tmp3.size(); i++)
				{
					if(month.equals(tmp3.get(i).getmonth()) && day.equals(tmp3.get(i).getDay()) && year.equals(tmp3.get(i).getyear()))
					{
						Vector v = new Vector();
						if(!tmp3.get(i).getName().equals("--"))
						{	
							v.addElement(tmp3.get(i).getSeats());
							v.addElement(tmp3.get(i).getRes());	
							model2.addRow(v);
							continue;
						}

						v.addElement(tmp3.get(i).getSeats());
						v.addElement(tmp3.get(i).getName());
						model2.addRow(v);
					}
				}
				
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, "��ȸ����� �о���� ���մϴ�.");
			}
		}
	}
	//�����ϱ� ��ư
	private  class Doreservation implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				//���� ��¥�� �޾ƿɴϴ�. ���� ��¥���� �����̸� ����ó���� �߻���ŵ�ϴ�.
				int YEAR = cal.get ( cal.YEAR );
				int MONTH = cal.get ( cal.MONTH ) + 1 ;
				int DATE = cal.get ( cal.DATE ) ;
				String year = (String) rm_jComboBox_year.getSelectedItem();
				String month = (String) rm_jComboBox_month.getSelectedItem();
				String day = (String) rm_jComboBox_day.getSelectedItem();
				int Month = Integer.parseInt(month);		
				int Year = Integer.parseInt(year);
				int Day = Integer.parseInt(day);
				if(YEAR > Year || (YEAR == Year && MONTH > Month) || (YEAR == Year && MONTH == Month && DATE > Day))
					throw new Exception("����ð����� �����Դϴ�.");
				int[] items = table.getSelectedRows();
				String name = nameField.getText();
				String phone = phoneField.getText();
			
					if (items.length == 0)
						throw new Exception("������ ������ �ּ���.");
					for (int i : items) {
						String status = (String) model2.getValueAt(i, 1);
						String seats = (String) model2.getValueAt(i, 0);
						if(status.equals("Reserved"))
							throw new Exception("������ �Ұ��� �մϴ�.");
						if (name.length() == 0 || phone.length() ==0)
							throw new Exception("�����ڸ�� ����ó�� �Է��ϼ���");
						Data data = new Data(month,day,year,seats,name, phone);
						Data.updateInfo(data,month,day,year,seats);
						JOptionPane.showMessageDialog(null,"������ �����Ǿ����ϴ�.");
					}
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, err.getMessage());
			}
		}
	}
	//������� ��ư ���⼭ ����ó���� �߻��մϴ�.
	private  class Canselreservation implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			int[] items = table.getSelectedRows();
			String name = nameField.getText();		
			String phone = phoneField.getText();	
			int tmp=0;
			try {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				int YEAR = cal.get ( cal.YEAR );
				int MONTH = cal.get ( cal.MONTH ) + 1 ;
				int DATE = cal.get ( cal.DATE ) ;
				String year = (String) rm_jComboBox_year.getSelectedItem();
				String month = (String) rm_jComboBox_month.getSelectedItem();
				String day = (String) rm_jComboBox_day.getSelectedItem();
				int Month = Integer.parseInt(month);		
				int Year = Integer.parseInt(year);
				int Day = Integer.parseInt(day);
				if(YEAR > Year || (YEAR == Year && MONTH > Month) || (YEAR == Year && MONTH == Month && DATE > Day))
					throw new Exception("����ð����� �����Դϴ�.");
				if (items.length == 0)
					throw new Exception("������ ������ �ּ���.");
				for (int i : items) {
					String status = (String) model2.getValueAt(i, 1);
					String seats = (String) model2.getValueAt(i, 0);
					
					if(status.equals("--"))
						throw new Exception("��Ұ� �Ұ��� �մϴ�.");
					if (name.length() == 0 || phone.length() ==0)
						throw new Exception("����Ϸ��� �����ڸ�� ����ó�� �Է��ϼ���");
					
					Data data = new Data();
					tmp =(int) Data.updatecanselInfo(data,month,day,year,name,phone);
					if(tmp == -1 || tmp ==0)
					{
						JOptionPane.showMessageDialog(null, "������ Ʋ���ϴ�.");
						tmp=0;
					}
					else{
						JOptionPane.showMessageDialog(null,"��Ұ� �����Ǿ����ϴ�.");
						tmp=0;
					}
				}
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, err.getMessage());
			}
		}
	}
	//���� ����
	private void initvalue() {
		// ��¥ ǥ��
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
				"yyyy�� MM�� dd��");
		Date currentTime = new Date();
		Datestring = String.format(("%s"),
				mSimpleDateFormat.format(currentTime));
		

		// �޺��ڽ��� ���� ����
		for (int i = 1900; i < 2100; i++) {
			String tmp = String.format(("%d"), i);
			rm_jComboBox_year.addItem(tmp);
		}
		SimpleDateFormat tmp_year = new SimpleDateFormat("yyyy");
		rm_jComboBox_year.setSelectedItem(tmp_year.format(currentTime));
		// �޺��ڽ��� �� ����
		for (int i = 1; i < 13; i++) {
			String tmp = String.format(("%02d"), i);
			rm_jComboBox_month.addItem(tmp);
		}
		SimpleDateFormat tmp_month = new SimpleDateFormat("MM");
		rm_jComboBox_month.setSelectedItem(tmp_month.format(currentTime));
		// �޺��ڽ��� �� ����
		for (int i = 1; i <= calc_number_of_month(rm_jComboBox_year,
				rm_jComboBox_month); i++) {
			String tmp = String.format(("%02d"), i);
			rm_jComboBox_day.addItem(tmp);
		}
		SimpleDateFormat tmp_day = new SimpleDateFormat("dd");
		rm_jComboBox_day.setSelectedItem(tmp_day.format(currentTime));
	}

	// ���� ���� ���ڷ� �޾Ƽ� �ش� ������ ��¥�� ���
	private int calc_number_of_month(javax.swing.JComboBox<String> year,
			javax.swing.JComboBox<String> month) {
		int day = 0;
		String tmp_year = (String) year.getSelectedItem();
		int tmpi_year = Integer.parseInt(tmp_year);
		String tmp_month = (String) month.getSelectedItem();
		int tmpi_month = Integer.parseInt(tmp_month);

		// 2��
		if (tmpi_month == 2) {
			// ����
			if ((tmpi_year % 400) == 0 || (tmpi_year % 4) == 0
					&& (tmpi_year % 100) != 0) {
				day = 29;
			} else {
				day = 28;
			}
		}
		// 7������
		else if (tmpi_month < 8) {
			if (tmpi_month % 2 == 1) {
				day = 31;
			} else {
				day = 30;
			}
		}
		// 8�� ����
		else {
			if (tmpi_month % 2 == 0) {
				day = 31;
			} else {
				day = 30;
			}
		}
		return day;
	}
}
