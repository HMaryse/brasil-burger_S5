FROM php:8.2-apache

# Installations de base
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev libzip-dev zip \
    && docker-php-ext-install intl pdo pdo_pgsql zip \
    && apt-get clean

# Activer les modules Apache
RUN a2enmod rewrite

# Configuration Apache simple mais efficace
COPY .htaccess /var/www/html/public/.htaccess

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Répertoire de travail
WORKDIR /var/www/html

# IMPORTANT : Copier d'abord seulement les fichiers nécessaires pour le cache
COPY composer.json composer.lock symfony.lock ./
RUN composer install --no-dev --no-scripts --no-autoloader

# Copier TOUT le reste
COPY . .

# Dump autoload
RUN composer dump-autoload --optimize --classmap-authoritative

# DEBUG : Vérifier que les fichiers CSS existent
RUN echo "=== DEBUG: Structure des fichiers ===" && \
    echo "Contenu du dossier public :" && \
    ls -la public/ && \
    echo "=== Recherche de fichiers CSS ===" && \
    find . -name "*.css" -type f | head -20 && \
    echo "=== Vérification du CSS spécifique ===" && \
    if [ -f "public/assets/css/dashboard.css" ]; then \
        echo "✓ Fichier CSS trouvé : public/assets/css/dashboard.css"; \
        echo "Taille :" $(stat -c%s "public/assets/css/dashboard.css") "octets"; \
    elif [ -f "public/asset/css/dashboard.css" ]; then \
        echo "✓ Fichier CSS trouvé : public/asset/css/dashboard.css"; \
        echo "Taille :" $(stat -c%s "public/asset/css/dashboard.css") "octets"; \
    else \
        echo "✗ Fichier dashboard.css NON TROUVÉ"; \
        echo "Recherche dans tout le projet :"; \
        find . -name "dashboard.css" -type f; \
    fi

# Permissions
RUN chown -R www-data:www-data /var/www/html \
    && chmod -R 755 /var/www/html/public

# Apache config - SIMPLIFIÉE
RUN echo '<VirtualHost *:80>\n\
    DocumentRoot /var/www/html/public\n\
    <Directory /var/www/html/public>\n\
        AllowOverride All\n\
        Require all granted\n\
        Options Indexes FollowSymLinks\n\
    </Directory>\n\
    ErrorLog ${APACHE_LOG_DIR}/error.log\n\
    CustomLog ${APACHE_LOG_DIR}/access.log combined\n\
</VirtualHost>' > /etc/apache2/sites-available/000-default.conf

EXPOSE 80
CMD ["apache2-foreground"]