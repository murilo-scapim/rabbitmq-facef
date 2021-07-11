# Parking Lot Queues
Realizar a implementação de um cenário de parking-lot baseado no exemplo 6 (DLQ).

Iniciar o RabbitMQ:
```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

Após o início do container, podemos acessar a URL do admin através do endereço: http://localhost:15672/ 
usando username: guest e password: guest

Executar o projeto spring:
```
./mvnw clean spring-boot:run
```

Executar no terminal usando o curl:
```
curl http://localhost:8080/messages/send
```
