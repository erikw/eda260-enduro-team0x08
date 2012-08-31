package xl;

import java.util.Observable;

public class Status extends Observable {
	private String currentStatus = "";

	public void setStatus(String status) {
		currentStatus = status;
		setChanged();
		notifyObservers();
	}

	public String getStatus() {
		return currentStatus;
	}

	public void clear() {
		currentStatus = "";
		setChanged();
		notifyObservers();
	}
}
