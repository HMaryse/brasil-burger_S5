package com.brasilburger.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private int id;
    private String nom;
    private String imageUrl;
    private String etat;
    private double prix;
    private List<MenuDetail> details = new ArrayList<>();

    public Menu() {}

    public Menu(String nom,double prix, String imageUrl,String etat) {
        this.nom = nom;
        this.imageUrl = imageUrl;
        this.etat = "DISPONIBLE";
        this.prix = prix;
        
    }


    public void addDetail(MenuDetail d) {
        details.add(d);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public List<MenuDetail> getDetails() {
        return details;
    }

    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }

    
}
