package ru.job4j.stock;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Стакан системы трейдинга. Хранит заявки двух типов(покупка, продажа).
 * 
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 */
public class Glass {
	public final String issuer;
	private TreeSet<Order> purchase, sale;

	public Glass(String issuer) {
		this.issuer = issuer;
		purchase = new TreeSet<>();
		sale = new TreeSet<>();
	}

	/**
	 * Добавление заявки в стакан в зависимости от ее действия. После добавления
	 * заявки происходит проверка добавленной заявки на совместимость с другими
	 * заявками в стакане.
	 * 
	 * @param newOrd - новая заявка
	 * @return true если заявка добавлена
	 */
	public boolean add(Order newOrd) {
		boolean result = false;
		if (newOrd.getAction() == Order.ACTION_ASK) {
			result = purchase.add(newOrd);
		} else if (newOrd.getAction() == Order.ACTION_BID) {
			result = sale.add(newOrd);
		}
		if (result) {
			this.makeDeals(newOrd);
		}
		return result;
	}

	/**
	 * Удаление заявки из стакана.
	 * 
	 * @param removedOrd
	 *            - заявка на удаление
	 * @return true если заявка удалена
	 */
	public boolean delete(Order removedOrd) {
		boolean result = false;
		if (removedOrd.getAction() == Order.ACTION_ASK) {
			result = this.reduceOrder(purchase, removedOrd);
		} else if (removedOrd.getAction() == Order.ACTION_BID) {
			result = this.reduceOrder(sale, removedOrd);
		}
		return result;
	}

	/**
	 * Проверка поступившей заявки на совместимость с заявками в стакане.
	 * При наличии совместимых заявок происходит изменение объема новой заявки и
	 * совместимой заявки. Процесс происходит в цикле до тех пор пока не будет
	 * проверено все дерево или объем новой заявки не станет равным 0. В этом случае
	 * она удаляется из стакана.
	 * 
	 * @param newOrd - новая заявка
	 */
	private void makeDeals(Order newOrd) {
		TreeSet<Order> ownerTree = null; 
		TreeSet<Order> checkedTree = null;
		if (newOrd.getAction() == Order.ACTION_ASK) {
			ownerTree = this.purchase;
			checkedTree = this.sale;
		} else {
			ownerTree = this.sale;
			checkedTree = this.purchase;			
		}

		for (Order checked : checkedTree) {
			if (newOrd.getVolume() > 0) {
				if ((newOrd.getAction() == Order.ACTION_ASK && newOrd.price >= checked.price)
						|| (newOrd.getAction() == Order.ACTION_BID && newOrd.price <= checked.price)) {
					if (newOrd.getVolume() > checked.getVolume()) {
						newOrd.setVolume(newOrd.getVolume() - checked.getVolume());
						checkedTree.remove(checked);
					} else if (newOrd.getVolume() < checked.getVolume()) {
						checked.setVolume(checked.getVolume() - newOrd.getVolume());
						ownerTree.remove(newOrd);
					} else {
						checkedTree.remove(checked);
						ownerTree.remove(newOrd);
					}
				}
			} else {
				ownerTree.remove(newOrd);
			}
		}
	}

	/**
	 * Уменьшает объем заявки в указаном дереве. В случае когда объем превышает или
	 * равен объему заявки на уменьшение она удаляется из дерева. Поиск заявки
	 * производится по цене. Уменьшение объема производится в первой найденной
	 * заявке.
	 * 
	 * @param tree - дерево для поиска
	 * @param reduced - заявка на уменьшение объема
	 * @return true если объем заявки в дереве изменился
	 */
	private boolean reduceOrder(TreeSet<Order> tree, Order reduced) {
		boolean result = false;
		for (Order checkedOrder : tree) {
			if (checkedOrder.price == reduced.price) {
				if (checkedOrder.getVolume() > reduced.getVolume()) {
					checkedOrder.setVolume(checkedOrder.getVolume() - reduced.getVolume());
					result = true;
				} else {
					tree.remove(checkedOrder);
					result = true;
				}
				break;
			}
		}
		return result;
	}

	/**
	 * Возвращение в строковом представлении стакана. Строки состоят из трех
	 * столбцов "покупка цена продажа" Если в стакане существуют заявки с одинаковой
	 * ценой, то в строку выводится суммарный объем этих заявок.
	 * 
	 * @param deals - дерево для представления в стоку
	 * @return строковое представление стакана
	 */
	private String glassInfo(TreeSet<Order> deals) {
		StringBuilder bldr = new StringBuilder();
		if (deals.size() > 0) {
			Iterator<Order> itr = deals.iterator();
			Order order = itr.next();
			double price = order.price;
			int vol = order.getVolume();
			while (itr.hasNext()) {
				order = itr.next();
				if (price != order.price) {
					bldr.append(String.format(this.getFormat(order), vol, price));
					vol = order.getVolume();
					price = order.price;
				} else {
					price = order.price;
					vol += order.getVolume();
				}
			}
			bldr.append(String.format(this.getFormat(order), vol, price));
		}
		return bldr.toString();
	}

	/**
	 * Формат вывода строки. В случае если заявка на покупку то она выводится в 1й и
	 * 2й столбец, иначе во 2й и 3й.
	 * 
	 * @param order - заявка для определения формата вывода
	 * @return формат вывода строки
	 */
	private String getFormat(Order order) {
		return order.getAction() == Order.ACTION_ASK ? "   %1$s     %2$s\n" : "         %2$s     %1$s\n";
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder bldr = new StringBuilder();
		bldr.append("Покупка  Цена  Продажа\n");
		bldr.append(this.glassInfo(this.purchase));
		bldr.append(this.glassInfo(this.sale));
		return bldr.toString();
	}
}
