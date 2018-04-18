package ru.job4j.parallelsearch;

import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Search text in all files with specified extensions in the
 * specified directory
 * @author achekhovsky
 * @version 0.1
 */
@ThreadSafe
public class ParallelSearch {
    private final String root;
    private final String text;
    private final List<String> exts;
    private volatile boolean executionFlag = true;

    private final Queue<String> files = new LinkedBlockingQueue<>();
    private final List<String> paths = Collections.synchronizedList(new ArrayList<>());

    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    /**
     * Runs two threads, one of which searches files with the specified 
     * extension and adds them to the queue, and the other receives files
     * from this queue and search the specified text in them
     */
    public void init() {
        Thread search = new Thread() {
            @Override
            public void run() {
                try {
					Files.walkFileTree(Paths.get(root), new FileFinder(exts, ParallelSearch.this.files));
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					ParallelSearch.this.executionFlag = false;
				}
            }
        };
        Thread read = new Thread() {
            @Override
            public void run() {
                TextFinder tf = new TextFinder(ParallelSearch.this.text, "cp1251");
                while (ParallelSearch.this.executionFlag || ParallelSearch.this.files.size() > 0) {
                	if (ParallelSearch.this.files.size() > 0) {
                    	Path file = Paths.get(ParallelSearch.this.files.poll());
                    	if (file != null && tf.searchText(file)) {
                    		ParallelSearch.this.paths.add(file.toString());
                    	}                		
                	}
                }
            }
        };
        search.start();
        read.start();
        try {
			read.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

	public List<String>  result() {
        return this.paths;
    }
}
