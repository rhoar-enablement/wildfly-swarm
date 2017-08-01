package com.redhat.coolstore.model;

import java.io.Serializable;

public class Store implements Serializable {

	private static final long serialVersionUID = -7304814269819778382L;

	public Store() {

	}

	public Store(String location, boolean open, String address) {
		this.location = location;
		this.open = open;
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	private String location;
	private boolean open;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String address;


}