/**
 * Purpose: Interact with the html on update device page and create communication between server
 * and client side 
 * Author : Fenya Tlala 
 * Client : Velaphanda
 * Date Created: 19-01-2018
 * Date Modified:19-01-2018
 */

/*---Create datalist to populate search---*/

//Get the <datalist> and <input> elements.
var dataList = document.getElementById('json-datalist');
var input = document.getElementById('ajax');

//Create a new XMLHttpRequest.
var request = new XMLHttpRequest();

//Handle state changes for the request.
request.onreadystatechange = function(response) {
	if (request.readyState === 4) {
		if (request.status === 200) {
			// Parse the JSON
			var jsonOptions = JSON.parse(request.responseText);

			// Loop over the JSON array.
			jsonOptions.forEach(function(item) {
				// Create a new <option> element.
				var option = document.createElement('option');
				// Set the value using the item in the JSON array.
				option.value = item;
				// Add the <option> element to the <datalist>.
				var appendChild = "Lets See";
				dataList.appendChild(option);
			});

			// Update the placeholder text.
			input.placeholder = "e.g. datalist";
		} else {
			// An error occured :(
			input.placeholder = "Couldn't load datalist options :(";
		}
	}
};

//Update the placeholder text.
var input = "Loading options";
input.placeholder = "Loading options...";
console.log("What do we have here : ", input.placeholder);
console.log("Mine : ", input);

/*---End Create datalist to populate search---*/
