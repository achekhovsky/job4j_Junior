package ru.job4j.jdbc.tracker;

import java.io.File;
import java.util.Arrays;

/**
 * The main class in which organized interface user interaction
 */
public class StartUI {
    private MenuTracker menu;
    private Input input;
    private Tracker tracker;
    /**
     * Constructor which initializes the tracker and input method
     * @param input - the input method
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public static void main(String[] args) {
    	ConsoleInput ci = new ConsoleInput(); 
        try (Tracker trc = new Tracker(ci.ask("Please enter the file name with the connection parameters:"))) {
        	if ("y".equals(ci.ask("Do you want to execute the initial slq script? (y/n)"))) {
        		trc.getConn().executeSqlScript(new File(ci.ask("Please enter the script name: ")), ";");
        	}
        	new StartUI(new ConsoleInput(), trc).init();
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * The method of organizing the output,input and processing of data
     */
    public void init() {
        this.menu = new MenuTracker(this.input, this.tracker);
        /**
         * Add item to the tracker
         */
        this.menu.addToMenu(new BaseAction(0, "Add new item") {
            @Override
            public void execute(Input input, Tracker tracker) {
                tracker.add(new Item(
                        input.ask("Enter the name: "),
                        input.ask("Enter the description: ")));
            }
        });
        /**
         * Show all tracker's items 
         */
        this.menu.addToMenu(new BaseAction(1, "Show all items") {
            @Override
            public void execute(Input input, Tracker tracker) {
                System.out.println(Arrays.deepToString(tracker.findAll()));
            }
        });
        /**
         * Edit item in the tracker
         */
        this.menu.addToMenu(new BaseAction(2, "Edit item") {
            @Override
            public void execute(Input input, Tracker tracker) {
                Item itm = new Item();
                itm.setId(input.ask("Enter item id: "));
                itm.setName(input.ask("Enter the name: "));
                itm.setDescription(input.ask("Enter the description: "));
                tracker.updateItem(itm);
            }
        });
        /**
         * Delete item from the tracker
         */
        this.menu.addToMenu(new BaseAction(3, "Delete item") {
            @Override
            public void execute(Input input, Tracker tracker) {
                tracker.deleteItem(tracker.findById(input.ask("Enter item id: ")));
            }
        });
        /**
         * Find item by id in the tracker
         */
        this.menu.addToMenu(new BaseAction(4, "Find item by id") {
            @Override
            public void execute(Input input, Tracker tracker) {
                System.out.printf("Finded item - %s " + System.lineSeparator(),
                        tracker.findById(input.ask("Enter item id: "))
                );
            }
        });
        /**
         * Find item by name in the tracker
         */
        this.menu.addToMenu(new BaseAction(5, "Find items by name") {
            @Override
            public void execute(Input input, Tracker tracker) {
                System.out.printf("Finded items - %s " + System.lineSeparator(),
                        Arrays.deepToString(tracker.findByName(input.ask("Enter item name: ")))
                );
            }
        });
        do {
            this.menu.showMenu();
            try {
                this.menu.select(Integer.parseInt(this.input.ask("Select: ", new int[] {0, 1, 2, 3, 4, 5})));
            } catch (MenuOutException moe) {
                System.out.println(moe);
            }
        } while (!"exit".equals(input.ask("Enter 'exit' for exit or any key to continue...")));
    }

}
