package com.nice.petudio.domain.member.point;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TicketType {
    DEFAULT(8_800);

    private final int price;
}
