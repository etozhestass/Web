package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    private final TalkRepository userRepository = new TalkRepositoryImpl();

    public void validateTalk(long userId, String text) throws ValidationException {
        if (userRepository.findTalksByUserId(userId) == null) {
            throw new ValidationException("User does not exist");
        }
        if (Strings.isNullOrEmpty(text)) {
            System.out.println("error");
            throw new ValidationException("Message is required");
        }
        text = text.trim();
        if (text.isEmpty()) {
            throw new ValidationException("Message must contain not only spaces");
        }
        if (text.length() >= 300) {
            throw new ValidationException("Message is too long");
        }
    }


    public Talk makeTalk(long sourceUserId, long targetUserId, String text) {
        Talk talk = new Talk();
        talk.setSourceUserId(sourceUserId);
        talk.setTargetUserId(targetUserId);
        talk.setText(text);
        return talk;
    }

    public void register(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Talk> findTalksByUserId(long userId) {
        return talkRepository.findTalksByUserId(userId);
    }
}
