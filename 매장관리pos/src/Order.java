

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
	private String date;// ��¥
	private String menu;// �޴�
	private String price;// ����

	// ������
	Order(String tmpdate, String tmpmenu, String tmpprice) {
		date = new String(tmpdate);
		menu = new String(tmpmenu);
		price = new String(tmpprice);
	}

	public String getDate() {
		return date;
	}

	public String getMenu() {
		return menu;
	}

	public String getPrice() {
		return price;
	}

	// ��¥�� from���� to���� �� �ش��ϴ��� Ȯ������, �ش��Ѵٸ� true return
	public boolean Isvaliddate(String from, String to) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date0;
		try {
			date0 = sdf.parse(date);// ���� ��¥
			Date date1 = sdf.parse(from);// ~����
			Date date2 = sdf.parse(to);// ~����

			if (date0.compareTo(date1) >= 0 && date0.compareTo(date2) <= 0) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return false;
		}

	}

}
