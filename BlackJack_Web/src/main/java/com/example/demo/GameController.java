package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GameController {

	List<Card> playerHand = new ArrayList<>();
	List<Card> dealerHand = new ArrayList<>();
	Deck deck = new Deck();
	String gameResult = null;

	@GetMapping("/")
	public String startGame(Model model) {
		initializeGame();
		deck.shuffle();

		playerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		playerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());

		model.addAttribute("playerHand", playerHand);
		model.addAttribute("dealerHand", dealerHand.get(0));
		model.addAttribute("hiddenCard", true);
		model.addAttribute("message", "choose hit or stand");
		model.addAttribute("gameResult", gameResult);

		return "game";
	}

	private void initializeGame() {
		deck = new Deck();
		deck.shuffle();
		playerHand = new ArrayList<>();
		dealerHand = new ArrayList<>();
		gameResult = null;
	}

	@PostMapping("/hit")
	public String hit(Model model) {
		playerHand.add(deck.dealCard());

		System.out.println("Player hits: " + playerHand);

		model.addAttribute("playerHand", playerHand);
		model.addAttribute("dealerHand", dealerHand.get(0));
		model.addAttribute("hiddenCard", true);

		if (getHandTotal(playerHand) > 21) {
			model.addAttribute("message", "バースト！ディーラーの勝利");
			model.addAttribute("hiddenCard", false);
			model.addAttribute("dealerHand", dealerHand);
			gameResult = "ディーラーの勝利";
			model.addAttribute("gameResult", gameResult);
			return "game";
		}

		model.addAttribute("message", "choose hit or stand");
		model.addAttribute("gameResult", gameResult);
		return "game";
	}

	@PostMapping("/stay")
	public String stay(Model model) {
		model.addAttribute("playerHand", playerHand);
		model.addAttribute("dealerHand", dealerHand);
		model.addAttribute("hiddenCard", false);

		while (getHandTotal(dealerHand) < 17) {
			dealerHand.add(deck.dealCard());
		}

		int playerTotal = getHandTotal(playerHand);
		int dealerTotal = getHandTotal(dealerHand);

		if (dealerTotal > 21 || playerTotal > dealerTotal) {
			model.addAttribute("message", "プレイヤーの勝利");
			gameResult = "プレイヤーの勝利";
		} else if (dealerTotal > playerTotal) {
			model.addAttribute("message", "ディーラーの勝利");
			gameResult = "ディーラーの勝利";
		} else {
			model.addAttribute("message", "引き分け");
			gameResult = "引き分け";
		}

		model.addAttribute("gameResult", gameResult);
		return "game";
	}

	@GetMapping("/newgame")
	public String newGame(Model model) {
		initializeGame();
		playerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		playerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		model.addAttribute("gameResult", gameResult);
		return "redirect:/";
	}

	private int getHandTotal(List<Card> hand) {
		int total = 0;
		int numAces = 0;
		for (Card card : hand) {
			int value = card.getCard();
			if (value == 1) {
				numAces++;
				total += 11;
			} else if (value >= 10) {
				total += 10;
			} else {
				total += value;
			}
		}
		while (total > 21 && numAces > 0) {
			total -= 10;
			numAces--;
		}
		return total;
	}
}
