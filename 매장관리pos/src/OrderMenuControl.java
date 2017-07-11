

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class OrderMenuControl {
	private ArrayList<Menu> menuList;
	private ArrayList<Order> orderList;

	OrderMenuControl() {
		menuList = new ArrayList<Menu>();
		orderList = new ArrayList<Order>();

		// ���� ����
		File input = new File("menu.txt");
		Scanner scanner = null;
		String menu = "";
		String price = "";
		String date = "";
		int num_of_menu = 0;
		
		try {
			scanner = new Scanner(input);
			System.out.println(scanner.nextLine());
			if (scanner.hasNextLine()) {
				// �޴��� ���� ����
				String tmptmp = scanner.nextLine();
				// text file�� ���Ƿ� �޸������� ���������� utf-8�� ��Ÿ���� byte��
				// text file�տ� ��. �װ��� ���Ƿ� ��������
				if (tmptmp.startsWith("\uFEFF")) {
					tmptmp = tmptmp.substring(1);
				}

				num_of_menu = Integer.parseInt(tmptmp);

				// �޴� �б�
				for (int i = 0; i < num_of_menu; i++) {
					menu = scanner.nextLine();
					price = scanner.nextLine();
					Menu temp = new Menu(menu, price);
					menuList.add(temp);
				}
				scanner.nextLine();
				// ���� ���� �б�
				while (scanner.hasNext()) {
					date = scanner.nextLine();
					menu = scanner.nextLine();
					price = scanner.nextLine();
					Order temp = new Order(date, menu, price);
					orderList.add(temp);
				}
				scanner.close();
			}
		} catch (NoSuchElementException e) {
			// ���������� ���� �� ���� ó��
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (MenuRegisterException e) {
			// TODO Auto-generated catch block
			// ����ϴٰ� ���� ������ �� ���������� ������ ������ ó����
			String tmp = String.format(
					"���� �߻� �� �� - menu : %s price %s\r\n�����޼��� %s", menu, price,
					e.getMessage());
			JOptionPane.showMessageDialog(null, tmp, "ERROR!!",
					JOptionPane.PLAIN_MESSAGE);
			// ���� �ݱ�
			scanner.close();
		}
	}

	// �޴� ���, ��Ͻ� ���� ����� ���ܹ߻�, �ߺ��� ���� �߻�
	public synchronized void registerMenu(String tmpmenu, String tmpprice)
			throws MenuRegisterException {
		int size_of_list = menuList.size();
		tmpmenu = tmpmenu.trim();
		tmpprice = tmpprice.trim();
		// �ߺ��� �޴� �ִ��� �˻�
		for (int i = 0; i < size_of_list; i++) {
			if (menuList.get(i).Issamemenu(tmpmenu)) {
				throw new MenuRegisterException(
						"�ߺ��޴��Դϴ�.\r\n������ �˻� �Ŀ� �����մϴ�.\r\n");

			}
		}
		// �ߺ��� �޴� �ƴϸ� ���� ���
		Menu temp = new Menu(tmpmenu, tmpprice);
		menuList.add(temp);
		writelist();
		return;
	}

	// �޴� ����, ������ ���� ����� ���ܹ߻�, �ߺ��� ���� �߻�
	public synchronized void editMenu(String tmpmenu, String tmpprice)
			throws MenuRegisterException {
		int size_of_list = menuList.size();
		tmpmenu = tmpmenu.trim();
		tmpprice = tmpprice.trim();
		// �޴� �ִ��� �˻�
		for (int i = 0; i < size_of_list; i++) {
			if (menuList.get(i).Issamemenu(tmpmenu)) {
				// �޴� ����
				menuList.get(i).Setmenuprice(tmpprice);
				writelist();
				return;
			}
		}
		throw new MenuRegisterException("�޴��� ���� ���� �ʽ��ϴ�.\r\n");
	}

	// �޴� ���� ������ false ����
	public synchronized boolean deleteMenu(String tmpmenu) {
		int size_of_list = menuList.size();
		tmpmenu = tmpmenu.trim();
		// �޴� �ִ��� �˻�
		for (int i = 0; i < size_of_list; i++) {
			if (menuList.get(i).Issamemenu(tmpmenu)) {

				menuList.remove(i);
				// ���Ͽ� ����
				writelist();
				return true;
			}
		}
		return false;
	}

	// �޴� �̸� �޾� ���� ��� ������ ���� �߻�
	public String getPrice(String menunametmp) throws MenuRegisterException {
		int size_of_list = menuList.size();
		menunametmp = menunametmp.trim();
		for (int i = 0; i < size_of_list; i++) {
			if (menuList.get(i).Issamemenu(menunametmp)) {
				return menuList.get(i).getmenuprice();
			}
		}
		throw new MenuRegisterException("���� �޴� �Դϴ�.\r\n");
	}

	// ���� ��Ȳ ���
	public void registerOrder(String tmpdate, String tmpmenu, String tmpprice) {
		Order temp = new Order(tmpdate, tmpmenu, tmpprice);
		orderList.add(temp);
		writelist();
	}

	// ���� ���� ������ ���� inner class
	class SalesInformation {
		public String temp_menu;
		public String temp_price;
		public int temp_num_of_order = 0;
	}

	// �Ⱓ�� �Է� �޾� �ش��Ͽ� �ش��ϴ� ���� ���� ���
	public String getSalesInformation(String from_date, String to_date) {
		String tmp = "";

		ArrayList<SalesInformation> salesinfo = new ArrayList<SalesInformation>();

		int size_of_list = orderList.size();
		for (int i = 0; i < size_of_list; i++) {
			// �ش� �Ⱓ�� �´� �ֹ����� Ȯ��
			if (orderList.get(i).Isvaliddate(from_date, to_date)) {
				int tempsize = salesinfo.size();
				boolean findsame = false;
				// ���� �ֹ� �־����� Ȯ���ϰ� ã���� �ֹ��� �ø�
				for (int j = 0; j < tempsize; j++) {
					if (orderList.get(i).getMenu()
							.compareTo(salesinfo.get(j).temp_menu) == 0) {
						if (orderList.get(i).getPrice()
								.compareTo(salesinfo.get(j).temp_price) == 0) {
							salesinfo.get(j).temp_num_of_order++;
							findsame = true;
							break;
						}
					}
				}
				// ã�� ������ ��
				if (!findsame) {
					SalesInformation tempsf = new SalesInformation();
					tempsf.temp_menu = orderList.get(i).getMenu();
					tempsf.temp_price = orderList.get(i).getPrice();
					tempsf.temp_num_of_order = 1;
					salesinfo.add(tempsf);
				}
			}
		}

		// �ش�Ⱓ �Ǹ� ���� String�� �ֱ�
		size_of_list = salesinfo.size();
		tmp = String.format("%-10s%-5s%-10s\r\n++++++++++++++++++++++\r\n",
				"ǰ��", "����", "����ݾ�");
		int sumofnum = 0;
		int sumofsales = 0;
		for (int i = 0; i < size_of_list; i++) {
			int number = salesinfo.get(i).temp_num_of_order;
			int totalSum = number
					* Integer.parseInt(salesinfo.get(i).temp_price);
			String totalSumS = NumberFormat.getNumberInstance(Locale.US)
					.format(totalSum);
			tmp = String.format("%s%-10s%-5d%-10s\r\n", tmp,
					salesinfo.get(i).temp_menu, number, totalSumS);

			sumofnum += number;
			sumofsales += totalSum;
		}
		String sumofsalesS = NumberFormat.getNumberInstance(Locale.US).format(
				sumofsales);
		tmp = String.format("%s++++++++++++++++++++++\r\n%-10s%-5d%-10s\r\n",
				tmp, "�հ�", sumofnum, sumofsalesS);
		return tmp;
	}

	private void writelist() {
		int size_of_list = menuList.size();
		OutputStream out;
		try {
			out = new FileOutputStream("menu.txt", false);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

			bw.write(String.format("%d\r\n", size_of_list));
			for (int i = 0; i < size_of_list; i++) {
				String temp = String.format("%s\r\n%s\r\n", menuList.get(i)
						.getmenuname(), menuList.get(i).getmenuprice());
				bw.write(temp);
			}
			bw.write("@@@\r\n");
			size_of_list = orderList.size();
			for (int i = 0; i < size_of_list; i++) {
				String temp = String.format("%s\r\n%s\r\n%s\r\n", orderList
						.get(i).getDate(), orderList.get(i).getMenu(),
						orderList.get(i).getPrice());
				bw.write(temp);
			}

			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "������ ���� �����ϴ�.", "ERROR!!",
					JOptionPane.PLAIN_MESSAGE);
			System.exit(1);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "������ ���� �����ϴ�.", "ERROR!!",
					JOptionPane.PLAIN_MESSAGE);
			System.exit(1);
		}

	}

	public ArrayList<String> getMenuList() {
		int size = menuList.size();
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			temp.add(menuList.get(i).getmenuname());
		}
		return temp;
	}

}
