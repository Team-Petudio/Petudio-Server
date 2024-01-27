package com.nice.petudio.domain.pet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum FurColor {
    WHITE,
    BLACK,
    SPOTTED
}
