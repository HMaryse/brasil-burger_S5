<?php

namespace App\Repository;

use App\Entity\Commande;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
 use App\Enum\StatutCommandeType;
use Doctrine\ORM\EntityRepository;


class CommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }

    public function totalRecettesJour(): float
    {
        $start = new \DateTimeImmutable('today');
        $end = $start->modify('+1 day');

        return (float) $this->createQueryBuilder('c')
            ->select('COALESCE(SUM(p.montant), 0)')
            ->join('c.paiement', 'p')
            ->andWhere('c.dateCommande >= :start')
            ->andWhere('c.dateCommande < :end')
            ->setParameter('start', $start)
            ->setParameter('end', $end)
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function countByStatutToday(StatutCommandeType $statut): int
    {
        $debutJour = new \DateTime('today 00:00:00');
        $finJour   = new \DateTime('today 23:59:59');

        return (int) $this->createQueryBuilder('c')
            ->select('COUNT(c.id)')
            ->where('c.statut = :statut')
            ->andWhere('c.dateCommande BETWEEN :debut AND :fin')
            ->setParameter('statut', $statut)
            ->setParameter('debut', $debutJour)
            ->setParameter('fin', $finJour)
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function recettesDuJour(): float
    {
        $debutJour = new \DateTime('today 00:00:00');
        $finJour   = new \DateTime('today 23:59:59');

        return (float) $this->createQueryBuilder('c')
            ->select('COALESCE(SUM(i.prixTotal), 0)')
            ->join('c.items', 'i')
            ->where('c.statut = :statut')
            ->andWhere('c.dateCommande BETWEEN :debut AND :fin')
            ->setParameter('statut', StatutCommandeType::VALIDEE)
            ->setParameter('debut', $debutJour)
            ->setParameter('fin', $finJour)
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function dernieresCommandes(int $limit = 5): array
    {
        return $this->createQueryBuilder('c')
            ->orderBy('c.dateCommande', 'DESC')
            ->setMaxResults($limit)
            ->getQuery()
            ->getResult();
    }
}