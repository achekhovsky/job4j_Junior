function findEmptyInputs() {
	let isEmtpy = false;
	let inputs = document.getElementsByTagName("input");
	for (let i = 0; i < inputs.length; i++) {
		if(inputs[i].getAttribute("type") == "text" && inputs[i].value == "") {
			isEmtpy = true;
			break;
		}
	}
	return isEmtpy;
}

function getFormData(formId) {
	var obj = "";
	var jArray = $("#" + formId).serializeArray();
	$.each(jArray, function() {
		obj += this.name + "=" + this.value + "&";
	});
	return obj;
}

function getPaidPlaces(url) {
	let forSend = "getAction=getPlaces";
	let checkedInp = document.querySelector("input[type=radio]:enabled:checked");
	$.ajax({
		type:"POST",
		url: url,
		data: forSend,
		success: function (data) {	
			let places = JSON.parse(data);
			if(places instanceof Array) {
				places.forEach(function(item){
					let plc = document.querySelector("input[type=radio][value='" + item['place']  + "']");
					if(plc != null && plc != undefined) {
						plc.checked = !item['paid'];
						plc.disabled = true;					
					}
				});
			}
	if(checkedInp != null && checkedInp != undefined) {
		checkedInp.checked = "true";	
	} 
		}
	});
		return false;
}

function sendPickedPlace(url) {
	let checkedInp = document.querySelector("input[type=radio]:enabled:checked");
	if (checkedInp != null && checkedInp != undefined) {
		let forSend = "getAction=forPaid&" + "place="  + checkedInp.value;
		$.ajax({
			type:"POST",
			url: url,
			data: forSend,
			success: function (data) {	
				document.location.href = "payment.html";
			}
		});
	} else {
		return false;	
	}
}

function getPickedPlace(url) {
		let forSend = "getAction=getPlaceForPaid";
		$.ajax({
			type:"POST",
			url: url,
			data: forSend,
			success: function (data) {	
				let response = JSON.parse(data);
				let place = (response['place']).toString();
				document.getElementById("pageTitle").textContent = "Вы выбрали ряд " + place[0]  + " место " +  place[1] + ", Сумма : " + response['price']  + " рублей."
			}
		});
		return false;	
}


function paid(url, formId) {
	if(!findEmptyInputs()) {
		let forSend = getFormData(formId) + "getAction=paid"; 
		$.ajax({
			type:"POST",
			url: url,
			data: forSend,
			success: function () {	
				document.location.href = "index.html";
			}
		});
	}
	return false;	
}
