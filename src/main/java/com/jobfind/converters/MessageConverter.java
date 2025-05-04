package com.jobfind.converters;

import com.jobfind.dto.response.MessageResponse;
import com.jobfind.models.Message;
import com.jobfind.populators.MessagePopulator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
@AllArgsConstructor
@Component
public class MessageConverter {
    private final MessagePopulator messagePopulator;

    public MessageResponse convertToMessageResponse(Message source) {
        MessageResponse target = new MessageResponse();
        messagePopulator.populate(source, target);
        return target;
    }
}

