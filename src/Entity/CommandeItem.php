<?php

namespace App\Entity;

use App\Repository\CommandeItemRepository;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;

#[ORM\Entity(repositoryClass: CommandeItemRepository::class)]
#[ORM\Table(name: "commande_item")]
class CommandeItem
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column]
    private int $quantite = 1;

    #[ORM\Column(name: "prix_total", type: "decimal", precision: 10, scale: 2)]
    private string $prixTotal;

    #[ORM\ManyToOne(inversedBy: 'items')]
    #[ORM\JoinColumn(name: "commande_id", nullable: false)]
    private Commande $commande;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: "burger_id", nullable: true)]
    private ?Burger $burger = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: "menu_id", nullable: true)]
    private ?Menu $menu = null;

    #[ORM\OneToMany(mappedBy: 'commandeItem', targetEntity: CommandeItemComplement::class)]
    private Collection $complements;

    public function __construct()
    {
        $this->complements = new ArrayCollection();
    }

    public function getId(): ?int { return $this->id; }
    public function getQuantite(): int { return $this->quantite; }
    public function getPrixTotal(): string { return $this->prixTotal; }

    public function getLibelle(): string
    {
        return $this->burger?->getNom()
            ?? $this->menu?->getNom()
            ?? 'Article';
    }
}
