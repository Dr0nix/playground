package playground.apps.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import playground.enums.BlackJackResult;
import playground.enums.BlackJackStatus;
import playground.model.entity.plain.PokerCard;

import java.util.List;
import java.util.Queue;

@Data
@AllArgsConstructor
public class BlackJackGameState {
    private Queue<PokerCard> deck;
    private List<PokerCard> playerHand;
    private List<PokerCard> dealerHand;

    private BlackJackStatus status;
    private BlackJackResult result;

    private Integer betAmount;

}
