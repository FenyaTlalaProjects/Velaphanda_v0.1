/**

 * Purpose: Interact with the html on update device page and create communication between server
 * and client side 
 * Author : Fenya Tlala 
 * Client : Velaphanda
 * Date Created: 19-01-2018
 * Date Modified:08-04-2018
 */

// If check box for removeAccessory is not checked hide submit button -->
$("#removeAccessory").hide();
$(".chkAccessories").click(function() {

	if ($(this).is(":checked")) {
		debugger;
		$("#removeAccessory").show();
		console.log("Show Remove Button");
		$("#hideUpdateProduct").hide();
	} else {
		$("#removeAccessory").hide();
		$("#hideUpdateProduct").show();
	}
});
// End If check box for removeAccessory is not checked hide submit button -->


/*--  Check if check boxes are checked for adding machine accessories, if checked enable input text --*/
var bridgeUnit = document.getElementById("Bridge Unit");
var finisher = document.getElementById("Finisher");
var bridgeUnit = $("input[type='checkbox'][id='Bridge Unit']");
var finisher = $("input[type='checkbox'][id='Finisher']");

bridgeUnit.on('change', function() {
	finisher.prop('checked', this.checked);
	console.log("Checked: ", finisher, bridgeUnit);
});
finisher.on('change', function() {
	bridgeUnit.prop('checked', this.checked);
	console.log("Checked: ", finisher, bridgeUnit);
});
