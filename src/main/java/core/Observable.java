package core;

public interface Observable {
	
	void addObserver(Observer obsever);
	void removeObserver(Observer observer);
	
}
