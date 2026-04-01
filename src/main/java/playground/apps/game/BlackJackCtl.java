package playground.apps.game;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import playground.enums.BlackJackStatus;
import playground.model.entity.plain.PokerCard;

import java.util.*;

@Tag(name = "BlackJack", description = "블랙잭 게임 API")
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/game/blackjack")
public class BlackJackCtl {
    private final BlackJackSvc svc;
    private static final String SESSION_KEY = "blackjackState";

    @PostMapping("/start")
    public ResponseEntity<?> startGame(@RequestBody Map<String, Integer> body, HttpSession session) {
        int betAmount = body.getOrDefault("betAmount", 100);

        BlackJackGameState state = svc.startGame(betAmount);
        session.setAttribute(SESSION_KEY, state);

        return new ResponseEntity<>(toResponse(state), HttpStatus.OK);
    }

    @PostMapping("/hit")
    public ResponseEntity<?> hit(HttpSession session) {
        BlackJackGameState state = getState(session);
        if (state == null) {
            return new ResponseEntity<>(Map.of("error", "게임이 시작되지 않았습니다."), HttpStatus.BAD_REQUEST);
        }

        state = svc.hit(state);
        session.setAttribute(SESSION_KEY, state);

        return new ResponseEntity<>(toResponse(state), HttpStatus.OK);
    }

    @PostMapping("/stand")
    public ResponseEntity<?> stand(HttpSession session) {
        BlackJackGameState state = getState(session);
        if (state == null) {
            return new ResponseEntity<>(Map.of("error", "게임이 시작되지 않았습니다."), HttpStatus.BAD_REQUEST);
        }

        state = svc.stand(state);
        session.setAttribute(SESSION_KEY, state);

        return new ResponseEntity<>(toResponse(state), HttpStatus.OK);
    }

    private BlackJackGameState getState(HttpSession session) {
        return (BlackJackGameState) session.getAttribute(SESSION_KEY);
    }

    private Map<String, Object> toResponse(BlackJackGameState state) {
        Map<String, Object> res = new HashMap<>();

        res.put("playerHand", toCardList(state.getPlayerHand()));
        res.put("playerScore", svc.calculateScore(state.getPlayerHand()));

        boolean finished = state.getStatus() == BlackJackStatus.FINISHED;

        // 딜러: 게임 진행 중이면 첫 번째 카드만 공개
        if (finished) {
            res.put("dealerHand", toCardList(state.getDealerHand()));
            res.put("dealerScore", svc.calculateScore(state.getDealerHand()));
        } else {
            List<Map<String, Object>> dealerCards = new ArrayList<>();
            List<PokerCard> dealerHand = state.getDealerHand();
            for (int i = 0; i < dealerHand.size(); i++) {
                Map<String, Object> card = toCardMap(dealerHand.get(i));
                card.put("faceDown", i == 1); // 두 번째 카드 뒤집기
                dealerCards.add(card);
            }
            res.put("dealerHand", dealerCards);
            res.put("dealerScore", null);
        }

        res.put("deckCount", state.getDeck().size());
        res.put("status", state.getStatus());
        res.put("result", state.getResult());
        res.put("betAmount", state.getBetAmount());

        return res;
    }

    private List<Map<String, Object>> toCardList(List<PokerCard> hand) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PokerCard card : hand) {
            Map<String, Object> m = toCardMap(card);
            m.put("faceDown", false);
            list.add(m);
        }
        return list;
    }

    private Map<String, Object> toCardMap(PokerCard card) {
        Map<String, Object> m = new HashMap<>();
        m.put("code", card.getCode());
        m.put("suit", card.getSuit());
        m.put("rank", card.getRank());
        m.put("imgUrl", card.getImgUrl());
        return m;
    }
}