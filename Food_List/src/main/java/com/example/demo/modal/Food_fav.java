package com.example.demo.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "list_details")
public class Food_fav {
    

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name", length = 100)
	private String name;

	@Column(name = "veg", length = 100)
	private String veg;

	@Column(name = "veg_price")
	private int vegPrice;

	@Column(name = "non_veg", length = 100)
	private String nonVeg;

	@Column(name = "non_veg_price")
	private int nonVegPrice;

	@Column(name = "reason", length = 250)
	private String reason;


	public Food_fav() {
	}

	public Food_fav(String name, String veg, int vegPrice, String nonVeg, int nonVegPrice, String reason) {
		this.name = name;
		this.veg = veg;
		this.vegPrice = vegPrice;
		this.nonVeg = nonVeg;
		this.nonVegPrice = nonVegPrice;
		this.reason = reason;
	}


	public int getId() {
		return (int) id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVeg() {
		return veg;
	}

	public void setVeg(String veg) {
		this.veg = veg;
	}

	public int getVegPrice() {
		return vegPrice;
	}

	public void setVegPrice(int vegPrice) {
		this.vegPrice = vegPrice;
	}

	public String getNonVeg() {
		return nonVeg;
	}

	public void setNonVeg(String nonVeg) {
		this.nonVeg = nonVeg;
	}

	public int getNonVegPrice() {
		return nonVegPrice;
	}

	public void setNonVegPrice(int nonVegPrice) {
		this.nonVegPrice = nonVegPrice;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Food_fav [id=" + id + ", name=" + name + ", veg=" + veg + ", vegPrice=" + vegPrice + ", nonVeg=" + nonVeg + ", nonVegPrice=" + nonVegPrice + ", reason=" + reason + "]";
	}
}