<?php

namespace App\Repository;

use App\Entity\CommandeItem;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class CommandeItemRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, CommandeItem::class);
    }

    
    public function topProduitsDuJour(int $limit = 5): array
    {
        $debutJour = new \DateTime('today 00:00:00');
        $finJour   = new \DateTime('today 23:59:59');

        return $this->createQueryBuilder('i')
            ->select('
                COALESCE(b.nom, m.nom) AS nom,
                COUNT(i.id) AS ventes,
                SUM(i.prixTotal) AS recette
            ')
            ->join('i.commande', 'c')
            ->leftJoin('i.burger', 'b')
            ->leftJoin('i.menu', 'm')
            ->where('c.dateCommande BETWEEN :debut AND :fin')
            ->groupBy('nom')
            ->orderBy('ventes', 'DESC')
            ->setMaxResults($limit)
            ->setParameter('debut', $debutJour)
            ->setParameter('fin', $finJour)
            ->getQuery()
            ->getResult();
    }
}
