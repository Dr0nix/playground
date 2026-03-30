package playground.model.entity.plain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.enums.CardSuit;

@Entity
@Table(name = "pg_poker_card", schema = "game")
@Getter
@NoArgsConstructor
public class PokerCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardSuit suit;

    @Column(nullable = false)
    private int rank;

    @Column(nullable = false)
    private String imgUrl;

}
