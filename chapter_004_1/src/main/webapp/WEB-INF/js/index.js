function selectOption(selectId, optionValue) {
	let options = document.getElementById(selectId).getElementsByTagName("option");
	for(let i = 0; i < options.length; i++) {
		if(options.item(i).value == optionValue) {
			options.item(i).selected = true;
			break;
		}
	}
}

function appendOptionInSelect(selectId, optionName) {
	let select = document.getElementById(selectId);
	if(select != null) {
		let opt = document.createElement("option");
		opt.name = optionName;
		opt.value = optionName;
		opt.textContent = optionName;
		select.appendChild(opt);
	}	
}

function sendAjaxjQuery(url, country) {
	let forSend = "getCitiesForThisCountry=" + country;
	$.ajax({
		type:"POST",
		url: url,
		data: forSend,
		success: function (data) {	
			let cities = JSON.parse(data);
			if(cities instanceof Array) {
				if(document.getElementById("cities") != null) {
					document.getElementById("cities").innerHTML = ""; 
				}
				cities.forEach(function(item){appendOptionInSelect("cities", item);});
			}
		}
	});
		return false;
}

function onChangeSelect(event) {
	if(event.currentTarget.tagName == "SELECT") {
		sendAjaxjQuery('add', event.currentTarget.options[event.currentTarget.selectedIndex].value);
	}
}

/**
 * Checks all inputs tags with type text on the page and if the tag is empty,
 * adds after it the inscription contained in the title attribute. 
 */
function findEmptyInputs() {
	let isEmtpy = true;
	let inputs = document.getElementsByTagName("input");
	for (let i = 0; i < inputs.length; i++) {
		if((inputs[i].getAttribute("type") == "text" || inputs[i].getAttribute("type") == "email") && inputs[i].value == "") {
			inputs[i].classList.add("is-invalid");			
			isEmtpy = false;
		} else {
			inputs[i].classList.remove("is-invalid");			
		}
	}
	alert("SUBMIT - " + isEmtpy);
	return isEmtpy;
}
