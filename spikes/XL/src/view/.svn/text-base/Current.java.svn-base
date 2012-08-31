package view;

import java.util.Observable;
import xl.Address;

public class Current extends Observable{
	private Address address = new Address(0,0);

	public void set(Address address){
		this.address = address;
		setChanged();
		notifyObservers();
	}

	public Address getAddress(){
		return address;
	}
}
