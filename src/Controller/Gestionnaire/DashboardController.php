<?php

namespace App\Controller\Gestionnaire;

use App\Enum\StatutCommandeType;
use App\Repository\CommandeItemRepository;
use App\Repository\CommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class DashboardController extends AbstractController
{
    #[Route('/gestionnaire/dashboard', name: 'app_gestionnaire_dashboard')]
    public function index(
        CommandeRepository $commandeRepository,
        CommandeItemRepository $itemRepository
    ): Response {
        return $this->render('gestionnaire/dashboard/index.html.twig', [
            'enCours'    => $commandeRepository->countByStatutToday(StatutCommandeType::EN_COURS),
            'validees'   => $commandeRepository->countByStatutToday(StatutCommandeType::VALIDEE),
            'annulees'   => $commandeRepository->countByStatutToday(StatutCommandeType::ANNULEE),
            'recettes'   => $commandeRepository->recettesDuJour(),
            'topBurgers' => $itemRepository->topProduitsDuJour(),
            'recentes'   => $commandeRepository->dernieresCommandes(),
        ]);
    }
}
