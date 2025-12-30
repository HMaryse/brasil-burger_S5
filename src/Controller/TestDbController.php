<?php

namespace App\Controller;

use App\Repository\CommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class TestDbController extends AbstractController
{
    #[Route('/test-db')]
    public function index(CommandeRepository $commandeRepo): Response
    {
        return $this->json([
            'commandes_total' => $commandeRepo->count([]),
            'commandes_en_cours' => $commandeRepo->countCommandesEnCoursToday(),
        ]);
    }
}
