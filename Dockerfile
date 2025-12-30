FROM php:8.2-apache

# Dépendances système + PostgreSQL
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev libzip-dev zip \
    && docker-php-ext-install intl pdo pdo_pgsql zip \
    && apt-get clean

# Activer les modules Apache nécessaires
RUN a2enmod rewrite headers

# Configurer Apache pour Symfony
COPY docker/apache.conf /etc/apache2/sites-available/000-default.conf
RUN echo "ServerName localhost" >> /etc/apache2/apache2.conf

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Dossier de travail
WORKDIR /var/www/html

# Copier le projet
COPY . .

# Installer dépendances Symfony
RUN composer install --no-dev --no-scripts --optimize-autoloader
RUN composer dump-autoload --classmap-authoritative

# Créer le dossier pour les logs et cache
RUN mkdir -p var/log var/cache

# Permissions Symfony
RUN chown -R www-data:www-data var \
    && chmod -R 775 var

# Exposer le port
EXPOSE 80