<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Security\Core\User\PasswordAuthenticatedUserInterface;

#[ORM\Entity(repositoryClass: UserRepository::class)]
#[ORM\Table(name: "`user`")]
class User implements UserInterface, PasswordAuthenticatedUserInterface
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(name: "nom_complet", length: 100)]
    private string $nomComplet;

    #[ORM\Column(length: 150, unique: true)]
    private string $email;

    #[ORM\Column(name: "mot_de_passe", type: "text")]
    private string $motDePasse;

    #[ORM\Column(length: 20)]
    private string $role;

    public function getId(): ?int { return $this->id; }

    public function getNomComplet(): string { return $this->nomComplet; }

    public function getEmail(): string { return $this->email; }

    public function getPassword(): string 
    { return $this->motDePasse; }

    public function getRoles(): array
    {
        return ['ROLE_' . strtoupper($this->role)];
    }
    public function getRole(): string
    {
        return $this->role;
    }


    public function getUserIdentifier(): string
    {
        return $this->email;
    }

    public function eraseCredentials(): void {}

}
