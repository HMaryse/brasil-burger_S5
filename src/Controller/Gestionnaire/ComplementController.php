<?php

namespace App\Controller\Gestionnaire;

use App\Entity\Complement;
use App\Form\ComplementType;
use App\Repository\ComplementRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class ComplementController extends AbstractController
{
    #[Route('/gestionnaire/complements', name: 'app_gestionnaire_complements')]
    public function index(
        ComplementRepository $complementRepository,
        Request $request
    ): Response {
        $page = max(1, $request->query->getInt('page', 1));
        $limit = 6;
        $offset = ($page - 1) * $limit;

        $total = $complementRepository->count([]);
        $complements = $complementRepository->findBy(
            [],
            ['id' => 'DESC'],
            $limit,
            $offset
        );

        $totalPages = ceil($total / $limit);

        return $this->render('gestionnaire/complements.html.twig', [
            'complements' => $complements,
            'page' => $page,
            'totalPages' => $totalPages,
        ]);
    }

    #[Route('/gestionnaire/complements/add', name: 'app_gestionnaire_complement_add', methods: ['GET', 'POST'])]
    public function add(
        Request $request,
        EntityManagerInterface $em
    ): Response {
        $complement = new Complement();
        $form = $this->createForm(ComplementType::class, $complement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($complement);
            $em->flush();

            $this->addFlash('success', 'Complément ajouté avec succès');

            return $this->redirectToRoute('app_gestionnaire_complements');
        }

        return $this->render('gestionnaire/complement_new.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/gestionnaire/complements/{id}/edit', name: 'app_gestionnaire_complement_edit', methods: ['GET', 'POST'])]
    public function edit(
        Complement $complement,
        Request $request,
        EntityManagerInterface $em
    ): Response {
        $form = $this->createForm(ComplementType::class, $complement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();

            $this->addFlash('success', 'Complément modifié avec succès');

            return $this->redirectToRoute('app_gestionnaire_complements');
        }

        return $this->render('gestionnaire/complement_edit.html.twig', [
            'form' => $form->createView(),
            'complement' => $complement,
        ]);
    }

    #[Route('/gestionnaire/complements/{id}/delete', name: 'app_gestionnaire_complement_delete', methods: ['POST'])]
    public function delete(
        Complement $complement,
        Request $request,
        EntityManagerInterface $em
    ): Response {
        if ($this->isCsrfTokenValid(
            'delete_complement_' . $complement->getId(),
            $request->request->get('_token')
        )) {
            $em->remove($complement);
            $em->flush();

            $this->addFlash('success', 'Complément supprimé');
        }

        return $this->redirectToRoute('app_gestionnaire_complements');
    }
}
