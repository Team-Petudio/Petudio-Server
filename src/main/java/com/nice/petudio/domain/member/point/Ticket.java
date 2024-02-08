package com.nice.petudio.domain.member.point;

import com.nice.petudio.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tickets")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type", length = 30, nullable = false)
    private TicketType type;

    @Column(name = "ticket_amount", nullable = false)
    private int amount;

    public static Ticket newInstance(Long memberId) {
        return Ticket.builder()
                .memberId(memberId)
                .type(TicketType.DEFAULT)
                .amount(0)
                .build();
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }
}
