package ru.job4j.jdbc.htmlparser;

import java.util.ArrayList;
import java.util.List;

public class Vacancies {
	private List<Vacancy> vcs;

	public Vacancies() {
		vcs = new ArrayList<Vacancy>();
	}
	
	public List<Vacancy> getVcs() {
		return vcs;
	}

	public void setVcs(List<Vacancy> vcs) {
		this.vcs = vcs;
	}
	
	public void addToVacancies(Vacancy vac) {
		this.vcs.add(vac);
	}
	
	public void addAllVacancies(Vacancies vacancies) {
		this.vcs.addAll(vacancies.getVcs());
	}
	
	public void clear() {
		this.vcs.clear();
	}
	
	public static class Vacancy {
		private int id;
		private String url;
		private String descritpion;
		private String publicationDate;
		
		public Vacancy() {
			
		}
		
		public Vacancy(String url, String description, String publicDate) {
			this.url = url;
			this.descritpion = description;
			this.publicationDate = publicDate;
		}
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
		public String getDescritpion() {
			return descritpion;
		}

		public void setDescritpion(String descritpion) {
			this.descritpion = descritpion;
		}
		
		public String getPublicationDate() {
			return publicationDate;
		}

		public void setPublicationDate(String publicationDate) {
			this.publicationDate = publicationDate;
		}
	}
}
