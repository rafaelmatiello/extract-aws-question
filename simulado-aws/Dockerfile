# Estagio 2 - Será responsavel por expor a aplicação
FROM nginx:1.13

# Replace variables in env.js
COPY dist/simulado-aws /usr/share/nginx/html/simulado
COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf

CMD ["/bin/sh",  "-c",  "envsubst < /usr/share/nginx/html/simulado/assets/env.template.js > /usr/share/nginx/html/simulado/assets/env.js && exec nginx -g 'daemon off;'"]
