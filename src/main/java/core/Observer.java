package core;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Set;

public interface Observer {
	
//	void notifyMouseMoved(Point location);
//	void notifyMousePressed(MouseEvent event);
//	void notifyMouseReleased(MouseEvent event);
//
//	void notifyRightPress(Point location);
//	void notifyRightRelease(Point location);
//
//	void notifyLeftPress(Point location);
//	void notifyLeftRelease(Point location);
//
//	void notifyKeysPressed(boolean[] keyCodes);
//	void notifyCharacterKeyPressed(Set<Character> keys);
	
	void notifyStateChange(int newStateIndex);
}
