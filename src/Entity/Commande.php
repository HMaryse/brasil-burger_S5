<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use App\Enum\StatutCommandeType;
use App\Enum\ModeConsommationType;

#[ORM\Entity(repositoryClass: CommandeRepository::class)]
#[ORM\Table(name: "commande")]
class Commande
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(name: "date_commande", type: "datetime")]
    private \DateTimeInterface $dateCommande;

    #[ORM\Column(enumType: StatutCommandeType::class)]
    private StatutCommandeType $statut;

    #[ORM\Column(name: "mode_consommation", enumType: ModeConsommationType::class)]
    private ModeConsommationType $modeConsommation;

    #[ORM\Column(type: "text", nullable: true)]
    private ?string $adresse = null;

    #[ORM\Column(name: "est_paye")]
    private bool $estPaye = false;

    #[ORM\ManyToOne(inversedBy: 'commandes')]
    #[ORM\JoinColumn(name: 'client_id', nullable: false)]
    private Client $client;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'zone_id', nullable: true)]
    private ?Zone $zone = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'livreur_id', nullable: true)]
    private ?Livreur $livreur = null;

    #[ORM\OneToMany(mappedBy: 'commande', targetEntity: CommandeItem::class)]
    private Collection $items;

    public function __construct()
    {
        $this->dateCommande = new \DateTime();
        $this->statut = StatutCommandeType::EN_COURS;
        $this->items = new ArrayCollection();
    }

    public function getId(): ?int 
    { 
        return $this->id; 
    }
    public function getDateCommande(): \DateTimeInterface 
    { 
        return $this->dateCommande; 
    }
    public function getStatut(): StatutCommandeType 
    { 
        return $this->statut; 
    }
    public function getModeConsommation(): ModeConsommationType 
    { 
        return $this->modeConsommation; 
    }
    public function getAdresse(): ?string 
    { 
        return $this->adresse; 
    }
    public function isEstPaye(): bool 
    { 
        return $this->estPaye; 
    }
    public function getClient(): Client 
    { 
        return $this->client; 
    }

    public function getItems(): Collection
    {
        return $this->items;
    }
    public function getTotal(): float
    {
        $total = 0;

        foreach ($this->items as $item) {
            $total += $item->getPrixTotal();
        }

        return $total;
    }
    public function setStatut(StatutCommandeType $statut): self
    {
        $this->statut = $statut;
        return $this;
    }


}
