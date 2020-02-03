/*
 * Implementation of NativeKeyListener that forwards keypresses to the parent,
 * but can be turned on/off when you don't want the application to listen.
 */

package lm.systems;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import lm.Sniffmanager;

public class KeyListener implements NativeKeyListener {
	
	private Sniffmanager parentWindow;
	private boolean active = false;

	public KeyListener() {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		logger.setUseParentHandlers(false);
	}
	
	public void nativeKeyPressed(NativeKeyEvent e) {
//		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
//		System.out.println("Key Pressed: " + e.getRawCode());
		if (active) {
			int c = e.getRawCode();
			if (c >= 97 && c <= 105 || c >= 112 && c <= 119 || c == 122 || c == 123) {
				parentWindow.keyPressed(e.getRawCode());
			}
		}

//		if (e.getRawCode() == 122) {
//			try {
//				GlobalScreen.unregisterNativeHook();
//			} catch (NativeHookException e1) {
//				e1.printStackTrace();
//			}
//		}
	}
	
	public void setParentWindow(Sniffmanager parentWindow) {
		this.parentWindow = parentWindow;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
	}
	public void nativeKeyTyped(NativeKeyEvent e) {
	}
}