<?php

namespace App\Entity;

use App\Repository\MenuDetailRepository;
use Doctrine\ORM\Mapping as ORM;
use App\Entity\Menu;
use App\Entity\Burger;


#[ORM\Entity(repositoryClass: MenuDetailRepository::class)]
#[ORM\Table(name: "menu_detail")]
class MenuDetail
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: "integer")]
    private int $quantite = 1;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: "menu_id", nullable: false)]
    private Menu $menu;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: "burger_id", nullable: false)]
    private Burger $burger;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getQuantite(): ?int
    {
        return $this->quantit;
    }

    public function setQuantite(int $quantit): static
    {
        $this->quantit = $quantit;

        return $this;
    }
}
