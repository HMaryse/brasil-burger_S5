package com.brasilburger.entity;

public class Burger {

    private int id;
    private String nom;
    private double prix;
    private String imageUrl;
    private String etat;

    public Burger() {}

    public Burger(String nom, double prix, String imageUrl) {
        this.nom = nom;
        this.prix = prix;
        this.imageUrl = imageUrl;
        this.etat = "DISPONIBLE";
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
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

}
