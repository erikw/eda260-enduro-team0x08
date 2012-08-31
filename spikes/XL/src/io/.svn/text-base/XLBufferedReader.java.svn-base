package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import expr.ExprParser;

import sheet.Sheet;
import sheet.SlotParser;

import xl.Address;
import xl.CyclicReferenceException;
import xl.XLException;

public class XLBufferedReader extends BufferedReader {
	public XLBufferedReader(String name) throws FileNotFoundException {
		super(new FileReader(name));
	}

	public void load(Sheet sheet) {
		SlotParser sp = new SlotParser(new ExprParser());
		List<Address> addresses = new ArrayList<Address>();
		boolean cyclic = false;
		try {
			Pattern pattern = Pattern.compile("^(.+?)=(.*)$");
			Matcher matcher = pattern.matcher("dummy-text");
			while (ready()) {
				matcher.reset(readLine());
				if (matcher.matches()) {
					Address address = new Address(matcher.group(1));
					sheet.put(address, sp.parse(matcher.group(2)));
					addresses.add(address);
				} else {
					throw new XLException("Syntax error");
				}

			}
			Iterator<Address> itr = addresses.iterator();
			while (itr.hasNext() && !cyclic) {
				cyclic = sheet.isCyclic(itr.next());
			}
		} catch (IllegalArgumentException ex) {
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (cyclic) {
			throw new CyclicReferenceException();
		}

	}
}