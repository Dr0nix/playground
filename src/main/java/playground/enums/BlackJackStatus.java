package playground.enums;

public enum BlackJackStatus {
    READY,          // 게임 시작 전
    PLAYER_TURN,    // 플레이어 턴
    DEALER_TURN,    // 딜러 턴
    PLAYER_BUST,    // 플레이어 버스트
    DEALER_BUST,    // 딜러 버스트
    FINISHED        // 게임 종료
}
