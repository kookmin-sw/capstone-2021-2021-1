package com.kookmin.pm.module.matchup.domain;

import com.kookmin.pm.module.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name="MATCH_UP_RECORD")
public class MatchUpRecord {
    @Id @GeneratedValue
    @Column(name = "MATCH_UP_RECORD_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCH_UP_ID")
    private MatchUp matchUp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WINNER_ID")
    private Member winner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOSER_ID")
    private Member loser;

    @Enumerated(EnumType.STRING)
    @Column(name="TYPE")
    private RecordType type;

    @Builder
    public MatchUpRecord(MatchUp matchUp, Member winner, Member loser, RecordType type) {
        this.matchUp = matchUp;
        this.winner = winner;
        this.loser = loser;
        this.type = type;
    }
}
