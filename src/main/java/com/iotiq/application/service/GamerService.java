package com.iotiq.application.service;

import com.iotiq.application.domain.Gamer;
import com.iotiq.application.repository.GamerRepository;
import com.iotiq.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamerService {
    private final GamerRepository gamerRepository;

    public Gamer getOrCreateGamerForUser(User user) {
        return gamerRepository.findById(user.getId()).orElseGet(() -> createNewGamer(user));
    }

    private Gamer createNewGamer(User user) {
        Gamer gamer = new Gamer();
        gamer.setUser(user);
        gamerRepository.save(gamer);
        return gamer;
    }
}
