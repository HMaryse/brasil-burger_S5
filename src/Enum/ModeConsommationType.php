<?php

namespace App\Enum;

enum ModeConsommationType: string
{
    case SUR_PLACE = 'SUR_PLACE';
    case A_EMPORTER = 'A_EMPORTER';
    case LIVRAISON = 'LIVRAISON';
}
