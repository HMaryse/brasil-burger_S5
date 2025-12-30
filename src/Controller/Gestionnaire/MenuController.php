<?php

namespace App\Controller\Gestionnaire;

use App\Entity\Menu;
use App\Form\MenuType;
use App\Repository\MenuRepository;
use App\Repository\BurgerRepository;
use App\Repository\ComplementRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class MenuController extends AbstractController
{
    #[Route('/gestionnaire/menus', name: 'app_gestionnaire_menus')]
    public function index(
        MenuRepository $menuRepository,
        Request $request
    ): Response {
        $page = max(1, $request->query->getInt('page', 1));
        $limit = 6;
        $offset = ($page - 1) * $limit;

        $total = $menuRepository->count([]);
        $menus = $menuRepository->findBy(
            [],
            ['id' => 'DESC'],
            $limit,
            $offset
        );

        $totalPages = ceil($total / $limit);

        return $this->render('gestionnaire/menus.html.twig', [
            'menus' => $menus,
            'page' => $page,
            'totalPages' => $totalPages,
        ]);
    }


    #[Route('/gestionnaire/menus/add', name: 'app_gestionnaire_menu_add', methods: ['GET', 'POST'])]
        public function add(
            Request $request,
            EntityManagerInterface $em,
            BurgerRepository $burgerRepository,
            ComplementRepository $complementRepository
        ): Response {
            $menu = new Menu();
            $form = $this->createForm(MenuType::class, $menu);
            $form->handleRequest($request);

            if ($form->isSubmitted() && $form->isValid()) {

                $burgerId = $request->request->get('burger_id');
                $complementId = $request->request->get('complement_id');

                if (!$burgerId || !$complementId) {
                    $this->addFlash('error', 'Veuillez choisir un burger et un complément');
                    return $this->redirectToRoute('app_gestionnaire_menu_add');
                }

                $burger = $burgerRepository->find($burgerId);
                $complement = $complementRepository->find($complementId);

                if (!$burger || !$complement) {
                    throw $this->createNotFoundException('Burger ou complément introuvable');
                }

                $menu->setPrix($burger->getPrix() + $complement->getPrix());

                $em->persist($menu);
                $em->flush();

                $this->addFlash('success', 'Menu ajouté avec succès');

                return $this->redirectToRoute('app_gestionnaire_menus');
            }

            return $this->render('gestionnaire/menu_new.html.twig', [
                'form' => $form->createView(),
                'burgers' => $burgerRepository->findBy(['etat' => 'DISPONIBLE']),
                'complements' => $complementRepository->findBy(['etat' => 'DISPONIBLE']),
            ]);
    }


    #[Route('/gestionnaire/menus/{id}/edit', name: 'app_gestionnaire_menu_edit', methods: ['GET', 'POST'])]
    public function edit(
        Menu $menu,
        Request $request,
        EntityManagerInterface $em,
        BurgerRepository $burgerRepository,
        ComplementRepository $complementRepository
    ): Response {
        $form = $this->createForm(MenuType::class, $menu);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();

            $this->addFlash('success', 'Menu modifié avec succès');

            return $this->redirectToRoute('app_gestionnaire_menus');
        }

        return $this->render('gestionnaire/menu_edit.html.twig', [
            'form' => $form->createView(),
            'menu' => $menu,
            'burgers' => $burgerRepository->findBy(
                ['etat' => 'DISPONIBLE'],
                ['nom' => 'ASC']
            ),
            'complements' => $complementRepository->findBy(
                ['etat' => 'DISPONIBLE'],
                ['nom' => 'ASC']
            ),
        ]);
    }

    #[Route('/gestionnaire/menus/{id}/delete', name: 'app_gestionnaire_menu_delete', methods: ['POST'])]
    public function delete(
        Menu $menu,
        Request $request,
        EntityManagerInterface $em
    ): Response {
        if ($this->isCsrfTokenValid(
            'delete_menu_' . $menu->getId(),
            $request->request->get('_token')
        )) {
            $em->remove($menu);
            $em->flush();

            $this->addFlash('success', 'Menu supprimé');
        }

        return $this->redirectToRoute('app_gestionnaire_menus');
    }
}
