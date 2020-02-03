/*
 * SWT Preload
 */

package lm;

public class MainSniff {
	public static void main(String[] args) {
		try {
			Sniffmanager window = new Sniffmanager();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
