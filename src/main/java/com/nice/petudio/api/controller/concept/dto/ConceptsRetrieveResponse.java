package com.nice.petudio.api.controller.concept.dto;

import com.nice.petudio.common.util.MessageUtils;
import com.nice.petudio.domain.concept.Concept;
import com.nice.petudio.domain.concept.ConceptMessageType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.MessageSource;

public record ConceptsRetrieveResponse(List<ConceptRetrieveResponse> conceptInfos) {

    public static ConceptsRetrieveResponse convertEntitiesToDto(List<Concept> concepts, MessageSource messageSource,
                                                                LocalDateTime now) {
        List<ConceptRetrieveResponse> conceptsResponse = new ArrayList<>();

        for (Concept concept : concepts) {
            String conceptMessagePrefix = concept.getInfo().getMessagePrefix();

            String name = MessageUtils.getMessage(messageSource,
                    conceptMessagePrefix + ConceptMessageType.NAME.getType());
            String descriptionMessage = MessageUtils.getMessage(messageSource,
                    conceptMessagePrefix + ConceptMessageType.DESCRIPTION.getType());

            conceptsResponse.add(new ConceptRetrieveResponse(concept.getId(), concept.getMainImageUri(), name,
                    concept.getPrice(), concept.getDiscountedPrice(now),
                    descriptionMessage, concept.getInfo().getPetType(), concept.validateIsNew(now)));
        }
        return new ConceptsRetrieveResponse(conceptsResponse);
    }
}
