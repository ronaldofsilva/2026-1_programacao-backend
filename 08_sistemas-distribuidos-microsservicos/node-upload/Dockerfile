# Imagem base leve Alpine
FROM node:18-alpine
# Diretório de trabalho no container
WORKDIR /app
# Copia manifesto e instala dependências
COPY package*.json ./
# RUN npm install --production
RUN npm install --omit=dev
# Copia código-fonte
COPY . .
# Cria diretório de uploads dentro do container
RUN mkdir -p /app/uploads
# Expõe porta do serviço
EXPOSE 3000
CMD ["node", "server.js"]