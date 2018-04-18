package ru.job4j.parallelsearch;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Queue;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Search files with specified extensions in specified directory
 * @author achekhovsky
 * @version 0.1
 */
public class FileFinder extends SimpleFileVisitor<Path> {
	private final Queue<String> files;
	private final FileNameExtensionFilter extensions;
	
	
	public FileFinder(List<String> extensions, Queue<String> files) {
		this.extensions = new FileNameExtensionFilter("Files for searsh", 
				extensions.toArray(new String[extensions.size()]));
		this.files = files;
	}

	/* 
	 * If a file with the specified extension was found, it's  added to the queue
	 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
	 */
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (extensions.accept(file.toFile())) {
			this.files.offer(file.toString());
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.err.println(exc);
        return FileVisitResult.CONTINUE;
	}
}
