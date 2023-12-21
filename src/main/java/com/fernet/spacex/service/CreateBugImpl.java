package com.fernet.spacex.service;

import com.fernet.spacex.controller.request.CreateCardRequest;
import com.fernet.spacex.service.model.CardCategory;
import com.fernet.spacex.service.model.ParamType;
import com.fernet.spacex.service.rest.TrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Objects.*;

@Service
public class CreateBugImpl extends AbstractCardCreator {
    public static final String BACKLOG_LIST_NAME = "Backlog";
    @Autowired
    private TrelloService trelloService;

    /**
     * Method that generates a random word.
     */
    private String generateRandomWord() {
        StringBuilder word = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // Letras minúsculas
            word.append(randomChar);
        }

        return word.toString();
    }


    @Override
    public Map<ParamType, String> getCardParameters(CreateCardRequest createCardRequest) {
        Map<ParamType, String> response = new HashMap<>();
        String labelId = trelloService.getLabelIdByName(CardCategory.Bug.toString());
        Random random = new Random();
        int randomNumber = random.nextInt(Integer.MAX_VALUE);

        String word = "bug-" + generateRandomWord() + "-" + randomNumber;
        List<String> boardMemebers = trelloService.getBoardMembers();
        String memberId = !boardMemebers.isEmpty() ? boardMemebers.get(random.nextInt(boardMemebers.size())) : "";

        response.put(ParamType.LIST, trelloService.getListByName(BACKLOG_LIST_NAME));
        response.put(ParamType.NAME, word);
        response.put(ParamType.LABEL, labelId);
        response.put(ParamType.DESCRIPTION, createCardRequest.getDescription());
        response.put(ParamType.MEMBERS, memberId);

        return response;
    }

    @Override
    public void runValidations(CreateCardRequest createCardRequest) {
        if (isNull(createCardRequest.getDescription())) {
            throw new IllegalArgumentException("Null description on bug");
        }
    }
}
