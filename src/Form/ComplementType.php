<?php

namespace App\Form;

use App\Entity\Complement;
use App\Enum\EtatType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\MoneyType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ComplementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom', TextType::class, [
                'label' => 'Nom du complément',
            ])
            ->add('prix', MoneyType::class, [
                'label' => 'Prix (FCFA)',
                'currency' => 'XOF',
            ])
            ->add('imageUrl', TextType::class, [
                'label' => 'Image (URL)',
                'required' => false,
            ])
            ->add('etat', ChoiceType::class, [
                'label' => 'État',
                'choices' => [
                    'Disponible' => EtatType::DISPONIBLE,
                    'Indisponible' => EtatType::INDISPONIBLE,
                ],
                'choice_value' => fn (?EtatType $etat) => $etat?->value,
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Complement::class,
        ]);
    }
}
