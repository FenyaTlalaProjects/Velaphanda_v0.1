/**
 * Purpose: Interact with the html pages and create communication between server
 * and client side 
 * Author : Fenya Tlala 
 * Client: Velaphanda
 * Created Date:21-01-2018
 * Last Date Modified:21-01-2018
 */

//Compare start, end and installation date between each other-->
 $("#startDate, #endDate").datepicker();

$("#endDate").change(function () {

var startDate = document.getElementById("startDate").value;
var endDate = document.getElementById("endDate").value;

if ((Date.parse(endDate) < Date.parse(startDate))) {
    alert("Leave End Date should not be a past date");
    document.getElementById("endDate").value = "";
} 

});

$(document).ready(function() {
	$('#startDatePicker').datepicker({
		format : "yyyy-mm-dd",
			//startDate: 'd0',
	        autoclose: true
	});
});

$(document).ready(function() {
	$('#endDatePicker').datepicker({
	format : "yyyy-mm-dd",
	//startDate: 'd0',
    autoclose: true
 });
});

$(document).ready(function() {
	$('#makeLeave').bootstrapValidator({
	   //framework: 'bootstrap',
    icon: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
        fields: {
        	leaveType: {
                validators: {
                    notEmpty: {
                        message: 'Leave type is required,and can not be empty'
                    }
                }
            },
            technicianUserName:{
                validators: {
                    notEmpty: {
                        message: 'Technician name is required,and can not be empty'
                    }
                }
            },
            startDate: {
                validators: {
                    notEmpty: {
                        message: 'Leave start date is required'
                    },
                    date: {
                        format: 'YYYY-MM-DD',
                        //max: 'endDate',
                        message: 'Leave start date is not a valid'
                    }
                }
            },
            endDate: {
                validators: {
                    notEmpty: {
                        message: 'Leave end date is required'
                    },
                    date: {
                        format: 'YYYY-MM-DD',
                        //min: 'startDate',
                        message: 'Leave end date is not a valid'
                    }
                }
            },
            contactNumber : {
				validators : {
					notEmpty : {
						message : 'Please enter 10 digits for contact number'
					},
					phone : {
						country : 'US',
						message : 'Please enter 10 digits for contact number'
					},
					regexp: {
						
						regexp: /^0[0-9].*$/,
						message :'Tellphone number must start with 0 (Zero)'
					}
				}
			},
			
			address: {
                validators: {
				stringLength : {
							min : 3,
					}, 					
                    notEmpty: {
                        message: 'Address is required, and can not be empty'
                    }
                }
            }
        }
    })        
}); 

	function isNumber(evt) {
	    evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	        return false;
	    }
	    return true;
	}