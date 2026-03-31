package playground.apps.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import playground.enums.BlackJackResult;
import playground.enums.BlackJackStatus;
import playground.model.entity.plain.PokerCard;
import playground.model.repository.PokerCardRepository;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlackJackSvc {
    private final PokerCardRepository repo;

    private Queue<PokerCard> getDeckWithoutJoker() {
        List<PokerCard> cards = repo.findByRankNot(0); // 조커 빼고 가져오기

        Collections.shuffle(cards);

        Queue<PokerCard> deck = new LinkedList<>(cards);
        return deck;
    }

    public BlackJackGameState startGame(int betAmount) {
        Queue<PokerCard> deck = getDeckWithoutJoker();

        List<PokerCard> playerHand = new ArrayList<>();
        playerHand.add(deck.poll());
        playerHand.add(deck.poll());

        List<PokerCard> dealerHand = new ArrayList<>();
        dealerHand.add(deck.poll());
        dealerHand.add(deck.poll());

        BlackJackGameState gameState = new BlackJackGameState(deck, playerHand, dealerHand,
                BlackJackStatus.PLAYER_TURN, BlackJackResult.NONE, betAmount);

        gameState = checkGameState(gameState);

        return gameState;
    }

    private BlackJackGameState checkGameState(BlackJackGameState gameState) {
        List<PokerCard> playerHand = gameState.getPlayerHand();
        List<PokerCard> dealerHand = gameState.getDealerHand();

        int playerScore = calculateScore(playerHand);
        int dealerScore = calculateScore(dealerHand);

        boolean playerBlackJack = playerHand.size() == 2 && playerScore == 21;
        boolean dealerBlackJack = dealerHand.size() == 2 && dealerScore == 21;

        boolean playerBust = playerScore > 21;
        boolean dealerBust = dealerScore > 21;

        if (playerBlackJack && dealerBlackJack) {
            gameState.setStatus(BlackJackStatus.FINISHED);
            gameState.setResult(BlackJackResult.PUSH);
        } else if (playerBlackJack) {
            gameState.setStatus(BlackJackStatus.FINISHED);
            gameState.setResult(BlackJackResult.PLAYER_BLACKJACK);
        } else if (dealerBlackJack) {
            gameState.setStatus(BlackJackStatus.FINISHED);
            gameState.setResult(BlackJackResult.DEALER_BLACKJACK);
        } else if (playerBust) {
            gameState.setStatus(BlackJackStatus.FINISHED);
            gameState.setResult(BlackJackResult.DEALER_WIN);
        } else if (dealerBust) {
            gameState.setStatus(BlackJackStatus.FINISHED);
            gameState.setResult(BlackJackResult.PLAYER_WIN);
        } else {
            gameState.setStatus(BlackJackStatus.PLAYER_TURN);
            gameState.setResult(BlackJackResult.NONE);
        }

        return gameState;
    }

    private int calculateScore(List<PokerCard> hand) {
        int total = 0;
        int aceCount = 0;

        for (PokerCard card : hand) {
            int rank = card.getRank();

            if (rank == 1) {
                total += 11;
                aceCount++;
            } else if (rank >= 11 && rank <= 13) {
                total += 10;
            } else {
                total += rank;
            }
        }

        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }
}
