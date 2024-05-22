package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private final List<Card> cards;
	private int currentCardIndex;
	
	public Deck() {
		cards = new ArrayList<>();
		for(int i=1;i<=13;i++) {
			for(int j=0;j<4;j++) {
				cards.add(new Card(i));
			}
		}
		currentCardIndex = 0;
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
		currentCardIndex = 0;
	}
	
	public Card dealCard() {
		if(currentCardIndex >= cards.size()) {
			throw new IllegalStateException("デッキに充分なカードがありません。");
		}
		return cards.get(currentCardIndex++);
	}
}
