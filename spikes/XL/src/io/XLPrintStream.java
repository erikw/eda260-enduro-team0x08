package io;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Set;
import java.util.Map.Entry;

import sheet.Slot;
import xl.Address;


public class XLPrintStream extends PrintStream {
    public XLPrintStream(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    public void save(Set<Entry<Address, Slot>> entrySet) {
        for (Entry<Address, Slot> entry : entrySet) {
            print(entry.getKey());
            print('=');
            println(entry.getValue());
        }
        flush();
        close();
    }
}
