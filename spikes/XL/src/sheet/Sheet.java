package sheet;

import java.util.HashMap;
import expr.Environment;
import expr.ExprParser;


import xl.Address;
import xl.Adjustment;
import xl.CyclicReferenceException;
import xl.NoSlotException;

public class Sheet extends HashMap<Address, Slot> implements Environment {
	private static SlotParser slotParser = new SlotParser(new ExprParser());

	public double value(Object key) {
		Slot slot;
		if ((slot = get(key)) != null) {
			return slot.value(this);
		} else {
			throw new NoSlotException();
		}
	}

	public boolean isCyclic(Address address) {
		boolean cyclic = false;
		if (containsKey(address)) {
			Slot slot = null;
			try {
				slot = get(address);
				BlankSlot bs = new BlankSlot();
				put(address, bs);
				slot.value(this);
			} catch (CyclicReferenceException cre) {
				cyclic = true;
			} finally {
				remove(address);
			}
			if (!cyclic) {
				put(address, slot);
			}
		}
		return cyclic;
	}
	
	public String toString(Address address, Adjustment adjustment) {
		return containsKey(address) ? get(address).toString(this, adjustment) : "";
	}
	
	public String toString(Address address) {
		return containsKey(address) ? get(address).toString() : "";
		
	}
	
	public void put(Address address, String slotString) {
		Slot slot  = slotParser.parse(slotString);
		put(address, slot);
		if (isCyclic(address)) {
			throw new CyclicReferenceException();
		}
	}
}
