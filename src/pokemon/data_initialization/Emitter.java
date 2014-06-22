/*
 * Writes to a file. 
 */

package pokemon.data_initialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

class Emitter {
	private PrintWriter printer;

	public Emitter(File file) {
		try {
			printer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist");
			e.printStackTrace();
		}
	}

	public void close() throws IOException {
		printer.flush();
		printer.close();
	}

	public void emit(Object obj) throws IOException {
		printer.println(obj);
	}

}
