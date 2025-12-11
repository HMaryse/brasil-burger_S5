package com.brasilburger.service;

import com.brasilburger.entity.Complement;
import java.util.List;
import java.util.Optional;

public interface ComplementService {

    Complement addComplement(String nom, double prix);

    Complement updateComplement(int id, String nom, double prix, String etat);

    List<Complement> findAll();

    Optional<Complement> findById(int id);
}
