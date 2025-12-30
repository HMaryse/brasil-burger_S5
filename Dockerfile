FROM php:8.2-apache

# Dépendances système + PostgreSQL
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev libzip-dev zip \
    && docker-php-ext-install intl pdo pdo_pgsql zip

# Activer Apache rewrite (Symfony)
RUN a2enmod rewrite

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Dossier de travail
WORKDIR /var/www/html

# Copier le projet
COPY . .

# Installer dépendances Symfony
RUN composer install --no-dev --no-scripts --optimize-autoloader
RUN composer dump-autoload --classmap-authoritative

# Apache → dossier public/
RUN sed -i 's!/var/www/html!/var/www/html/public!g' /etc/apache2/sites-available/000-default.conf

# Permissions Symfony
RUN mkdir -p /var/www/html/var \
    && chown -R www-data:www-data /var/www/html/var

EXPOSE 80
