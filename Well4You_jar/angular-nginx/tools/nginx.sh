#!/bin/bash

sed -i -r "s#443#$CLIENT_PORT#g"    /etc/nginx/nginx.conf

sed -i -r "s#first#$CERTS_#g"    /etc/nginx/nginx.conf

sed -i -r "s#second#$KEYS_#g"    /etc/nginx/nginx.conf

chmod 755 /var/www/angular/
chown -R www-data:www-data /var/www/angular/

nginx -g "daemon off;"
