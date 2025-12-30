<?php

namespace App\Enum;

enum PaiementModeType: string
{
    case ESPECES = 'ESPECES';
    case WAVE = 'WAVE';
    case OM = 'OM';
}
