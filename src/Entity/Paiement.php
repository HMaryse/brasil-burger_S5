<?php

namespace App\Entity;

use App\Repository\PaiementRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Enum\StatutPaiementType;
use App\Enum\PaiementModeType;
use App\Entity\Commande;

#[ORM\Entity(repositoryClass: PaiementRepository::class)]
#[ORM\Table(name: "paiement")]
class Paiement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: "decimal", precision: 10, scale: 2)]
    private string $montant;

    #[ORM\Column(name: "date_paiement", type: "datetime", nullable: true)]
    private ?\DateTimeInterface $datePaiement = null;

    #[ORM\Column(name: "statut_paiement", enumType: StatutPaiementType::class)]
    private StatutPaiementType $statutPaiement;

    #[ORM\Column(name: "mode_paiement", enumType: PaiementModeType::class)]
    private PaiementModeType $modePaiement;


    #[ORM\OneToOne]
    #[ORM\JoinColumn(name: "commande_id", nullable: false)]
    private Commande $commande;

    public function __construct()
    {
        $this->datePaiement = new \DateTime();
        $this->statutPaiement = StatutPaiementType::EN_ATTENTE;
        $this->modePaiement = PaiementModeType::ESPECES;
    }


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getMontant(): ?string
    {
        return $this->montant;
    }

    public function setMontant(string $montant): static
    {
        $this->montant = $montant;

        return $this;
    }

    public function getDatePaiement(): ?\DateTime
    {
        return $this->datePaiement;
    }

    public function setDatePaiement(?\DateTime $datePaiement): static
    {
        $this->datePaiement = $datePaiement;

        return $this;
    }

    public function getStatutPaiement(): ?string
    {
        return $this->statutPaiement;
    }

    public function setStatutPaiement(string $statutPaiement): static
    {
        $this->statutPaiement = $statutPaiement;

        return $this;
    }

    public function getModePaiemet(): ?string
    {
        return $this->modePaiemet;
    }

    public function setModePaiemet(string $modePaiemet): static
    {
        $this->modePaiemet = $modePaiemet;

        return $this;
    }
}
