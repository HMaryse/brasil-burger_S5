<?php

namespace App\Controller\Gestionnaire;

use App\Repository\BurgerRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
 use App\Entity\Burger;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Request;
use App\Enum\EtatType;


class BurgerController extends AbstractController
{
    #[Route('/gestionnaire/burgers', name: 'app_gestionnaire_burgers')]
    public function index(
        BurgerRepository $burgerRepository,
        Request $request
    ): Response {
        $page = max(1, $request->query->getInt('page', 1));
        $limit = 3;
        $offset = ($page - 1) * $limit;

        $total = $burgerRepository->count([]);
        $burgers = $burgerRepository->findBy([], ['id' => 'DESC'], $limit, $offset);

        $totalPages = ceil($total / $limit);

        return $this->render('gestionnaire/burgers.html.twig', [
            'burgers' => $burgers,
            'page' => $page,
            'totalPages' => $totalPages,
        ]);
    }

   
    #[Route('/gestionnaire/burgers/new', name: 'app_gestionnaire_burger_new')]
    public function new(Request $request, EntityManagerInterface $em): Response
    {
        if ($request->isMethod('POST')) {
            $burger = new Burger();
            $burger->setNom($request->request->get('nom'));
            $burger->setPrix((float)$request->request->get('prix'));
            $burger->setImageUrl($request->request->get('image_url'));
            $burger->setEtat(EtatType::DISPONIBLE);

            $em->persist($burger);
            $em->flush();

            return $this->redirectToRoute('app_gestionnaire_burgers');
        }

        return $this->render('gestionnaire/burger_new.html.twig');
    }

}
