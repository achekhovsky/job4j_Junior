/**
 * Checks all inputs tags with type text on the page and if the tag is empty,
 * adds after it the inscription contained in the title attribute. 
 */
function findEmptyInputs() {
	let isEmtpy = false;
	let inputs = document.getElementsByTagName("input");
	for (let i = 0; i < inputs.length; i++) {
		if(inputs[i].getAttribute("type") == "text" && inputs[i].value == "") {
			createMessageUnder(inputs[i], inputs[i].getAttribute("title"));
			isEmtpy = true;
		}
	}
	return isEmtpy;
}

/**
 * Creates a message with the specified text after the specified element. And
 * remove it after 4 seconds.
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
	var row = document.createElement("tr");
	for(let i = 0; i < table.tHead.rows[0].cells.length; i++) {
		row.insertAdjacentElement("beforeEnd", document.createElement("td"));
	} 
	for(let i = 0; i < values.length && i < row.cells.length; i++) {
		row.cells[i].textContent = values[i]; 	
	}
	if(table.tBodies.length == 0) {
		table.insertAdjacentElement("beforeEnd", document.createElement("tbody"));
	} 
	table.tBodies[0].insertAdjacentElement("beforeEnd", row);

}


function getFormDataInJSOn(formId) {
	var obj = {};
	var jArray = $("#" + formId).serializeArray();
	$.each(jArray, function() {
	    if (obj[this.name]) {
		    if (!obj[this.name].push) {
		    obj[this.name] = [obj[this.name]];
		}
		obj[this.name].push(this.value || '');
		} else {
			obj[this.name] = this.value || '';
		}
	});
    return obj;
}	

function sendAjaxjQuery(url, formId) {
	if(!findEmptyInputs()) {
	let data = JSON.stringify(getFormDataInJSOn(formId));
	$.ajax({
		type:"POST",
		url: url,
		data: data,
		contentType: "application/json",
		success: function () {	
			let usr = JSON.parse(data);
			addRow(tbl, usr.usrName, usr.usrLName, usr.usrDesc);
		}
		//error: function (errorThrown) {alert("Error" + errorThrown);},		
	});} else {
		return false;
	}
}

function sendXhr(url, formId) {
	if (!findEmptyInputs()) {
		let dataJS = getFormDataInJSOn(formId);
		let xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200) {
				addRow(tbl, dataJS.usrName, dataJS.usrLName, dataJS.usrDesc);
			}
		}
		xhr.open("POST", url, true);
		xhr.setRequestHeader('Content-type', 'application/json');
		xhr.send(JSON.stringify(dataJS));
	} else {
		return false;
	}
}
