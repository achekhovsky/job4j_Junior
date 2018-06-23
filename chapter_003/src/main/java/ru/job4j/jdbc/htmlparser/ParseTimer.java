package ru.job4j.jdbc.htmlparser;

import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParseTimer extends Timer {
	private static Logger log = LogManager.getLogger(ParseTimer.class.getName());
	private Properties prp;
	
	public ParseTimer(Properties p) {
		super(String.format("Timer created at %tb", new Date()));
		this.prp = p;
	}
	
	/* 
	 * @see java.util.Timer#schedule(java.util.TimerTask, long, long)
	 */
	public void start() {
		VParser parser = new VParser(this.prp);
		VacanciesStore vs = new VacanciesStore(prp);
		this.schedule(new TimerTask() {
			@Override
			public void run() {
				if (parser.isFirstRun()) {
					try {
						vs.saveAll(parser.parseVacanciesFromPage(prp.getProperty("first_page")));
					} catch (Exception e) {
						log.warn("ParseTimer::start ", e);
					}	
				} else {
					try {
						vs.saveAll(parser.parseVacanciesFromPages(prp.getProperty("first_page"), 
								Integer.parseInt(prp.getProperty("max_pages_for_time"))));
					} catch (Exception e) {
						log.warn("ParseTimer::init ", e);
					}	
				}
			}
		}, 0, Integer.parseInt(this.prp.getProperty("period", "10000")));
	}
}
