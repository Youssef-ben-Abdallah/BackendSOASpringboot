package com.backend.miniProjetBack.metier;

import com.backend.miniProjetBack.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
public interface UserInterface {

    Optional<User> getClientById(Long userId);
    List<User> getClientsByRole(int role);
}
