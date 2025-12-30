<?php

namespace App\Controller;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class DefaultController
{
    #[Route('/', name: 'homepage')]
    public function index(): Response
    {
        return new Response('Symfony fonctionne en production');
    }
}
