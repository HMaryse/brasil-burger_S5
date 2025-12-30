FROM php:8.2-apache

# Installations de base
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev libzip-dev zip \
    && docker-php-ext-install intl pdo pdo_pgsql zip \
    && apt-get clean

# Activer Apache rewrite
RUN a2enmod rewrite

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Dossier de travail
WORKDIR /var/www/html

# Copier tout le projet
COPY . .

# Installer dépendances (sans exécuter les scripts)
RUN composer install --no-dev --no-scripts --optimize-autoloader

# Nettoyer le fichier bundles.php des bundles de dev
RUN sed -i '/DebugBundle/d; /WebProfilerBundle/d' config/bundles.php 2>/dev/null || true

# Installer les assets manuellement
RUN php bin/console assets:install public --no-interaction 2>/dev/null || true

# Dump autoload
RUN composer dump-autoload --optimize --classmap-authoritative

# Permissions
RUN chown -R www-data:www-data var public

# Config Apache pour pointer vers public/
RUN sed -i 's!/var/www/html!/var/www/html/public!g' /etc/apache2/sites-available/000-default.conf

EXPOSE 80
CMD ["apache2-foreground"]