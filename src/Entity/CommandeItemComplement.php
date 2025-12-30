<?php

namespace App\Entity;

use App\Repository\CommandeItemComplementRepository;
use Doctrine\ORM\Mapping as ORM;
use App\Entity\CommandeItem;
use App\Entity\Complement;


#[ORM\Entity(repositoryClass: CommandeItemComplementRepository::class)]
#[ORM\Table(name: "commande_item_complement")]
class CommandeItemComplement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: "integer")]
    private int $quantite = 1;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: "commande_item_id", nullable: false)]
    private CommandeItem $commandeItem;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: "complement_id", nullable: false)]
    private Complement $complement;



    public function getId(): ?int
    {
        return $this->id;
    }

    public function getQuantite(): ?int
    {
        return $this->quantite;
    }

    public function setQuantite(int $quantite): static
    {
        $this->quantite = $quantite;

        return $this;
    }
}
