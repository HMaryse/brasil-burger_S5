<?php

namespace App\Controller\Gestionnaire;

use App\Entity\Burger;
use App\Form\BurgerType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class BurgerAddController extends AbstractController
{
    #[Route(
        '/gestionnaire/burgers/add',
        name: 'app_gestionnaire_burger_add',
        methods: ['GET', 'POST']
    )]
    public function add(
        Request $request,
        EntityManagerInterface $em
    ): Response {
        $burger = new Burger();
        $form = $this->createForm(BurgerType::class, $burger);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($burger);
            $em->flush();

            $this->addFlash('success', 'Burger ajouté avec succès');

            return $this->redirectToRoute('app_gestionnaire_burgers');
        }

        return $this->render('gestionnaire/burger_new.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    #[Route(
        '/gestionnaire/burgers/{id}/edit',
        name: 'app_gestionnaire_burger_edit',
        methods: ['GET', 'POST']
    )]
    public function edit(
        Burger $burger,
        Request $request,
        EntityManagerInterface $em
    ): Response {
        $form = $this->createForm(BurgerType::class, $burger);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();

            $this->addFlash('success', 'Burger modifié avec succès');

            return $this->redirectToRoute('app_gestionnaire_burgers');
        }

        return $this->render('gestionnaire/burger_edit.html.twig', [
            'form' => $form->createView(),
            'burger' => $burger
        ]);
    }
    #[Route(
        '/gestionnaire/burgers/{id}/delete',
        name: 'app_gestionnaire_burger_delete',
        methods: ['POST']
    )]
    public function delete(
        Burger $burger,
        EntityManagerInterface $em,
        Request $request
    ): Response {
        if ($this->isCsrfTokenValid(
            'delete_burger_' . $burger->getId(),
            $request->request->get('_token')
        )) {
            $em->remove($burger);
            $em->flush();

            $this->addFlash('success', 'Burger supprimé avec succès');
        }

        return $this->redirectToRoute('app_gestionnaire_burgers');
    }


}
