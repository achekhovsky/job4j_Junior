package ru.job4j.generics;

/**
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public abstract class Base {
    private final String id;

    protected Base(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;			
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Base other = (Base) obj;
		if (id == null) {
			if (other.id != null) {
				return false;				
			}
		} else if (!id.equals(other.id)) {
			return false;			
		}
		return true;
	}
    
    
}
