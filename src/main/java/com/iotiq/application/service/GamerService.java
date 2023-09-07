package com.iotiq.application.service;

import com.iotiq.application.domain.Gamer;
import com.iotiq.application.repository.GamerRepository;
import com.iotiq.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GamerService {
    private final GamerRepository gamerRepository;

    public Gamer getOrCreateGamerForUser(User user) {
        Optional<Gamer> optionalGamer = gamerRepository.findById(user.getId());
        Gamer gamer;

        if (optionalGamer.isPresent()) {
            gamer = optionalGamer.get();
        } else {
            gamer = new Gamer();
            gamer.setUser(user);
            gamerRepository.save(gamer);
        }

        return gamer;
    }
}
