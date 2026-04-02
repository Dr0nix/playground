package playground.apps.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import playground.enums.BlackJackResult;
import playground.enums.BlackJackStatus;
import playground.model.entity.plain.PokerCard;
import playground.model.entity.plain.User;
import playground.model.repository.PokerCardRepository;
import playground.model.repository.UserRepository;
import playground.utils.UsetUtil;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlackJackSvc {
    private final PokerCardRepository repo;
    private final UserRepository userRepository;

    private Queue<PokerCard> getDeckWithoutJoker() {
        List<PokerCard> cards = repo.findByRankNot(0); // 조커 빼고 가져오기

        Collections.shuffle(cards);

        Queue<PokerCard> deck = new LinkedList<>(cards);
        return deck;
    }

    public BlackJackGameState startGame(int betAmount) {
        // 베팅 차감
        UsetUtil.deductPoint(betAmount);

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

        // 오프닝에서 바로 끝난 경우 (블랙잭 등) 정산
        if (gameState.getStatus() == BlackJackStatus.FINISHED) {
            settle(gameState);
        }

        return gameState;
    }

    public BlackJackGameState hit(BlackJackGameState gameState) {
        if (gameState.getStatus() != BlackJackStatus.PLAYER_TURN) {
            return gameState;
        }

        gameState.getPlayerHand().add(gameState.getDeck().poll());
        gameState = checkGameState(gameState);

        // 버스트로 끝난 경우 정산
        if (gameState.getStatus() == BlackJackStatus.FINISHED) {
            settle(gameState);
        }

        return gameState;
    }

    public BlackJackGameState stand(BlackJackGameState gameState) {
        if (gameState.getStatus() != BlackJackStatus.PLAYER_TURN) {
            return gameState;
        }

        gameState.setStatus(BlackJackStatus.DEALER_TURN);

        // 딜러: 17 미만이면 계속 히트
        while (calculateScore(gameState.getDealerHand()) < 17 || calculateScore(gameState.getDealerHand()) < calculateScore(gameState.getPlayerHand())) {
            gameState.getDealerHand().add(gameState.getDeck().poll());
        }

        gameState = resolveGame(gameState);
        settle(gameState);

        return gameState;
    }

    private BlackJackGameState resolveGame(BlackJackGameState gameState) {
        int playerScore = calculateScore(gameState.getPlayerHand());
        int dealerScore = calculateScore(gameState.getDealerHand());

        boolean playerBJ = gameState.getPlayerHand().size() == 2 && playerScore == 21;
        boolean dealerBJ = gameState.getDealerHand().size() == 2 && dealerScore == 21;

        gameState.setStatus(BlackJackStatus.FINISHED);

        if (playerBJ && dealerBJ) {
            gameState.setResult(BlackJackResult.PUSH);
        } else if (playerBJ) {
            gameState.setResult(BlackJackResult.PLAYER_BLACKJACK);
        } else if (dealerBJ) {
            gameState.setResult(BlackJackResult.DEALER_BLACKJACK);
        } else if (dealerScore > 21) {
            gameState.setResult(BlackJackResult.PLAYER_WIN);
        } else if (playerScore > dealerScore) {
            gameState.setResult(BlackJackResult.PLAYER_WIN);
        } else if (playerScore < dealerScore) {
            gameState.setResult(BlackJackResult.DEALER_WIN);
        } else {
            gameState.setResult(BlackJackResult.PUSH);
        }

        return gameState;
    }

    private void settle(BlackJackGameState gameState) {
        int bet = gameState.getBetAmount();
        BlackJackResult result = gameState.getResult();

        switch (result) {
            case PLAYER_BLACKJACK -> UsetUtil.addPoint((int) (bet * 2.5));
            case PLAYER_WIN       -> UsetUtil.addPoint(bet * 2);
            case PUSH             -> UsetUtil.addPoint(bet);
            // DEALER_WIN, DEALER_BLACKJACK → 이미 차감됨
            default -> {}
        }

        savePoint();
    }

    private void savePoint() {
        User user = UsetUtil.getUser();
        if (user != null) {
            userRepository.save(user);
        }
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

    public int calculateScore(List<PokerCard> hand) {
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
