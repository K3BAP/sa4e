FROM node:22

WORKDIR /app

COPY package*.json ./
COPY docker.env ./.env
COPY tsconfig.json ./
COPY src ./src

RUN npm install
RUN npm run build

EXPOSE 3000

CMD ["npm", "run", "start"]
