<?php

namespace App\Enum;

enum StatutCommandeType: string
{
    case EN_COURS  = 'EN_COURS';
    case VALIDEE   = 'VALIDÉE';
    case TERMINEE  = 'TERMINÉE';
    case ANNULEE   = 'ANNULÉE';
}
