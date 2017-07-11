

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
	String[] field = {"자리NO.","예약현황"};
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
		setTitle("예약화면-사용자모드");
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(null);
		Date now = new Date();
		model = new SpinnerDateModel(now,null,now,Calendar.DAY_OF_WEEK);
		spinner = new JSpinner(model);

		rm_jComboBox_year.setBounds(70, 45, 70, 30);
		getContentPane().add(rm_jComboBox_year);
		JLabel rm_jLabel1 = new JLabel("년");
		rm_jLabel1.setBounds(140,45,70,30);
		getContentPane().add(rm_jLabel1);
		rm_jComboBox_month.setBounds(150, 45, 70, 30);
		getContentPane().add(rm_jComboBox_month);
		JLabel rm_jLabel2 = new JLabel("월");
		rm_jLabel2.setBounds(220,45,70,30);
		getContentPane().add(rm_jLabel2);
		rm_jComboBox_day.setBounds(230, 45, 70, 30);
		getContentPane().add(rm_jComboBox_day);
		JLabel rm_jLabel3 = new JLabel("일");
		rm_jLabel3.setBounds(300,45,70,30);
		getContentPane().add(rm_jLabel3);
	
		JLabel dateLabel = new JLabel("날짜:");
		dateLabel.setBounds(30, 45, 450, 25);
		getContentPane().add(dateLabel);
		
		JButton ressrchBtn = new JButton("예약조회");
		ressrchBtn.addActionListener(new searchreservation());
		ressrchBtn.setBounds(350, 45, 150, 25);
		getContentPane().add(ressrchBtn);
		
		model2 = new DefaultTableModel(field, 0);
		table = new JTable(model2);
		JScrollPane pane = new JScrollPane(table);
		getContentPane().add(pane);
		pane.setBounds(40,80,500,200);
		
		JLabel nameLabel = new JLabel("예약자명");
		nameLabel.setBounds(30, 300, 100, 25);
		getContentPane().add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(99, 300, 100, 25);
		getContentPane().add(nameField);
		
		JLabel phoneLabel = new JLabel("연락처 : ");
		phoneLabel.setBounds(300, 300, 100, 25);
		getContentPane().add(phoneLabel);
		
		phoneField = new JTextField();
		phoneField.setBounds(350, 300, 100, 25);
		getContentPane().add(phoneField);
		
		JButton doresBtn = new JButton("예약하기");
		doresBtn.addActionListener(new Doreservation());
		doresBtn.setBounds(45, 350, 150, 30);
		getContentPane().add(doresBtn);
		
		JButton canresBtn = new JButton("예약취소");
		canresBtn.addActionListener(new Canselreservation());
		canresBtn.setBounds(200, 350, 150, 30);
		getContentPane().add(canresBtn);
	}
	//예약조회 버튼
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
				JOptionPane.showMessageDialog(null, "조회결과를 읽어오지 못합니다.");
			}
		}
	}
	//예약하기 버튼
	private  class Doreservation implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				//현재 날짜를 받아옵니다. 현재 날짜보다 과거이면 예외처리를 발생시킵니다.
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
					throw new Exception("현재시간보다 과거입니다.");
				int[] items = table.getSelectedRows();
				String name = nameField.getText();
				String phone = phoneField.getText();
			
					if (items.length == 0)
						throw new Exception("예약을 선택해 주세요.");
					for (int i : items) {
						String status = (String) model2.getValueAt(i, 1);
						String seats = (String) model2.getValueAt(i, 0);
						if(status.equals("Reserved"))
							throw new Exception("예약이 불가능 합니다.");
						if (name.length() == 0 || phone.length() ==0)
							throw new Exception("예약자명과 연락처를 입력하세요");
						Data data = new Data(month,day,year,seats,name, phone);
						Data.updateInfo(data,month,day,year,seats);
						JOptionPane.showMessageDialog(null,"예약이 성공되었습니다.");
					}
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, err.getMessage());
			}
		}
	}
	//예약취소 버튼 여기서 예외처리를 발생합니다.
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
					throw new Exception("현재시간보다 과거입니다.");
				if (items.length == 0)
					throw new Exception("예약을 선택해 주세요.");
				for (int i : items) {
					String status = (String) model2.getValueAt(i, 1);
					String seats = (String) model2.getValueAt(i, 0);
					
					if(status.equals("--"))
						throw new Exception("취소가 불가능 합니다.");
					if (name.length() == 0 || phone.length() ==0)
						throw new Exception("취소하려면 예약자명과 연락처를 입력하세요");
					
					Data data = new Data();
					tmp =(int) Data.updatecanselInfo(data,month,day,year,name,phone);
					if(tmp == -1 || tmp ==0)
					{
						JOptionPane.showMessageDialog(null, "정보가 틀립니다.");
						tmp=0;
					}
					else{
						JOptionPane.showMessageDialog(null,"취소가 성공되었습니다.");
						tmp=0;
					}
				}
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, err.getMessage());
			}
		}
	}
	//연도 세팅
	private void initvalue() {
		// 날짜 표시
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
				"yyyy년 MM월 dd일");
		Date currentTime = new Date();
		Datestring = String.format(("%s"),
				mSimpleDateFormat.format(currentTime));
		

		// 콤보박스에 연도 세팅
		for (int i = 1900; i < 2100; i++) {
			String tmp = String.format(("%d"), i);
			rm_jComboBox_year.addItem(tmp);
		}
		SimpleDateFormat tmp_year = new SimpleDateFormat("yyyy");
		rm_jComboBox_year.setSelectedItem(tmp_year.format(currentTime));
		// 콤보박스에 월 세팅
		for (int i = 1; i < 13; i++) {
			String tmp = String.format(("%02d"), i);
			rm_jComboBox_month.addItem(tmp);
		}
		SimpleDateFormat tmp_month = new SimpleDateFormat("MM");
		rm_jComboBox_month.setSelectedItem(tmp_month.format(currentTime));
		// 콤보박스에 일 세팅
		for (int i = 1; i <= calc_number_of_month(rm_jComboBox_year,
				rm_jComboBox_month); i++) {
			String tmp = String.format(("%02d"), i);
			rm_jComboBox_day.addItem(tmp);
		}
		SimpleDateFormat tmp_day = new SimpleDateFormat("dd");
		rm_jComboBox_day.setSelectedItem(tmp_day.format(currentTime));
	}

	// 연과 월을 인자로 받아서 해당 연월에 날짜수 계산
	private int calc_number_of_month(javax.swing.JComboBox<String> year,
			javax.swing.JComboBox<String> month) {
		int day = 0;
		String tmp_year = (String) year.getSelectedItem();
		int tmpi_year = Integer.parseInt(tmp_year);
		String tmp_month = (String) month.getSelectedItem();
		int tmpi_month = Integer.parseInt(tmp_month);

		// 2월
		if (tmpi_month == 2) {
			// 윤달
			if ((tmpi_year % 400) == 0 || (tmpi_year % 4) == 0
					&& (tmpi_year % 100) != 0) {
				day = 29;
			} else {
				day = 28;
			}
		}
		// 7월이하
		else if (tmpi_month < 8) {
			if (tmpi_month % 2 == 1) {
				day = 31;
			} else {
				day = 30;
			}
		}
		// 8월 부터
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
