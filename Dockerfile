FROM php:8.2-apache

WORKDIR /var/www/html

# Copier tout
COPY . .

# Installations
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev libzip-dev zip \
    && docker-php-ext-install intl pdo pdo_pgsql zip \
    && apt-get clean

# Apache
RUN a2enmod rewrite

# Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Corriger bundles.php POUR AJOUTER DoctrineMigrationsBundle
RUN if [ -f "config/bundles.php" ]; then \
        # Ajouter DoctrineMigrationsBundle s'il n'est pas présent
        if ! grep -q "DoctrineMigrationsBundle" "config/bundles.php"; then \
            sed -i '/];/i \    Doctrine\\Bundle\\MigrationsBundle\\DoctrineMigrationsBundle::class => ["all" => true],' config/bundles.php; \
        fi; \
    else \
        # Créer bundles.php
        echo '<?php' > config/bundles.php && \
        echo 'return [' >> config/bundles.php && \
        echo '    Symfony\Bundle\FrameworkBundle\FrameworkBundle::class => ["all" => true],' >> config/bundles.php && \
        echo '    Symfony\Bundle\SecurityBundle\SecurityBundle::class => ["all" => true],' >> config/bundles.php && \
        echo '    Symfony\Bundle\TwigBundle\TwigBundle::class => ["all" => true],' >> config/bundles.php && \
        echo '    Doctrine\Bundle\DoctrineBundle\DoctrineBundle::class => ["all" => true],' >> config/bundles.php && \
        echo '    Doctrine\Bundle\MigrationsBundle\DoctrineMigrationsBundle::class => ["all" => true],' >> config/bundles.php && \
        echo '];' >> config/bundles.php; \
    fi

# Installer dépendances
RUN composer install --optimize-autoloader

# Créer dossiers
RUN mkdir -p var/cache var/log

# Permissions
RUN chown -R www-data:www-data /var/www/html

# Config Apache
RUN sed -i 's!/var/www/html!/var/www/html/public!g' /etc/apache2/sites-available/000-default.conf

EXPOSE 80
CMD ["apache2-foreground"]