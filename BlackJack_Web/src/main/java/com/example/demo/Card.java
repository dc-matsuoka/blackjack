package com.example.demo;

public class Card {
	private final int value;
	
	public Card(int value) {
		this.value = value;
	}
	
	public int getCard() {
		return value;
	}
	
	@Override
	public String toString() {
		if(value == 1) {
			return "A";
		} else if(value == 11) {
			return "J";
		} else if(value == 12) {
			return "Q";
		} else if(value == 13) {
			return "K";
		} else {
			return String.valueOf(value);
		}
	}
}
