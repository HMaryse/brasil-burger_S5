package com.brasilburger.service.impl;

import com.brasilburger.entity.Complement;
import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.service.ComplementService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ComplementServiceImpl implements ComplementService {

    private final ComplementRepository complementRepo;
    private final Cloudinary cloudinary;

    //  COMPLÉMENTS : FRITES, SAUCES, BOISSONS... image dans cloudinary

    private static final String[] FRIES_IMAGES = {
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765478198/photo-1541592106381-b31e9677c0e5_nvxfcu.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765484296/potato-fries-6323202_1280_qrvmft.jpg"
    };

    private static final String[] SAUCE_IMAGES = {
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765484307/sauce-4171459_1280_vykwo5.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765484316/salad-dressing-7295630_1280_sczsdw.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765484314/ketchup-356439_1280_yl6kqe.jpg"
    };

    private static final String[] DRINK_IMAGES = {
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765482589/drinks-7658475_1280_cfe5ka.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765482550/drink-2598132_1280_bxpkty.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765481091/drink-462776_1280_cu5rxi.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765478417/photo-1497534446932-c925b458314e_trfmdo.jpg"
    };

    public ComplementServiceImpl(ComplementRepository repo, Cloudinary cloudinary) {
        this.complementRepo = repo;
        this.cloudinary = cloudinary;
    }

    @Override
    public Complement addComplement(String nom, double prix) {
        String imageUrl = generateImageUrl(nom);

        Complement c = new Complement(nom, prix, imageUrl);
        c.setEtat("DISPONIBLE");

        return complementRepo.save(c);
    }

    @Override
    public Complement updateComplement(int id, String nom, double prix, String etat) {
        Optional<Complement> opt = complementRepo.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Complément introuvable id = " + id);
        }

        Complement c = opt.get();

        boolean nameChanged = !c.getNom().equalsIgnoreCase(nom);

        c.setNom(nom);
        c.setPrix(prix);
        c.setEtat(etat);

        if (nameChanged) {
            c.setImageUrl(generateImageUrl(nom));
        }

        return complementRepo.update(c);
    }

    private String generateImageUrl(String nom) {
        try {
            String lower = nom.toLowerCase();

            String image;

            if (lower.contains("frit")) {
                image = FRIES_IMAGES[(int)(Math.random() * FRIES_IMAGES.length)];
            }
            else if (lower.contains("sauce") || lower.contains("ketchup") || lower.contains("mayo")) {
                image = SAUCE_IMAGES[(int)(Math.random() * SAUCE_IMAGES.length)];
            }
            else if (lower.contains("boisson") || lower.contains("cola") || lower.contains("fanta")) {
                image = DRINK_IMAGES[(int)(Math.random() * DRINK_IMAGES.length)];
            }
            else {
                image = FRIES_IMAGES[0];
            }

            Map upload = cloudinary.uploader().upload(
                    image,
                    ObjectUtils.asMap(
                            "folder", "complements/generated",
                            "public_id", "complement_" + System.currentTimeMillis()
                    )
            );

            return upload.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Erreur generation image complement : " + e.getMessage());
        }
    }

    @Override
    public List<Complement> findAll() { return complementRepo.findAll(); }

    @Override
    public Optional<Complement> findById(int id) { return complementRepo.findById(id); }
}
