package com.nice.petudio.api.controller.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeNotificationStatusResponse {
    private final boolean notificationStatus;
}
