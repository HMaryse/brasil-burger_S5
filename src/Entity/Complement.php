<?php

namespace App\Entity;

use App\Repository\ComplementRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Enum\EtatType;
use App\Entity\CommandeItemComplement;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;


#[ORM\Entity(repositoryClass: ComplementRepository::class)]
#[ORM\Table(name: "complement")]
class Complement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $nom = null;

    #[ORM\Column(name: "image_url", type: "string", length: 500, nullable: true)]
    private ?string $imageUrl = null;

    #[ORM\Column(type: "decimal", precision: 10, scale: 2)]
    private string $prix;

    #[ORM\Column(enumType: EtatType::class)]
    private EtatType $etat;

    #[ORM\OneToMany(mappedBy: 'complement', targetEntity: CommandeItemComplement::class)]
    private Collection $commandeItemComplements;


    public function __construct()
    {
        $this->etat = EtatType::DISPONIBLE;
        $this->commandeItemComplements = new ArrayCollection();

    }


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): static
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrix(): ?string
    {
        return $this->prix;
    }

    public function setPrix(string $prix): static
    {
        $this->prix = $prix;

        return $this;
    }

    public function getImageUrl(): ?string
    {
        return $this->imageUrl;
    }

    public function setImageUrl(string $imageUrl): static
    {
        $this->imageUrl = $imageUrl;

        return $this;
    }

    public function getEtat(): EtatType
    {
        return $this->etat;
    }

    public function setEtat(EtatType $etat): self
    {
        $this->etat = $etat;
        return $this;
    }
}
