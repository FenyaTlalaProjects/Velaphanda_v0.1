package com.demo.bean;

import java.util.Calendar;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SparePartsBean {
	
	private String partNumber;
	private String modelNumber;
	private String itemDescription;
	private int quantity;
	private Calendar arrivedDate;
	private String modelBrand;
	private String color;
	private String capturedBy;
	private Date dateCaptured;
}
