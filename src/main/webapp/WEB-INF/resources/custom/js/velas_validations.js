/**
 * Purpose: Validations for inputs on client side before sending data to
 * controllers and server. Interact with the HTML pages and create communication between server and client side 
 * Author				: Fenya Tlala 
 * Client				: Velaphanda Trading & * Projects BBB-EE Level 1 
 * Date Created 		: 29-06-2017 
 * Last Date Modified	:16-01-2018
 */

/*-- Validate add and logTicket --*/
$(document)
				.ready(
						function() {
							$('#logTicket')
									.bootstrapValidator(
											{
												feedbackIcons : {
													valid : 'glyphicon glyphicon-ok',
													invalid : 'glyphicon glyphicon-remove',
													validating : 'glyphicon glyphicon-refresh'
												},
												fields : {
													device : {
														validators : {
															stringLength : {
																min : 3,
															},
															notEmpty : {
																message : 'Serial number is required to search and cannot be empty'
															}
														}
													},
													modelNumber : {
														validators : {

															notEmpty : {
																message : 'Model number is required to search and cannot be empty'
															}
														}
													},
													technicianUserName : {
														validators : {
															notEmpty : {
																message : 'Technician is required and cannot be empty'
															}
														}
													},
													priority : {
														validators : {
															notEmpty : {
																message : 'Priority is required and cannot be empty'
															}
														}
													},
													description : {
														validators : {
															stringLength : {
																min : 10,
																max : 100,
																message : 'Please enter at least 10 characters and no more than 100'
															},
															notEmpty : {
																message : 'Description is required and cannot be empty'
															}
														}
													},
													customerName : {
														validators : {
															stringLength : {
																min : 2,
															},
															notEmpty : {
																message : 'Customer name is required and cannot be empty'
															}
														}
													},
													firstName : {
														validators : {
															stringLength : {
																min : 3,
															},
															notEmpty : {
																message : 'First name is required to search and cannot be empty'
															}
														}
													},
													lastName : {
														validators : {
															stringLength : {
																min : 3,
															},
															notEmpty : {
																message : 'Last name is required and cannot be empty'
															}
														}
													},
													currentMonoReading : {
														validators : {
															stringLength : {
																min : 1,
															},
															notEmpty : {
																message : 'Current Colour Reading is required and cannot be empty'
															}
														}
													},
													currentColourReading : {
														validators : {
															stringLength : {
																min : 1,
															},
															notEmpty : {
																message : 'Current Colour Reading is required and cannot be empty'
															}
														}
													},
													contactEmail : {
														validators : {
															notEmpty : {
																message : 'Email address is required and cannot be empty'
															},
															emailAddress : {
																message : 'The email address is not valid'
															}
														}
													},
													contactCellNumber : {
														validators : {
															phone : {
																country : 'US',
																message : 'Please enter 10 digits for cellphone number'
															},
															regexp : {

																regexp : /^0[0-9].*$/,
																message : 'Cellphone number must start with 0 (Zero)'
															}
														}
													},
													contactTelephoneNumber : {
														validators : {
															phone : {
																country : 'US',
																message : 'Please enter 10 digits for telephone number'
															},
															regexp : {

																regexp : /^0[0-9].*$/,
																message : 'Tellphone number must start with 0 (Zero)'
															}
														}
													}
												}
											});
						});
//end log ticket

// validate search for serial number
$(document)
.ready(
		function() {
			$('#searchBylogTicket')
					.bootstrapValidator(
							{
								feedbackIcons : {
									valid : 'glyphicon glyphicon-ok',
									invalid : 'glyphicon glyphicon-remove',
									validating : 'glyphicon glyphicon-refresh'
								},
								fields : {
									serialNumber : {
										validators : {
											stringLength : {
												min : 3,
											},
											notEmpty : {
												message : 'Serial number is required to search and cannot be empty'
											}
										}
									},
								}
							});
		});
//end search for serial number
$(document)
		.ready(
				function() {
					$('#addClient, #updateClient')
							.bootstrapValidator(
									{
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											customerName : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'Customer name is required and cannot be empty'
													}/*,
													regexp : {
														regexp : /^[-_ a-zA-Z0-9]+$/,
														message : 'Customer name can consist of only alphabetical characters'
													}*/
												}
											},
											telephoneNumber : {
												validators : {
													notEmpty : {
														message : 'Please enter 10 digits for tellphone number'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Tellphone number must start with 0 (Zero)'
													},
													phone : {
														country : 'US',
														message : 'Please enter 10 digits for tellphone number'
													}
												}
											},
											emailCompany : {
												validators : {
													notEmpty : {
														message : 'Company email address is required and cannot be empty'
													},
													emailAddress : {
														message : 'The email address is not valid'
													}
												}
											},
											streetNumber : {
												validators : {
													stringLength : {
														min : 1,
													},
													notEmpty : {
														message : 'Street number is required and cannot be empty'
													}
												}
											},
											streetName : {
												validators : {
													stringLength : {
														min : 3,
													},
													notEmpty : {
														message : 'Street name is required and cannot be empty'
													}
												}
											},

											city_town : {
												validators : {
													notEmpty : {
														stringLength : {
															min : 3,
														},
														message : 'City is required and cannot be empty'
													}
												}
											},
											province : {
												validators : {
													notEmpty : {
														message : 'Province is required and cannot be empty'
													}
												}
											},
											zipcode : {
												validators : {
													stringLength : {
														max : 4,
														min : 4,
													},
													notEmpty : {
														message : 'Zipcode is required and cannot be empty'
													}

												}
											},
											faxNumber : {
												validators : {
													phone : {
														country : 'US',
														message : 'Please enter 10 digits for fax number number '
													}
												}
											},
											email : {
												validators : {
													notEmpty : {
														message : 'Email address is required and cannot be empty'
													},
													emailAddress : {
														message : 'The email address is not valid'
													}
												}
											},
											firstName : {
												validators : {
													notEmpty : {
														stringLength : {
															min : 3,
														},
														message : 'First Name is required and cannot be empty'
													},
													regexp : {
														regexp : /^[-_ a-zA-Z]+$/,
														message : 'First Name can consist of only alphabetical characters'
													}
												}
											},
											lastName : {
												validators : {
													notEmpty : {
														stringLength : {
															min : 3,
														},
														message : 'Last Name is required and cannot be empty'
													},
													regexp : {
														regexp : /^[-_ a-zA-Z]+$/,
														message : 'Last Name can consist of only alphabetical characters'
													}
												}
											},
											contactCellNumber : {
												validators : {
													notEmpty : {
														message : 'Please enter 10 digits for cellphone number'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Cellphone number must start with 0 (Zero)'
													},
													phone : {
														country : 'US',
														message : 'Please enter 10 digits for cellphone number'
													}
												}
											},
											contactTelephoneNumber : {
												validators : {
													notEmpty : {
														message : 'Please enter 10 digits for telephone number'
													},
													phone : {
														country : 'US',
														message : 'Please enter 10 digits for telephone number'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Telephone number must start with 0 (Zero)'
													}
												}
											},

											contactEmail : {
												validators : {
													notEmpty : {
														message : 'Email address is required and cannot be empty'
													},
													emailAddress : {
														message : 'The email address is not valid'
													}
												}
											},

											// Contact Person 2(Optional)
											firstName1 : {
												validators : {
													stringLength : {
														min : 3,
													},
													regexp : {
														regexp : /^[-_ a-zA-Z]+$/,
														message : 'First Name can consist of only alphabetical characters  '
													}
												}
											},
											lastName1 : {
												validators : {
													stringLength : {
														min : 3,
													},
													regexp : {
														regexp : /^[-_ a-zA-Z]+$/,
														message : 'Last Name can consist of only alphabetical characters '
													}
												}
											},
											contactCellNumber1 : {
												validators : {
													phone : {
														country : 'US',
														message : 'Please enter 10 digits for cellphone number'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Cellphone number must start with 0 (Zero)'
													}
												}
											},
											contactTelephoneNumber1 : {
												validators : {
													phone : {
														country : 'US',
														message : 'Please enter 10 digits for telephone number'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Tellphone number must start with 0 (Zero)'
													}
												}
											},

											contactEmail1 : {
												validators : {
													emailAddress : {
														message : 'The email address is not valid'
													}
												}
											},
											description : {
												validators : {
													notEmpty : {
														stringLength : {
															min : 4,
														},
														message : 'Comment is required of what was updated and updated to what, this cannot be empty'
													}
												}
											},

										}

									});
				})/*--// End Validate add and update client --*/

/*-- Validate register and update employee --*/
$(document)
		.ready(
				function() {
					$('#addEmployee, #updateEmployee')
							.bootstrapValidator(
									{
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											firstName : {
												validators : {
													stringLength : {
														min : 2,
													},
													notEmpty : {
														message : 'First Name is required and cannot be empty'
													}
												}
											},
											lastName : {
												validators : {
													stringLength : {
														min : 2,
													},
													notEmpty : {
														message : 'Last Name is required and cannot be empty'
													}
												}
											},
											title : {
												validators : {
													notEmpty : {
														message : 'Title is required and cannot be empty'
													}
												}
											},
											gender : {
												validators : {
													notEmpty : {
														message : 'Gender is required and cannot be empty'
													}
												}
											},
											username : {
												validators : {
													notEmpty : {
														stringLength : {
															min : 2,
														},
														message : 'Username is required and cannot be empty'
													}
												}
											},
											password : {
												validators : {
													notEmpty : {
														stringLength : {
															min : 4,
														},
														message : 'Password is required and cannot be empty'
													}
												}
											},											
											cellphoneNumber : {
												validators : {
													notEmpty : {
														message : 'Cellphone number is required and cannot be empty'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Cellphone number must start with 0 (Zero)'
													},
													phone : {
														country : 'US',
														message : 'Please provide a vaild Cellphone Number'
													}

												}
											},
											email : {
												validators : {
													notEmpty : {
														message : 'The email address is required and cannot be empty'
													},
													emailAddress : {
														message : 'The email address is not valid'
													}
												}
											},
											role : {
												validators : {
													notEmpty : {
														message : 'Role is required and cannot be empty'
													}
												}
											},
											cellNumber : {
												validators : {
													notEmpty : {
														message : 'Please enter 10 numbers'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Cellphone number must start with 0 (Zero)'
													},
													phone : {
														country : 'US',
														message : 'Please provide a vaild Cellphone Number'
													}

												}
											},
										}
									});
				});/*--// End Validate register employee --*/

/*Validate Login Page*/
$(document)
		.ready(
				function() {
					$('#loginVali')
							.bootstrapValidator(
									{
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											email : {
												validators : {
													notEmpty : {
														message : 'The email address is required and cannot be empty'
													},
													emailAddress : {
														message : 'The email address is not valid'
													}
												}
											},
											password : {
												validators : {
													stringLength : {
														min : 3,
													},
													notEmpty : {
														message : 'Password is required to login and cannot be empty'
													}
												}
											}

										}
									});
				});
/*End Validate Login Page*/

/* Check gender selection */
function CheckGender(val) {

	var element = document.getElementById('title');

	if (val == 'pick title' || val == 'Mr') {
		document.getElementById('gender').value = 'Male';
	} else if (val == 'pick title' || val == "Miss") {
		document.getElementById('gender').value = 'Female';
	} else if (val == 'pick title' || val == "Mrs") {
		document.getElementById('gender').value = 'Female';
	} else {
		// document.getElementById('gender').value = '';
	}
} /*-- //End  Check gender selection --*/

/*-- Validate add and update device --*/
$(document)
		.ready(
				function() {
					$('#addDevice, #updateDevice')
							.bootstrapValidator(
									{
										icon : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {

											customerName : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'Customer name is required and cannot be empty'
													}
												}
											},
											// Contact Person
											firstName : {
												validators : {
													notEmpty : {
														stringLength : {
															min : 3,
														},
														message : 'First Name is required and cannot be empty'
													},
													regexp : {
														regexp : /^[-_ a-zA-Z]+$/,
														message : 'First Name must only consist of letters'
													}
												}
											},
											lastName : {
												validators : {
													notEmpty : {
														stringLength : {
															min : 3,
														},
														message : 'Last Name is required and cannot be empty'
													},
													regexp : {
														regexp : /^[-_ a-zA-Z]+$/,
														message : 'Last Name must only consist of letters'
													}
												}
											},
											cellphoneNumber : {
												validators : {
													notEmpty : {
														message : 'Please enter 10 digits for cellphone number'
													},
													phone : {
														country : 'US',
														message : 'Please enter 10 digits for cellphone number'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Cellphone number must start with 0 (Zero)'
													}
												}
											},
											telephoneNumber : {
												validators : {
													notEmpty : {
														message : 'Please enter 10 digits for telephone number'
													},
													phone : {
														country : 'US',
														message : 'Please enter 10 digits for telephone number'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Tellphone number must start with 0 (Zero)'
													}
												}
											},

											cellphone : {
												validators : {
													notEmpty : {
														message : 'Please enter 10 digits for cellphone number'
													},
													phone : {
														country : 'US',
														message : 'Please enter 10 digits for cellphone number'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Cellphone number must start with 0 (Zero)'
													}
												}
											},
											telephone : {
												validators : {
													notEmpty : {
														message : 'Please enter 10 digits for telephone number'
													},
													phone : {
														country : 'US',
														message : 'Please enter 10 digits for telephone number'
													},
													regexp : {

														regexp : /^0[0-9].*$/,
														message : 'Tellphone number must start with 0 (Zero)'
													}
												}
											},

											email : {
												validators : {
													notEmpty : {
														message : 'Email address is required and cannot be empty'
													},
													emailAddress : {
														message : 'The email address is not valid'
													}
												}
											},// Contact Person

											// Machine Details

											serialNumber : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'Serial Number is required and cannot be empty'
													},
													regexp : {
														regexp : /^[a-z-A-Z0-9]+$/,
														message : 'Serial Number can only consist of numbers '
													}
												}
											},
											modelNumber : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'Model number is required and cannot be empty'
													}
												}
											},
											modelBrand : {
												validators : {
													notEmpty : {
														message : 'Brand is required and cannot be empty'
													}
												}
											},
											startDate : {
												validators : {
													notEmpty : {
														message : 'Contract start date is required'
													},
													date : {
														format : 'YYYY-MM-DD',
														// max: 'endDate',
														message : 'Contract start date is not a valid'
													}
												}
											},
											endDate : {
												validators : {
													notEmpty : {
														message : 'Contract end date is required'
													},
													date : {
														format : 'YYYY-MM-DD',
														// min: 'startDate',
														message : 'Contract end date is not a valid'
													}
												}
											},
											installationDate : {
												validators : {
													notEmpty : {
														message : 'The installation date is required'
													},
													date : {
														format : 'YYYY-MM-DD',
														// min: 'endDate',
														message : 'The installation date is not a valid'
													}
												}
											},
											streetNumber : {
												validators : {
													stringLength : {
													// min : 3,
													},
													notEmpty : {
														message : 'Street number is required and cannot be empty'
													}
												/*,
																									regexp : {
																										regexp : /^[0-9]+$/,
																										message : 'The Street number can only consist of numbers '
																									}*/
												}
											},
											buildingName : {
												validators : {
													stringLength : {
														min : 2,
													},
													notEmpty : {
														message : 'Building name is required and cannot be empty'
													}
												}
											},

											floorNumber : {
												validators : {
													stringLength : {
													// min : 3,
													},
													notEmpty : {
														message : 'Floor number is required and cannot be empty'
													}
												/*,
																									regexp : {
																										regexp : /^[0-9]+$/,
																										message : 'Floor number can only consist of numbers '
																									}*/
												}
											},
											streetName : {
												validators : {
													stringLength : {
														min : 3,
													},
													notEmpty : {
														message : 'Street name is required and cannot be empty'
													}

												}
											},
											city_town : {
												validators : {
													stringLength : {
														min : 3,
													},
													notEmpty : {
														message : 'City is required and cannot be empty'

													},
													regexp : {
														regexp : /^[-_ a-zA-Z]+$/,
														message : 'City or Town must only consist of letters'
													}
												}
											},
											province : {
												validators : {
													notEmpty : {
														message : 'Province is required and cannot be empty'
													}
												}
											},
											zipcode : {
												validators : {
													stringLength : {
														max : 4,
														min : 4,
													},
													notEmpty : {
														message : 'Zipcode is required and cannot be empty'
													}
												}
											},
											monocolour : {
												validators : {

													notEmpty : {
														message : 'Mono/Colour reading is required and cannot be empty'
													}

												}
											},
											colour : {
												validators : {

													notEmpty : {
														message : 'Mono/Colour reading is required and cannot be empty'
													}

												}
											},
											colourReading : {

												validators : {
													stringLenth : {
														min : 4,
														max : 6,
													},
													notEmpty : {
														message : 'Colour reading is required and cannot be empty'
													}

												}
											},

											colourCopyCost : {

												validators : {
													stringLenth : {
														min : 4,
														max : 6,
													},
													notEmpty : {
														message : 'Colour Copy Cost is required and cannot be empty'
													}

												}
											},

											monoReading : {

												validators : {
													stringLenth : {
														min : 4,
														max : 6,
													},
													notEmpty : {
														message : 'Mono reading is required and cannot be empty'
													}

												}
											},
											monoCopyCost : {

												validators : {
													stringLenth : {
														min : 4,
														max : 6,
													},
													notEmpty : {
														message : 'Mono Copy Cost is required and cannot be empty'
													}

												}
											},

											// Machine Details

											// Machine Accesories

											bridgeunit : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'Bridge unit is required and cannot be empty'
													}
												}
											},

											faxunitserial : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'Fax unit is required and cannot be empty'
													}
												}
											},

											onebintray : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'One bintray is required and cannot be empty'
													}
												}
											},

											finisherserial : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'Finisher is required and cannot be empty'
													}
												}
											},

											lct : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'LCT is required and cannot be empty'
													}
												}
											},

											credenza : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'Credenza is required and cannot be empty'
													}
												}
											},

											additionalserial : {
												validators : {
													stringLength : {
														min : 2,

													},
													notEmpty : {
														message : 'Additional paper tray is required and cannot be empty'
													}
												}
											},
											description : {
												validators : {
													notEmpty : {
														stringLength : {
															min : 4,
														},
														message : 'Comment is required of what was updated and updated to what, this cannot be empty'
													}
												}
											}
										}
									});
				});/*-- //End Validate add and update device --*/

/* -- Validate add parts -- */
$(document)
		.ready(
				function() {
					$('#spareParts')
							.bootstrapValidator(
									{
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											partNumber : {
												validators : {
													notEmpty : {
														message : 'Part number is required and cannot be empty'
													}
												}
											},
											itemType : {
												validators : {
													notEmpty : {
														message : 'Item type is required and cannot be empty'
													}
												}
											},
											color:{
												validators:{
													notEmpty:{
														message: 'Colour is required and cannot be empty'
													}
												}
											},
											quantity : {
												validators : {
													notEmpty : {
														message : 'Quantity is required and cannot be empty'
													}
												}
											},
											itemDescription : {
												validators : {
													notEmpty : {
														message : 'Description is required and cannot be empty'
													}
												}
											},
											modelBrand : {
												validators : {
													notEmpty : {
														message : 'Model brand is required and cannot be empty'
													}
												}
											},
											receivedBy : {
												validators : {
													notEmpty : {
														message : 'Received by is required and cannot be empty'
													}
												}
											},
											compitableDevice : {
												validators : {
													notEmpty : {
														message : 'Compitable Device by is required and cannot be empty'
													}
												}
											}
										}
									});
				});

/* -- //End Validate add parts -- */

/* --  Validate save Spare Parts -- */
$(document).ready(function() {
	$('#saveSpareParts').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			partNumber : {
				validators : {
					notEmpty : {
						message : 'Part number is required and cannot be empty'
					}
				}
			},
			itemType : {
				validators : {
					notEmpty : {
						message : 'Item type is required and cannot be empty'
					}
				}
			},
			quantity : {
				validators : {
					notEmpty : {
						message : 'Quantity is required and cannot be empty'
					}
				}
			},
			description : {
				validators : {
					notEmpty : {
						message : 'Description is required and cannot be empty'
					}
				}
			},
			supplierName : {
				validators : {
					notEmpty : {
						message : 'supplier Name is required and cannot be empty'
					}
				}
			},
			supplierOrderNo : {
				validators : {
					notEmpty : {
						message : 'Supplier Order Number is required and cannot be empty'
					}
				}
			},
			
			
		}
	});
});/* --  Validate save Spare Parts -- */

/* -- Validate Reset Password -- */
$(document)
		.ready(
				function() {
					$('#changePass')
							.bootstrapValidator(
									{
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {

											newpassword : {
												validators : {
													notEmpty : {
														message : 'The password is required and can\'t be empty'
													},
													identical : {
														field : 'confirmpassword',
														message : 'The password and its confirm are not the same'
													},
													stringLength : {
														min : 6,
														message : 'Password must be more than 6 characters long'
													},
													regexp : {
														regexp : /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[\d])(?=.*?[^\sa-zA-Z0-9]).{6,}$/,
														message : 'Password must contain at least one upper case, one lower case, one special character and  at least one digit'
													}
												}
											},
											confirmpassword : {
												validators : {
													notEmpty : {
														message : 'The confirm password is required and can\'t be empty'
													},
													identical : {
														field : 'newpassword',
														message : 'The password and its confirm are not the same'
													},
													stringLength : {
														min : 6,
														message : 'Password must be more than 6 characters long'
													},
													regexp : {
														regexp : /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[\d])(?=.*?[^\sa-zA-Z0-9]).{6,}$/,
														message : 'Password must contain at least one upper case, one lower case, one special character and  at least one digit'
													}
												}
											},

										}
									});
				});
/* -- //End Validate Reset Password --*/

/*--Compare start, end and installation date between each other--*/
$("#startDate, #endDate, #installationDate").datepicker();

$("#endDate").change(function() {

	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;

	if ((Date.parse(endDate) <= Date.parse(startDate))) {
		alert("Contract end date should be greater than Contract start date");
		document.getElementById("endDate").value = "";
	}

});
$("#installationDate")
		.change(
				function() {

					var startDate = document.getElementById("startDate").value;
					var endDate = document.getElementById("endDate").value;
					var installationDate = document
							.getElementById("installationDate").value;

					if ((Date.parse(installationDate) >= Date.parse(endDate) && Date
							.parse(startDate))) {
						alert("Installation date should be between contract start date and contract end date");
						document.getElementById("installationDate").value = "";
					}

				});/*-- //End Compare start, end and installation date between each other--*/

/*-- Validate searchEmployee --*/
$(document)
		.ready(
				function() {
					$('#searchEmployee')
							.bootstrapValidator(
									{
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											empName : {
												validators : {
													stringLength : {
														min : 2,
													},
													notEmpty : {
														message : 'Employee name is required to search and cannot be empty'
													}
												}
											}
										}
									});
				});/*-- //End Validate searchEmployee --*/

/*--Validate search partNumber--*/
$(document)
		.ready(
				function() {
					$('#searchpartNumber')
							.bootstrapValidator(
									{
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											partNumber : {
												validators : {
													stringLength : {
														min : 3,
													},
													notEmpty : {
														message : 'Part number is required to search and cannot be empty'
													}
												}
											},
										}
									});
				});/*--End Validate search partNumber--*/

/* ---Validate Make Order ----*/
$(document).ready(function() {
	$('#putInOrder').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			stockType : {
				validators : {
					notEmpty : {
						message : 'Stoke type is required and cannot be empty'
					}
				}
			},
			location : {
				validators : {
					stringLength : {
						min : 2,
					},
					notEmpty : {
						message : 'location is required and cannot be empty'
					}
				}
			},
			technicianUserName : {
				validators : {
					notEmpty : {
						message : 'Technician is required and cannot be empty'
					}
				}
			},
			customer : {
				validators : {
					notEmpty : {
						message : 'Customer is required and cannot be empty'
					}
				}
			},
			approver : {
				validators : {
					notEmpty : {
						message : 'Approver is required and cannot be empty'
					}
				}
			}
		}
	});
});/*-- //End Validate Make Order --*/


//validate parts movement
$(document).ready(function() {
	$('#moveSpares').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			reasonForSite : {
				validators : {
					notEmpty : {
						message : 'Reason is required and cannot be empty'
					}
				}
			},
			reasonForBoot : {
				validators : {
					notEmpty : {
						message : 'Reason is required and cannot be empty'
					}
				}
			},
			technicianName : {
				validators : {
					stringLength : {
						min : 2,
					},
					notEmpty : {
						message : 'Technician name is required and cannot be empty'
					}
				}
			},
			
			headOffice : {
				validators : {
					notEmpty : {
						message : 'Head Office is required and cannot be empty'
					}
				}
			},
			customerName : {
				validators : {
					notEmpty : {
						message : 'Customer is required and cannot be empty'
					}
				}
			}
		}
	});
});
//end validate parts movement

//Compare available quantity with entered quantity
function compareQuantity(element, availableQuantity) {

	if (availableQuantity > element.value) {
		console.log("True,", element.value + " is less than "
				+ availableQuantity);
		console.log("Place an Order");
	}
	if (element.value == '' || element.value == 0) {
		alert("Part is out of stock");
		element.value = null;
	} else if (element.value == '' || element.value == '') {
		alert("Quantity can not be empty or cant not be zero. Please enter quantity which is less than available quantity");
		element.value = null;
		console.log(element.value);
	} else if (availableQuantity < element.value) {
		alert("Your order quantity can not be greater than available quantity. Please enter less quantity");
		element.value = null;
		console.log("False,", availableQuantity + " is small than "
				+ element.value);
		console.log("You can not place an order, enter less quantity");
		console.log("Enter value between 1 till " + element.value
				+ " not more than " + availableQuantity);
	}
}
//End Compare available quantity with entered quantity	

/* --Stock type Selection-- */
function CheckStockType(val) {
	var element = document.getElementById('Site');
	if (val == 'select stock type' || val == 'Site')
		element.style.display = 'block';
	else
		element.style.display = 'none';
	var element = document.getElementById('Boot');
	if (val == 'select stock type' || val == 'Boot')
		element.style.display = 'block';
	else
		element.style.display = 'none';

}/* --Stock type Selection-- */

/*--Enable datepicker for start, end and install date --*/
$(document).ready(function() {
	$('#startDatePicker').datepicker({
		format : "yyyy-mm-dd",
		todayHighlight : true,
		//startDate: 'd0',
		autoclose : true
	});
});

$(document).ready(function() {
	$('#endDatePicker').datepicker({
		format : "yyyy-mm-dd",
		todayHighlight : true,
		//	startDate: 'd0',
		autoclose : true
	});
});

$(document).ready(function() {
	$('#installDatePicker').datepicker({
		format : "yyyy-mm-dd",
		todayHighlight : true,
		//startDate: 'd0',
		autoclose : true
	});
});
/*--//End Enable datepicker for start, end and install date --*/

/*--  Check if checkboxes are checked for adding machine accessories, if checked enable input text --*/
var bridgeUnit = $("input[type='checkbox'][value='Bridge unit']");
var finisher = $("input[type='checkbox'][value='Finisher']");

bridgeUnit.on('change', function() {
	finisher.prop('checked', this.checked);
});
finisher.on('change', function() {
	bridgeUnit.prop('checked', this.checked);
});

document.getElementById('bridgeunitserial').onchange = function() {
	document.getElementById('bridgeunit').disabled = !this.checked;
	document.getElementById('finisherserial').disabled = !this.checked;
};
document.getElementById('finisher').onchange = function() {
	document.getElementById('finisherserial').disabled = !this.checked;
	document.getElementById('bridgeunit').disabled = !this.checked;
};

document.getElementById('faxunit').onchange = function() {
	document.getElementById('faxunitserial').disabled = !this.checked;
};
document.getElementById('onebintrayserial').onchange = function() {
	document.getElementById('onebintray').disabled = !this.checked;
};

document.getElementById('ltcserial').onchange = function() {
	document.getElementById('lct').disabled = !this.checked;
};
document.getElementById('creserial').onchange = function() {
	document.getElementById('cre').disabled = !this.checked;
};
document.getElementById('addserial').onchange = function() {
	document.getElementById('add').disabled = !this.checked;
}; /*--// End Check if checkboxes are checked for adding machine accessories, if checked enable input text --*/

/*--Make all Serials numbers UpperCase--*/
function upperCaseF(a) {
	setTimeout(function() {
		a.value = a.value.toUpperCase();
	}, 1);
}/*-- //End Make all Serials numbers UpperCase--*/

/*--Check which is selected between mono and colour reading--*/
function CheckColors(val) {
	var element = document.getElementById('mono');
	if (val == 'pick a mono/colour' || val == 'mono' || val == 'colour')
		element.style.display = 'block';
	else
		element.style.display = 'none';

	var element = document.getElementById('colour');
	if (val == 'pick a colour' || val == 'colour')
		element.style.display = 'block';
	else
		element.style.display = 'none';
	
	

}/*--//End Check which is selected between mono and colour reading--*/

//Select colours
function SelectColours(val) {
	
	var element = document.getElementById('colors')
	if (val == 'select colour' || val == 'Toner')
		element.style.display = 'block';
	else
		elecemnt.style.display = 'none';	
	
}// end select from colors


/*----------GIVE ALERT TO USER*------------/

 /*-- Deactivate script --*/
$(document).on('click', ':not(form)[data-confirm]', function(e) {
	if (!confirm($(this).data('confirm'))) {
		e.stopImmediatePropagation();
		e.preventDefault();
	}
});/*-- End Deactivate script --*/

/*Check if checkbox for removeAccessory is checked*/
function valthisform() {
	var checkboxs = document.getElementsByName("removeAccessory");
	var okay = false;
	for (var i = 0, l = checkboxs.length; i < l; i++) {
		if (checkboxs[i].checked) {
			okay = true;
			break;
		}
	}
	if (okay)
		alert("Thank you for checking a checkbox");
	else
		alert("Please check a checkbox");
}/*Check if checkbox for removeAccessory is checked*/

/*-------end GIVE ALERT TO USER*--------/

 /*---Script to add other Accossory on add product--*/
$('#addOtherMachineAccessories').on('click', 'input[type="button"]',
		function() {
			$(this).closest('tr').remove();
		})
$('p input[type="button"]')
		.click(
				function() {
					$('#addOtherMachineAccessories')
							.append(
									'<tr><td><input type="text" class="form-control" id="machineType" name="machineType" placeholder="Machine Accessory Type" /></td><td><input type="text" class="form-control" id="serialNumberOtherAccessory" name="serialNumberOtherAccessory" onkeydown="upperCaseF(this)" placeholder="Serial Number"/></td><td><input type="button" class="btn btn-danger" id="remove" name="remove" value="Remove" /></td></tr>')
				});
/*---Script to add other Accossory on add product--*/

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

//Set up and make the request.
request.open('GET',
		'https://s3-us-west-2.amazonaws.com/s.cdpn.io/4621/html-elements.json',
		true);
request.send();

/*---End Create datalist to populate search---*/

/* Hide contact person 2 */
$('#contactPerson2').change(function() {
	$('#contactPerson2checkboxdiv').toggle();
});

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

/*-- Accept alphabetical characters only --*/
function testInput(event) {
	var value = String.fromCharCode(event.which);
	var pattern = new RegExp(/[a-zåäö ]/i);
	return pattern.test(value);
}
//$('#streetName').bind('keypress', testInput);

/* Accept alphabetical character only */
function onlyAlphabets(e, t) {
	try {
		if (window.event) {
			var charCode = window.event.keyCode;
		} else if (e) {
			var charCode = e.which;
		} else {
			return true;
		}
		if ((charCode > 64 && charCode < 91)
				|| (charCode > 96 && charCode < 123))
			return true;
		else
			return false;
	} catch (err) {
		alert(err.Description);
	}
}
