package ru.job4j.jdbc.tracker;

/**
 * This class describes the behavior of item
 * @author achekhovsky
 * @version 1.0
 */
public class Item {
    private String id;
    private String name;
    private String description;
    private long created;
    private String[] comments;

    /**
     * Default constructor
     */
    public Item() {
        this.id = "";
        this.name = "";
        this.description = "";
        this.created = System.currentTimeMillis();
        this.comments = new String[1];
    }

    /**
     * Constructor which init name and description
     * @param name - the item name
     * @param description - the item description
     */
    public Item(String name, String description) {
        this.id = "";
        this.name = name;
        this.description = description;
        this.created = System.currentTimeMillis();
        this.comments = new String[1];
    }

    /**
     * Getting the id of the item
     * @return - the item id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getting the name of the item
     * @return - the item name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getting the description of the item
     * @return - the item description
     */
    public String getDescription() {

        return this.description;
    }

    /**
     * Getting the created date of the item
     * @return - the item created date
     */
    public long getCreated() {
        return this.created;
    }

    /**
     * Set id of the item
     * @param id1 - new value for id
     */
    public void setId(String id1) {
        this.id = id1;
    }
    /**
     * Set name of the item
     * @param name1 - new value for name
     */
    public void setName(String name1) {
        this.name = name1;
    }
    /**
     * Set description of the item
     * @param description1 - new value for description
     */
    public void setDescription(String description1) {
        this.description = description1;
    }
    
    public void setCreated(long created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return this.id + " | " + this.name + " | " + this.description + " | " + this.created;
    }
}
