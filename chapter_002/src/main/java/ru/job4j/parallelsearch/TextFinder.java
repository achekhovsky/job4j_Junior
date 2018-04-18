package ru.job4j.parallelsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import net.jcip.annotations.ThreadSafe;

/**
 * Search text in specified file
 * @author achekhovsky
 * @version 0.1
 */
@ThreadSafe
public class TextFinder {
	private final String charset;
	private final String searchText;
	
	public TextFinder(String text, String charset) {
		this.searchText = text;
		this.charset = charset;
	}
	
	/**
	 * Search text in specified file
	 * @param file - the specified path to file
	 * @return true if file found
	 */
	public boolean searchText(Path file) {
		boolean result = false;
		BufferedReader br = null;
		try {
			br = Files.newBufferedReader(file, Charset.forName(this.charset));
			String nextLine = br.readLine();
			while (nextLine != null) {
				if (nextLine.contains(this.searchText)) {
					result = true;
					break;
				}
				nextLine = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
