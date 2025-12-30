FROM php:8.2-apache

# Mise à jour et installation
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev libzip-dev zip \
    && docker-php-ext-install intl pdo pdo_pgsql zip \
    && apt-get clean

# Activer rewrite
RUN a2enmod rewrite

# Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Travail
WORKDIR /var/www/html

# Copier tout
COPY . .

# Dépendances
RUN composer install --no-dev --optimize-autoloader

# Vérifier
RUN ls -la public/ && echo "CSS:" && find public/ -name "*.css" 2>/dev/null

# Permissions
RUN chown -R www-data:www-data var public

# Config Apache basique (SANS guillemets multilignes problématiques)
RUN sed -i 's!/var/www/html!/var/www/html/public!g' /etc/apache2/sites-available/000-default.conf

EXPOSE 80
CMD ["apache2-foreground"]