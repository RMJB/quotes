FROM node:alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build


FROM nginx:alpine
COPY --from=build /app/dist/quote-generator/browser /usr/share/nginx/html
EXPOSE 4200
RUN sed -i -e 's/80/4200/g' /etc/nginx/conf.d/default.conf
CMD ["nginx", "-g", "daemon off;"]
