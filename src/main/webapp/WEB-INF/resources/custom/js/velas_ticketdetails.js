/**
 * Purpose: Interact with the html pages and create communication between server
 * and client side 
 * Author : Fenya Tlala 
 * Client: Velaphanda
 * Created Date:20-08-2017
 * Last Date Modified:15-05-2018
 */



// Take selected used part numbers only -->
function checkUsedPartNumbers() {

	var checkboxes = document.getElementsByName('selectedItem');
	var checkboxesChecked = [];
	// loop over them all
	for (var i = 0; i < checkboxes.length; i++) {
		// And stick the checked ones onto an array...
		if (checkboxes[i].checked) {
			checkboxesChecked.push(checkboxes[i].value);
		}
	}
	document.getElementById("usedPartNumbers").value = checkboxesChecked;

}

// Validate action and used part numbers -->
$(document)
		.ready(
				function() {

					$('#updateResolved')
							.validate(
									{
										errorClass : "error-class",
										validClass : "valid-class",
										rules : {

											actionTaken : {
												required : true
											},
											usedPartNumbers : {
												required : {
													depends : function(element) {
														if ($("#actionTaken")
																.val() == "Replaced Part/Toner") {
															return true;
														} else {
															return false;
														}
													}
												}
											},
											comment : {
												max : 150,
												required : {
													depends : function(element) {
														if ($("#actionTaken")
																.val() == "Cleared Paper Jam"
																|| $(
																		"#actionTaken")
																		.val() == "Installed Drivers"
																|| $(
																		"#actionTaken")
																		.val() == "Configured Drivers"
																|| $(
																		"#actionTaken")
																		.val() == "Configured Printer"
																|| $(
																		"#actionTaken")
																		.val() == "User Error"
																|| $(
																		"#actionTaken")
																		.val() == "No fault Found"
																|| $(
																		"#actionTaken")
																		.val() == "Cleaned Mirrors"
																|| $(
																		"#actionTaken")
																		.val() == "Cleaned Laser Unit"
																|| $(
																		"#actionTaken")
																		.val() == "Cleaned Charge Rollers"
																|| $(
																		"#actionTaken")
																		.val() == "Cleaned ADF Class"
																|| $(
																		"#actionTaken")
																		.val() == "Cleaned Rollers")

														{
															return true;
														} else {
															return false;
														}
													}
												}
											}

										},

										messages : {
											usedPartNumbers : 'Used part numbers is required check boot or site stock for used part numbers',
											actionTaken : 'Action taken is required and can not be empty',
											comment : 'Please provide comments on what solution you provided',
										// technicianUserName : 'Technician is
										// required and can not be empty'

										}
									});
				});

$(document)
		.ready(
				function() {
					$(
							'#updateResolved,#ticketAcknowledgedReassign,#ticketEscalatedReassign,#ticketOpenReassign,#ticketTakenEscalate,#ticketTakenAwaiting,#ticketTakenEscalate,#ticketTakenResolve')

							.bootstrapValidator(
									{
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											orderNum : {
												validators : {
													notEmpty : {
														message : 'Order number is required and cant not be empty'
													}
												}
											},
											escalatedTo : {
												validators : {
													notEmpty : {
														message : 'Manager is required and cant not be empty'
													}
												}
											},
											colourReading : {
												validators : {
													notEmpty : {
														message : 'Colour reading is required and can not be empty'
													}
												}
											},
											monoReading : {
												validators : {
													notEmpty : {
														message : 'Mono reading is required and can not be empty'
													}
												}
											},
											comments : {
												validators : {
													stringLength : {
														min : 10,
														max : 500,
														message : 'Please enter at least 10 characters and not more than 500'
													},
													notEmpty : {
														message : 'Comment is required and can not be empty'
															
													}
												}
											},
											technicianUserName : {
												validators : {
													notEmpty : {
														message : 'Technician is required and can not be empty'
													}
												}
											},
											bridgedReason : {
												validators : {
													notEmpty : {
														message : 'Reason is required and can not be empty'
													}
												}
											},
											usedPartNumbers : {
												validators : {
													notEmpty : {
														message : 'Used part numbers is required check boot or site stock for used part numbers'
													}
												}
											},
											reopenReason: {
												validators : {
													notEmpty : {
														message : 'Reason for Re-Opening ticket is required and cant not be empty'
													}
												}
											}

										}
									});
				});

$(document).on("focusin", "#usedPartNumbers", function() {
	$(this).prop('readonly', true);
});

$(document).on("focusout", "#usedPartNumbers", function() {
	$(this).prop('readonly', false);
});

$(document).ready(function() {
	$('#updataTckt').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			status : {
				validators : {
					notEmpty : {
						message : 'Status is required and cant not be empty'
					}
				}
			}

		}
	});
});

// Select Action Taken -->
$("#actionTaken").on(
		'change',
		function() {

			if ($(this).val() == "Replaced Part/Toner") {
				$('textarea[name="usedPartNumbers"]:enabled').attr('disabled',
						false);
				$('#usedPartNumbers').attr('disabled', false);
				console.log($(this).val());
			} else if ($(this).val() == ""
					|| $(this).val() == "Cleared Paper Jam"
					|| $(this).val() == "Installed Drivers"
					|| $(this).val() == "Configured Drivers"
					|| $(this).val() == "Configured Printer"
					|| $(this).val() == "User Error"
					|| $(this).val() == "No fault Found"
					|| $(this).val() == "Cleaned Mirrors"
					|| $(this).val() == "Cleaned Laser Unit"
					|| $(this).val() == "Cleaned Charge Rollers"
					|| $(this).val() == "Cleaned ADF Class"
					|| $(this).val() == "Cleaned Rollers"
					|| $(this).val() == "Cleaned Waste Toner Bottle"
					|| $(this).val() == "Replaced Stipples") {
				$('textarea[name="usedPartNumbers"]:enabled').attr('disabled',
						false);
				$('#usedPartNumbers').attr('disabled', false);
				console.log($(this).val(), "see");
			}
		});

// Select hideComent before -->
function Faulty(val) {

	var element = document.getElementById('hideUsedPartNumbersHidding');
	if (val == 'pick a Part or Toner options' || val == 'Replaced Part/Toner')
		element.style.display = 'block';
	else
		element.style.display = 'none';
	console.log('Hide heading if Replaced Part/Toner is not selected ');

	var element = document.getElementById('hideIfIsNotPartToner');
	if (val == 'pick a Part or Toner options' || val == 'Replaced Part/Toner')
		element.style.display = 'block';
	else
		element.style.display = 'none';
	console.log('Toner or Part:  For getting list of Parts or Toners');

	var element = document.getElementById('hideComent');
	if (val == 'pick a fualty options' || val == 'Cleared Paper Jam'
			|| val == 'Installed Drivers' || val == 'Configured Drivers'
			|| val == 'Configured Printer' || val == 'User Error'
			|| val == 'No fault Found' || val == 'Cleaned Mirrors'
			|| val == 'Cleaned Laser Unit' || val == 'Cleaned Charge Rollers'
			|| val == 'Cleaned ADF Class' || val == 'Cleaned Rollers' || val == 'Firmware Upgrade'
			|| val == 'Cleaned Waste Toner Bottle' || val == 'Replaced Stipples')
		
		element.style.display = 'block';
	else
		element.style.display = 'none';
	console
			.log('Other Action Taken, beside Replace Part or Toner:  Add Comment on Solution Modal ticket, is resolved remove comment');

	var element = document.getElementById('actionTakenSubmit');
	if (val == 'pick a show submit' || val == 'Cleared Paper Jam'
			|| val == 'Installed Drivers' || val == 'Configured Drivers'
			|| val == 'Configured Printer' || val == 'User Error'
			|| val == 'No fault Found' || val == 'Replaced Part/Toner'
			|| val == 'Cleaned Mirrors' || val == 'Cleaned Laser Unit'
			|| val == 'Cleaned Charge Rollers' || val == 'Cleaned ADF Class'
			|| val == 'Cleaned Rollers' || val == "Firmware Upgrade"
			|| val == 'Cleaned Waste Toner Bottle' || val == 'Replaced Stipples')
		element.style.display = 'block';
	else
		element.style.display = 'none';
	console
			.log('Submit Hidden:  Hide submit buttons if nothing was selected from Action Taken');

	var element = document.getElementById('hideMonoAndColour');
	if (val == 'pick a show submit' || val == 'Cleared Paper Jam'
			|| val == 'Installed Drivers' || val == 'Configured Drivers'
			|| val == 'Configured Printer' || val == 'User Error'
			|| val == 'No fault Found' || val == 'Replaced Part/Toner'
			|| val == 'Cleaned Mirrors' || val == 'Cleaned Laser Unit'
			|| val == 'Cleaned Charge Rollers' || val == 'Cleaned ADF Class'
			|| val == 'Cleaned Rollers' || val == "Firmware Upgrade"
			|| val == 'Cleaned Waste Toner Bottle' || val == 'Replaced Stipples')
		
		element.style.display = 'block';
	else
		element.style.display = 'none';
	console
			.log('hide Mono And Colour:  Hide Mono and Colour Reading if action ');
}

function BootStockChecked(){
	
	  $("#bootStockItems").removeClass("displayNone");
	  $("#bootStockItems").addClass("showDIV");
	
		//Make sure siteStockItems is not visible
	  $("#siteStockItems").removeClass("showDIV");
	  $("#siteStockItems").addClass("displayNone");
	  
	}

	function SiteStockChecked(){
	
	  $("#siteStockItems").removeClass("displayNone");
	  $("#siteStockItems").addClass("showDIV");
	
	  //Make sure bootStockItems is not visible
	  $("#bootStockItems").removeClass("showDIV");
    $("#bootStockItems").addClass("displayNone");
}
	


// Select Open before showing comment-->
function CheckStatus(val) {
	var element = document.getElementById('comments');
	if (val == 'pick a customer' || val == 'Open')
		element.style.display = 'block';
	else
		element.style.display = 'none';
}

// Accept alphanumeric characters only -->
function isNumber(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}

// Make all Serials numbers UpperCase -->
function upperCaseF(a) {
	setTimeout(function() {
		a.value = a.value.toUpperCase();
	}, 1);
}

// hide the all of the element class usedPartNumbersDetails
$(".usedPartNumbersDetails").each(function(i) {
	if ($('input[name$=usedPartNumbers][value=""]', this).length == 1) {
		$(this).hide();
		console.log('hiding toner or part');
	} else {
		$(this).show();
		console.log('showing toner or part');
	}
});

//Compare available mono reading with updated mono reading
function compareMonoReading(element, availableMonoReading){				
		
	if (availableMonoReading < element.value ){		
		console.log("True,",element.value + " is more than " + availableMonoReading);
			}
	if (element.value == ''){
		alert("Mono reading can not be empty");
		console.log(element.value);
		element.value = null;
	}		
	else if(availableMonoReading > element.value) {
		alert("Entered mono reading "+ element.value +", must be greater than available mono reading "+ availableMonoReading +"");
		element.value = null;		
	}
}
//End Compare available mono reading with updated mono reading

//Compare available colour reading with updated colour reading
function compareColourReading(element, availableColourReading){				
		
	if (availableColourReading < element.value ){		
		console.log("True,",element.value + " is more than " + availableColourReading);
	}
	if (element.value == ''){
		alert("Colour reading cant not be empty");
	}
	else if(availableColourReading > element.value  ) {
		alert("Entered colour reading "+ element.value +", must be greater than available colour reading "+ availableColourReading +"");
		element.value = null;		
	}
}
//End Compare available colour reading with updated colour reading	


//--------------end User, Admin and Manager Tabs-----------------
//hide tabs of actions on page load

//Do not show tabs, up until user click on action
$('.mTicketOpenReassign').hide();
//end hide of Take tabs 

//Do not shw tabs for status that is Acknowledged, up until user click on action
$('.mTicketAcknowledgedReassign').hide();
//end hide of Acknowledged tabs 

//Do not shw tabs for status that is Taken, up until user click on action 
$('.mTicketTakenAwaiting').hide();
$('.mTicketTakenEscalate').hide();
$('.mTicketTakenResolve').hide();
$('.mTicketEscalatedReassign').hide();

//end hide of taken tabs 

//Do not shw tabs for status that is Resolved, up until user click on action
$('.mTicketReopenResolved').hide();
//end hide of Resolved tabs

//end hide of tabs actions on page load	

//Open Ticket
$("#mTicketOpenReassignLink").click(function(){
     $('.nav-tabs li').removeClass('active');
     $('.mTicketOpenReassign').addClass('active');
     $('.mTicketOpenReassign').show();
     $('.tab-pane').removeClass('in active');
     $('.tab-content div#mTicketOpenReassign').addClass('in active');
     console.log("Status is Acknowledged: Ticket can be reassign");
 });//end Open ticket

//Acknowledged ticket
$("#mTicketAcknowledgedReassignLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.mTicketAcknowledgedReassign').addClass('active');
	 $('.mTicketAcknowledgedReassign').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#mTicketAcknowledgedReassign').addClass('in active');
	 console.log("Status is Acknowledged: Ticket can be reassign");
});//end Acknowledged Tickit

//Taken Tickets
$("#mTicketTakenAwaitingLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.mTicketTakenAwaiting').addClass('active');
	 $('.mTicketTakenAwaiting').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#mTicketTakenAwaiting').addClass('in active');
	 console.log("Status is Taken: Ticket can be on Awaiting Spares if no order is recived");
});	
$("#mTicketTakenEscalateLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.mTicketTakenEscalate').addClass('active');
	 $('.mTicketTakenEscalate').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#mTicketTakenEscalate').addClass('in active');
	 console.log("Status is Taken: Ticket can be Escalated");
});

$("#mTicketTakenResolveLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.mTicketTakenResolve').addClass('active');
	 $('.mTicketTakenResolve').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#mTicketTakenResolve').addClass('in active');
	 console.log("Status is Taken: Ticket can be Resolve");
});

//end Taken Tickets


$("#mTicketEscalatedReassignLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.mTicketEscalatedReassign').addClass('active');
	 $('.mTicketEscalatedReassign').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#mTicketEscalatedReassign').addClass('in active');
	 console.log("Status is Taken: Ticket can be Resolved");
});
$("#mTicketEscalatedResolvedDetailsLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.mTicketEscalatedResolvedDetails').addClass('active');
	 $('.mTicketEscalatedResolvedDetails').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#mTicketEscalatedResolvedDetails').addClass('in active');
	 console.log("Status is Escalated: Ticket can be Escalated");
});	

$("#mTicketSLABridgedResolvedLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.mTicketSLABridgedResolved').addClass('active');
	 $('.mTicketSLABridgedResolved').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#mTicketSLABridgedResolved').addClass('in active');
	 console.log("Status is SLABridged: Ticket can be Resolved");
});

$("#mTicketReopenResolvedLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.mTicketReopenResolved').addClass('active');
	 $('.mTicketReopenResolved').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#mTicketReopenResolved').addClass('in active');
	 console.log("Status is Resolved: Ticket can be Re-Open");
});	
//end Reopen Resolved tickets
//--------------end User, Admin and Manager Tabs-----------------


//--------------Techinician Tabs--------------------------
//hide tabs of actions on page load

//Do not shw tabs for status that is Take, up until user click on action
$('.tTakeTicket').hide();
//end hide of Take tabs 

//Do not shw tabs for status that is Acknowledged, up until user click on action
$('.tAcknowledgedTicket').hide();
//end hide of Acknowledged tabs 

//Do not shw tabs for status that is Taken, up until user click on action 
$('.tTicketTakenAwaitingSpares').hide();
$('.tTicketTakenEscalate').hide();
$('.tTicketTakenResolve').hide();
//end hide of taken tabs 

//Do not shw tabs for status that is Awaitng Spares, up until user click on action
$('.tTicketAwaitingSparesEscalate').hide();
$('.tTicketAwaitingSparesResolve').hide();
//end hide of awaiting Spares tabs

//Do not shw tabs for status that is SLA Bridge, up until user click on action
 $('.tTicketSLABridgedEscalate').hide();
 $('.tTicketSLABridgedResolved').hide();
//end hide of SLA Bridge tabs

//end hide of tabs actions on page load

//Acknowledged Ticket
$("#tAcknowledgedTicketLink").click(function(){
     $('.nav-tabs li').removeClass('active');
     $('.tAcknowledgedTicket').addClass('active');
     $('.tAcknowledgedTicket').show();
     $('.tab-pane').removeClass('in active');
     $('.tab-content div#tAcknowledgedTicket').addClass('in active');
     console.log("Acknowledged Ticket Active");
 });//end Acknowledged

//Take ticket
$("#tTakeTicketLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.tTakeTicket').addClass('active');
	 $('.tTakeTicket').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#tTakeTicket').addClass('in active');
	 console.log("Take Ticket Active");
});//end Take Tickit

//Taken Tickets
$("#tTicketTakenAwaitingSparesLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.tTicketTakenAwaitingSpares').addClass('active');
	 $('.tTicketTakenAwaitingSpares').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#tTicketTakenAwaitingSpares').addClass('in active');
	 console.log("Status is Taken: Ticket can be on Awaiting Spares if no order is recived");
});	
$("#tTicketTakenEscalateLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.tTicketTakenEscalate').addClass('active');
	 $('.tTicketTakenEscalate').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#tTicketTakenEscalate').addClass('in active');
	 console.log("Status is Taken: Ticket can be Escalated");
});	
$("#tTicketTakenResolveLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.tTicketTakenResolve').addClass('active');
	 $('.tTicketTakenResolve').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#tTicketTakenResolve').addClass('in active');
	 console.log("Status is Taken: Ticket can be Resolved");
});//end Taken tickets

//Awaitng Spares tickets
$("#tTicketAwaitingSparesEscalateLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.tTicketAwaitingSparesEscalate').addClass('active');
	 $('.tTicketAwaitingSparesEscalate').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#tTicketAwaitingSparesEscalate').addClass('in active');
	 console.log("Status is Awaiting Spare: Ticket can be Escalated");
});	
$("#tTicketAwaitingSparesResolveLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.tTicketAwaitingSparesResolve').addClass('active');
	 $('.tTicketAwaitingSparesResolve').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#tTicketAwaitingSparesResolve').addClass('in active');
	 console.log("Status is Awaiting Spare: Resolved");
});//end Awaitng Spares tickets

//SLA Bridged Tickets
$("#tTicketSLABridgedEscalateLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.tTicketSLABridgedEscalate').addClass('active');
	 $('.tTicketSLABridgedEscalate').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#tTicketSLABridgedEscalate').addClass('in active');
	 console.log("Status is SLA Bridge: Ticket can be Ticket must can be Escalated");
});	
$("#tTicketSLABridgedResolvedLink").click(function(){
	 $('.nav-tabs li').removeClass('active');
	 $('.tTicketSLABridgedResolved').addClass('active');
	 $('.tTicketSLABridgedResolved').show();
	 $('.tab-pane').removeClass('in active');
	 $('.tab-content div#tTicketSLABridgedResolved').addClass('in active');
	 console.log("Status is SLA Bridge: Ticket must be Resolved");
});//end SLA Bridged Tickets

//--------------Techinician Tabs--------------------------