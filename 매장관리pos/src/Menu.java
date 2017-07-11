

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
	private String menu_name;// �޴��̸�
	private String menu_price;// �޴� ����

	Menu(String tmpname, String tmpprice) throws MenuRegisterException {
		Setmenuname(tmpname);
		Setmenuprice(tmpprice);
	}

	// �޴� �̸� ����
	public final void Setmenuname(String tmpname) throws MenuRegisterException {
		menu_name = new String(tmpname);
		// �޴����� 10���̳� �ѱ�/����/�������� ����
		Pattern pa = Pattern.compile("^[a-zA-Z��-�R0-9]{1,10}$");
		Matcher ma = pa.matcher(menu_name);
		if (!ma.matches()) {
			throw new MenuRegisterException(
					"�߸��� �޴����Դϴ�.\r\n�޴����� 10���̳� �ѱ�/����/�������� �����մϴ�.(2~10)\r\n");
		}
		if (menu_name.compareTo("����") == 0) {
			throw new MenuRegisterException("������ �޴������� ����Ҽ� �����ϴ�.\r\n");

		}
	}

	// �޴� ���� ����
	public final void Setmenuprice(String tmpprice)
			throws MenuRegisterException {
		menu_price = new String(tmpprice);
		// ������ 4�ڸ� �̳� ����
		Pattern pa = Pattern.compile("^[0-9]{1,4}$");
		Matcher ma = pa.matcher(menu_price);
		if (!ma.matches()) {
			throw new MenuRegisterException(
					"�߸��� �����Դϴ�.\r\n������ 4�� �̳� ���ڸ� �����մϴ�.\r\n");
		}
	}

	// �޴� �̸� ���
	public String getmenuname() {
		return menu_name;
	}

	// �޴� ���� ���
	public String getmenuprice() {
		return menu_price;
	}

	// �޴� �̸� �Է¹޾� ������ Ȯ�� ���� true�� ����
	public boolean Issamemenu(String tmpname) {
		if (tmpname == null) {
			return false;
		} else {
			if (tmpname.trim().compareTo(menu_name) == 0)
				return true;
			else
				return false;
		}
	}

}
