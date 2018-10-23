/**
 * Checks all inputs tags with type text on the page and if the tag is empty,
 * adds after it the inscription contained in the title attribute. Cancels an 
 * event if it is canceled, without stopping further propagation of this event.
 */
function findEmptyInputs(event) {
	let isEmtpy = false;
	let inputs = document.getElementsByTagName("input");
	for (let i = 0; i < inputs.length; i++) {
		if(inputs[i].getAttribute("type") == "text" && inputs[i].value == "") {
			createMessageUnder(inputs[i], inputs[i].getAttribute("title"));
			event.preventDefault();
			isEmtpy = true;
		}
	}
	console.log(isEmtpy);
	return isEmtpy;
}

/**
 * Creates a message with the specified text after the specified element. 
 * And remove it after 4 seconds.
 */
function createMessageUnder(elem, text) {
	let message = document.createElement('div');
	message.style.cssText = "position:absolute; color: red; font-size: 12px";
	message.innerHTML = text;

	elem.insertAdjacentElement("afterend", message); 
	setTimeout(function() {
	let prnt = message.parentElement;
	prnt.removeChild(message);
	}, 4000);
}

function addRow(table, ...values) {
	let row = table.tBodies[0].rows[0].cloneNode(true);
	for(cell in row.cells) {
		cell.textContent = "";
	} 
	for(let i = 0; i < values.length || i < row.cells.length; i++) {
		row.cells[i].textContent = values[i]; 	
	}
	table.tBodies[0].insertAdjacentElement("beforeEnd", row);
}
