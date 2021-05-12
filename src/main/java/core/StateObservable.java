package core;

public interface StateObservable {
	
	void addObserver(StateObserver obsever);
	void removeObserver(StateObserver observer);
	
}
