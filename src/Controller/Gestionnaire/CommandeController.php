<?php

namespace App\Controller\Gestionnaire;

use App\Entity\Commande;
use App\Enum\StatutCommandeType;
use App\Repository\CommandeRepository;
use App\Repository\LivreurRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpKernel\Exception\BadRequestHttpException;
use Symfony\Component\Routing\Attribute\Route;

class CommandeController extends AbstractController
{
    #[Route('/gestionnaire/commandes', name: 'app_gestionnaire_commandes')]
    public function index(
        CommandeRepository $commandeRepository,
        Request $request
    ): Response {
        $page = max(1, $request->query->getInt('page', 1));
        $limit = 6;
        $offset = ($page - 1) * $limit;

        $total = $commandeRepository->count([]);
        $totalPages = ceil($total / $limit);

        return $this->render('gestionnaire/commandes/index.html.twig', [
            'enCours' => $commandeRepository->findBy(
                ['statut' => StatutCommandeType::EN_COURS],
                ['dateCommande' => 'DESC'],
                $limit,
                $offset
            ),
            'validees' => $commandeRepository->findBy(
                ['statut' => StatutCommandeType::VALIDEE],
                ['dateCommande' => 'DESC'],
                $limit,
                $offset
            ),
            'terminees' => $commandeRepository->findBy(
                ['statut' => StatutCommandeType::TERMINEE],
                ['dateCommande' => 'DESC'],
                $limit,
                $offset
            ),
            'annulees' => $commandeRepository->findBy(
                ['statut' => StatutCommandeType::ANNULEE],
                ['dateCommande' => 'DESC'],
                $limit,
                $offset
            ),
            'page' => $page,
            'totalPages' => $totalPages,
        ]);
    }

    #[Route('/gestionnaire/commandes/{id}', name: 'app_gestionnaire_commande_show')]
    public function show(
        Commande $commande,
        LivreurRepository $livreurRepository
    ): Response {
        return $this->render('gestionnaire/commandes/show.html.twig', [
            'commande' => $commande,
            'livreurs' => $livreurRepository->findAll(),
        ]);
    }

    #[Route('/gestionnaire/commandes/{id}/statut/{action}', name: 'app_gestionnaire_commande_statut')]
    public function changerStatut(
        Commande $commande,
        string $action,
        EntityManagerInterface $em
    ): Response {

        $action = strtolower(trim($action));

        $map = [
            'valider'    => StatutCommandeType::VALIDEE,
            'annuler'    => StatutCommandeType::ANNULEE,
            'terminer'   => StatutCommandeType::TERMINEE,

            'validee'    => StatutCommandeType::VALIDEE,
            'acceptee'   => StatutCommandeType::VALIDEE,
            'terminee'   => StatutCommandeType::TERMINEE,
            'annulee'    => StatutCommandeType::ANNULEE,
        ];

        if (!isset($map[$action])) {
            $this->addFlash('danger', 'Action invalide sur la commande.');
            return $this->redirectToRoute(
                'app_gestionnaire_commande_show',
                ['id' => $commande->getId()]
            );
        }

        $commande->setStatut($map[$action]);
        $em->flush();

        $this->addFlash('success', 'Statut de la commande mis à jour.');

        return $this->redirectToRoute(
            'app_gestionnaire_commande_show',
            ['id' => $commande->getId()]
        );
    }


    #[Route('/gestionnaire/commandes/{id}/livreur', name: 'app_gestionnaire_commande_livreur', methods: ['POST'])]
    public function attribuerLivreur(
        Commande $commande,
        Request $request,
        LivreurRepository $livreurRepository,
        EntityManagerInterface $em
    ): Response {
        $livreur = $livreurRepository->find($request->request->get('livreur_id'));

        if ($livreur) {
            $commande->setLivreur($livreur);
            $em->flush();
        }

        return $this->redirectToRoute(
            'app_gestionnaire_commande_show',
            ['id' => $commande->getId()]
        );
    }
}
