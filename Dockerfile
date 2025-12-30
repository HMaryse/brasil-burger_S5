FROM php:8.2-apache

# Installations
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev libzip-dev zip \
    && docker-php-ext-install intl pdo pdo_pgsql zip \
    && apt-get clean

# Apache
RUN a2enmod rewrite

# Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Dossier de travail
WORKDIR /var/www/html

# Copier tout
COPY . .

# Créer les dossiers manquants
RUN mkdir -p var/cache var/log

# SUPPRIMER et RECRÉER bundles.php
RUN rm -f config/bundles.php && \
    echo '<?php' > config/bundles.php && \
    echo 'return [' >> config/bundles.php && \
    echo '    Symfony\Bundle\FrameworkBundle\FrameworkBundle::class => ["all" => true],' >> config/bundles.php && \
    echo '    Symfony\Bundle\SecurityBundle\SecurityBundle::class => ["all" => true],' >> config/bundles.php && \
    echo '    Symfony\Bundle\TwigBundle\TwigBundle::class => ["all" => true],' >> config/bundles.php && \
    echo '    Doctrine\Bundle\DoctrineBundle\DoctrineBundle::class => ["all" => true],' >> config/bundles.php && \
    echo '];' >> config/bundles.php

# Installer dépendances
RUN composer install --no-dev --no-scripts --optimize-autoloader

# Dump autoload
RUN composer dump-autoload --optimize

# VÉRIFICATION du CSS
RUN echo "=== Vérification CSS ===" && \
    if [ -f "public/assets/css/dashboard.css" ]; then \
        echo "✓ CSS trouvé: public/assets/css/dashboard.css"; \
    elif [ -f "public/asset/css/dashboard.css" ]; then \
        echo "✓ CSS trouvé: public/asset/css/dashboard.css"; \
    else \
        echo "✗ CSS NON TROUVÉ"; \
        echo "Liste des fichiers CSS:"; \
        find public/ -name "*.css" 2>/dev/null; \
    fi

# Créer un fichier .htaccess dans public/ s'il n'existe pas
RUN if [ ! -f "public/.htaccess" ]; then \
        echo 'DirectoryIndex index.php' > public/.htaccess && \
        echo '' >> public/.htaccess && \
        echo '<IfModule mod_rewrite.c>' >> public/.htaccess && \
        echo '    RewriteEngine On' >> public/.htaccess && \
        echo '    RewriteCond %{REQUEST_FILENAME} -f' >> public/.htaccess && \
        echo '    RewriteRule ^ - [L]' >> public/.htaccess && \
        echo '    RewriteRule ^ index.php [L]' >> public/.htaccess && \
        echo '</IfModule>' >> public/.htaccess; \
    fi

# Permissions POUR LE CSS
RUN chown -R www-data:www-data /var/www/html && \
    chmod -R 755 /var/www/html/public && \
    find public/ -name "*.css" -exec chmod 644 {} \; 2>/dev/null || true

# Config Apache POUR SERVIR LE CSS
RUN sed -i 's!/var/www/html!/var/www/html/public!g' /etc/apache2/sites-available/000-default.conf && \
    echo '<Directory /var/www/html/public>' >> /etc/apache2/apache2.conf && \
    echo '    Options Indexes FollowSymLinks' >> /etc/apache2/apache2.conf && \
    echo '    AllowOverride All' >> /etc/apache2/apache2.conf && \
    echo '    Require all granted' >> /etc/apache2/apache2.conf && \
    echo '</Directory>' >> /etc/apache2/apache2.conf

EXPOSE 80
CMD ["apache2-foreground"]