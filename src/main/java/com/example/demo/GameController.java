package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
	@GetMapping("/")
	public String startGame(Model model) {
		Deck deck = new Deck();
		deck.shuffle();
		List<Card> playerHand = new ArrayList<>();
		List<Card> dealerHand = new ArrayList<>();
		
		playerHand.add(deck.dealCard());
        dealerHand.add(deck.dealCard());
        playerHand.add(deck.dealCard());
        dealerHand.add(deck.dealCard());

        model.addAttribute("playerHand", playerHand);
        model.addAttribute("dealerHand", dealerHand.get(0));
        model.addAttribute("hiddenCard", true);
        model.addAttribute("message", "choose hit or stand");

        return "game";
	}
}
