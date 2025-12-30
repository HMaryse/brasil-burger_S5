FROM php:8.2-apache

# Installations de base
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev libzip-dev zip \
    && docker-php-ext-install intl pdo pdo_pgsql zip \
    && apt-get clean

# Activer les modules Apache
RUN a2enmod rewrite

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Répertoire de travail
WORKDIR /var/www/html

# Copier d'abord les fichiers essentiels pour le cache
COPY composer.json composer.lock symfony.lock ./

# Installer les dépendances (sans les dev)
RUN composer install --no-dev --no-scripts --no-autoloader

# Copier TOUT le projet (y compris public/.htaccess)
COPY . .

# DEBUG: Vérifier que les fichiers sont bien copiés
RUN echo "=== Vérification des fichiers importants ===" && \
    echo "1. .htaccess existe ?" && \
    ls -la public/.htaccess 2>/dev/null && echo "✓" || echo "✗" && \
    echo "2. Fichiers CSS dans public/assets/ ?" && \
    find public/assets -name "*.css" 2>/dev/null | head -5 && \
    echo "3. Structure de public/ :" && \
    ls -la public/

# Dump autoload optimisé
RUN composer dump-autoload --optimize --classmap-authoritative

# Permissions (TRÈS IMPORTANT)
RUN chown -R www-data:www-data /var/www/html \
    && chmod -R 755 /var/www/html/public \
    && chmod 644 public/.htaccess 2>/dev/null || true

# Configuration Apache SIMPLIFIÉE et CORRECTE
RUN echo '<VirtualHost *:80>
    DocumentRoot /var/www/html/public
    
    <Directory /var/www/html/public>
        AllowOverride All
        Require all granted
        Options FollowSymLinks
        
        # Important: permettre l\'accès aux fichiers .css
        <FilesMatch "\.(css|js|jpg|jpeg|png|gif|ico|svg|woff|woff2|ttf|eot)$">
            Require all granted
        </FilesMatch>
    </Directory>
    
    ErrorLog ${APACHE_LOG_DIR}/error.log
    CustomLog ${APACHE_LOG_DIR}/access.log combined
</VirtualHost>' > /etc/apache2/sites-available/000-default.conf

# Désactiver le message d'avertissement Apache
RUN echo "ServerName localhost" >> /etc/apache2/apache2.conf

EXPOSE 80

CMD ["apache2-foreground"]