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

# CRÉER UN NOUVEAU FICHIER bundles.php PROPRE
RUN cat > config/bundles.php << 'EOF'
<?php

return [
    Symfony\Bundle\FrameworkBundle\FrameworkBundle::class => ['all' => true],
    Symfony\Bundle\SecurityBundle\SecurityBundle::class => ['all' => true],
    Symfony\Bundle\TwigBundle\TwigBundle::class => ['all' => true],
    Doctrine\Bundle\DoctrineBundle\DoctrineBundle::class => ['all' => true],
];
EOF

# Installer dépendances (mode production)
RUN composer install --no-dev --optimize-autoloader --no-scripts

# Nettoyer le cache
RUN rm -rf var/cache/*

# Dump autoload
RUN composer dump-autoload --optimize --classmap-authoritative

# Créer un .env.local pour la production
RUN echo "APP_ENV=prod" > .env.local && \
    echo "APP_SECRET=production_secret_$(openssl rand -base64 12)" >> .env.local

# VÉRIFIER le CSS
RUN echo "=== CSS Check ===" && \
    ls -la public/ && \
    find public/ -name "*.css" 2>/dev/null | head -5

# Permissions
RUN chown -R www-data:www-data /var/www/html && \
    chmod -R 755 /var/www/html/public

# Config Apache TRÈS SIMPLE
RUN echo '<VirtualHost *:80>' > /etc/apache2/sites-available/000-default.conf && \
    echo '    DocumentRoot /var/www/html/public' >> /etc/apache2/sites-available/000-default.conf && \
    echo '    <Directory /var/www/html/public>' >> /etc/apache2/sites-available/000-default.conf && \
    echo '        AllowOverride All' >> /etc/apache2/sites-available/000-default.conf && \
    echo '        Require all granted' >> /etc/apache2/sites-available/000-default.conf && \
    echo '    </Directory>' >> /etc/apache2/sites-available/000-default.conf && \
    echo '</VirtualHost>' >> /etc/apache2/sites-available/000-default.conf
# Activer l'affichage des erreurs PHP
RUN echo '<?php phpinfo(); ?>' > /var/www/html/public/info.php
EXPOSE 80
CMD ["apache2-foreground"]