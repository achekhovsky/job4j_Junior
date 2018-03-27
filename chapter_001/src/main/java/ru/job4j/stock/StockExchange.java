package ru.job4j.stock;

import java.util.ArrayList;

/**
 * @author chekhovsky
 * @version 0.1
 * @since 0.1
 */
public class StockExchange {
	private ArrayList<Glass> glasses;

	public StockExchange() {
		this.glasses = new ArrayList<>();
	}

	/**
	 * Принятие заявки. Помещает (удаляет) заявку в нужный стакан.
	 * Если стакана, указанного в заявке не существует, то он создается. 
	 * @param acceptingOrder - принимаемая заявка
	 * @return true если заявка принята
	 */
	public boolean acceptOrder(Order acceptingOrder) {
		Glass selectedGlass = this.checkStockExchange(acceptingOrder.book);
		boolean success = false;
		if (acceptingOrder.getType() == Order.TYPE_ADD) {
			if (selectedGlass.add(acceptingOrder)) {
				success = true;
			}
		} else if (acceptingOrder.getType() == Order.TYPE_DELETE) {
			success = selectedGlass.delete(acceptingOrder);
		}
		return success;
	}

	/**
	 * Выводит в System.out строковое представление стакана.
	 * @param glassName - имя стакана, который необходимо вывести
	 */
	public void viewGlass(String glassName) {
		System.out.println(this.findGlass(glassName));
	}

	/**
	 * Проверяет наличие стакана, указанного в заявке в системе.
	 * Если стакан с указанным именем не найден, то он создается.
	 * @param glassName - имя стакана
	 * @return найденный (созданный) стакан
	 */
	private Glass checkStockExchange(String glassName) {
		Glass selectedGlass = this.findGlass(glassName);
		if (selectedGlass == null) {
			selectedGlass = new Glass(glassName);
			glasses.add(selectedGlass);
		}
		return selectedGlass;
	}

	/**
	 * Поиск стакана в системе по имени
	 * @param checkedName - имя стакана
	 * @return найденный стакан или null если его нет в системе
	 */
	private Glass findGlass(String checkedName) {
		Glass result = null;
		for (Glass glass : glasses) {
			if (glass.issuer.equals(checkedName)) {
				result = glass;
				break;
			}
		}
		return result;
	}
}
