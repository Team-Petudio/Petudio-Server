package com.nice.petudio.api.controller.pet.dto;

import java.util.List;

public record FindMyPetsResponse(List<FindMyPetResponse> petsInfo) {
}
