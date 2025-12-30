<?php

namespace App\Enum;

enum StatutPaiementType: string
{
    case EN_ATTENTE = 'EN_ATTENTE';
    case PAYE = 'PAYE';
    case ANNULE = 'ANNULE';
}
