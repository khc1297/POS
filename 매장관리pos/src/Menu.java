

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
	private String menu_name;// ¸Ş´ºÀÌ¸§
	private String menu_price;// ¸Ş´º °¡°İ

	Menu(String tmpname, String tmpprice) throws MenuRegisterException {
		Setmenuname(tmpname);
		Setmenuprice(tmpprice);
	}

	// ¸Ş´º ÀÌ¸§ ¼³Á¤
	public final void Setmenuname(String tmpname) throws MenuRegisterException {
		menu_name = new String(tmpname);
		// ¸Ş´º¸íÀº 10ÀÚÀÌ³» ÇÑ±Û/¿µ¹®/¼ıÀÚÁ¶ÇÕ °¡´É
		Pattern pa = Pattern.compile("^[a-zA-Z°¡-ÆR0-9]{1,10}$");
		Matcher ma = pa.matcher(menu_name);
		if (!ma.matches()) {
			throw new MenuRegisterException(
					"Àß¸øµÈ ¸Ş´º¸íÀÔ´Ï´Ù.\r\n¸Ş´º¸íÀº 10ÀÚÀÌ³» ÇÑ±Û/¿µ¹®/¼ıÀÚÁ¶ÇÕ °¡´ÉÇÕ´Ï´Ù.(2~10)\r\n");
		}
		if (menu_name.compareTo("ÄíÆù") == 0) {
			throw new MenuRegisterException("ÄíÆùÀº ¸Ş´º¸íÀ¸·Î »ç¿ëÇÒ¼ö ¾ø½À´Ï´Ù.\r\n");

		}
	}

	// ¸Ş´º °¡°İ ¼³Á¤
	public final void Setmenuprice(String tmpprice)
			throws MenuRegisterException {
		menu_price = new String(tmpprice);
		// °¡°İÀº 4ÀÚ¸® ÀÌ³» ¼ıÀÚ
		Pattern pa = Pattern.compile("^[0-9]{1,4}$");
		Matcher ma = pa.matcher(menu_price);
		if (!ma.matches()) {
			throw new MenuRegisterException(
					"Àß¸øµÈ °¡°İÀÔ´Ï´Ù.\r\n°¡°İÀº 4ÀÚ ÀÌ³» ¼ıÀÚ¸¸ °¡´ÉÇÕ´Ï´Ù.\r\n");
		}
	}

	// ¸Ş´º ÀÌ¸§ ¾ò±â
	public String getmenuname() {
		return menu_name;
	}

	// ¸Ş´º °¡°İ ¾ò±â
	public String getmenuprice() {
		return menu_price;
	}

	// ¸Ş´º ÀÌ¸§ ÀÔ·Â¹Ş¾Æ °°ÀºÁö È®ÀÎ ÇØÁÜ true¸é °°À½
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
