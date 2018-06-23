package ru.job4j.jdbc.htmlparser;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Parsing of vacancies for specified templates
 * @author achekhovsky
 * @version 1.0
 */
public class VParser {
	private static Logger log = LogManager.getLogger(ParserMain.class.getName());
	
	private String currentPage;
	private boolean isFirstRun = true;
	private final String pagesPattern;
	private final String vacancyPattern;
	private final String datePtrn; 
	
	public VParser(Properties prp) {
		this.pagesPattern = prp.getProperty("pages_pattern", "");
		this.vacancyPattern = prp.getProperty("vacancy_pattern", "");
		this.datePtrn = prp.getProperty("publication_date_pattern");
	}
	
	/**
	 * Parsing vacancies on the specified page
	 * @param url - specified page
	 * @return found vacancies
	 */
	public Vacancies parseVacanciesFromPage(String url) {
		this.currentPage = url;
		this.isFirstRun = false;
		String sltr = String.format("tr:has(a:matches(%s))", this.vacancyPattern);
		Vacancies vcs = new Vacancies();
		Elements vac = this.parseElements(url, sltr);
			for (int i = 0; i < vac.size(); i++) {
				Element a =  vac.get(i).selectFirst(String.format("a:matches(%s)", this.vacancyPattern));
				vcs.addToVacancies(new Vacancies.Vacancy(a.attr("href"), a.ownText(), this.parseTextByRegex(vac.get(i), Pattern.compile(this.datePtrn))));
			}			
		return vcs;
	}
	
	/**
	 * Parsing vacancies on the specified pages.
	 * @param startPage - first page
	 * @param maxPages - number of pages for parsing (number of jumps to the next page)
	 * @return found vacancies
	 */
	public Vacancies parseVacanciesFromPages(String startPage, int maxPages) {
		this.isFirstRun = false;
		int count = 0;
		Vacancies vcs = this.parseVacanciesFromPage(startPage);
		while (this.nextPage() && count++ <= maxPages) {
			vcs.addAllVacancies(this.parseVacanciesFromPage(this.currentPage));
		}
		return vcs;
	}
	
	/**
	 * Parses the text in the specified element by using a regular expression.
	 * @param elem - specified element
	 * @param ptrn - regular expression
	 * @return the text contained in the element (in the first element if there are several), 
	 * matching the supplied regular expression
	 */
	private String parseTextByRegex(Element elem, Pattern ptrn) {
		Elements elems = elem.getElementsMatchingOwnText(ptrn); 
		return  !elems.isEmpty() ? elems.first().text() : "";
	}
	
	/**
	 * @return true if parser is launched for the first time
	 */
	public boolean isFirstRun() {
		return this.isFirstRun;
	}
	
	/**
	 * Selects all links to other pages
	 * @param url - page from which links will be selected
	 * @return links to other page
	 */
	private String[] findPages(String url) {
		String sltr = String.format("[href~=%s]", this.pagesPattern);
		Elements pages = this.parseElements(url, sltr);
		String[] pgs = new String[pages.size()];
			for (int i = 0; i < pages.size(); i++) {
				pgs[i] = pages.get(i).attr("href");
			}
		return pgs;
	}
	
	/**
	 * Gets the page number (integer) from the link
	 * @param pageUrl - url of the page from which you need to get the number
	 * @return page number
	 */
	private int getPageNumber(String pageUrl) {
		return Integer.parseInt(pageUrl.replace(this.pagesPattern, ""));
	}
	
	/**
	 * Assigns the "currentPage" field to the next page url. 
	 * The next page is selected by the number in the link. If the number of the current page url
	 * is less than the number in the checked url, then the current page is assigned a new url
	 * @return true if a new url is assigned to the field
	 */
	private boolean nextPage() {
		boolean result = false;
		for (String page : this.findPages(this.currentPage)) {
			if (this.getPageNumber(this.currentPage) < this.getPageNumber(page)) {
				this.currentPage = page;
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Parse elements on the page by the specified template
	 * @param url 
	 * @param csspattern
	 * @return selected elements
	 */
	private Elements parseElements(String url, String csspattern) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			log.warn("VParser::parseElements ", e);
		}
		return doc.select(csspattern);
	}
}
