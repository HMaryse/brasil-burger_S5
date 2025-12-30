<?php

namespace App\Controller\Gestionnaire;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class LivraisonController extends AbstractController
{
    #[Route('/gestionnaire/livraisons', name: 'app_gestionnaire_livraisons')]
    public function index(): Response
    {
        return $this->render('gestionnaire/livraisons.html.twig');
    }
}
