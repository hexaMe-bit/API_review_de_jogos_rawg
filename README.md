## API de criação de reviews de jogos utilizando a api externa RAWG.io

o intuito aqui é apenas criar uma api básica com alguns endpoints prontos outros em necessidade de refatoração e correção de código. no futuro vou adicionar lógica de controle de usuários e autenticação.


## camadas

o projeto tem várias camadas desde DTOs para não expor entidades do banco de dados. camadas de serviço para controle da lógica de implementação, e a de controle que é basicamente a camada mais próxima das requisicao como o corpo de uma requisicao é formado e etc. ainda há muita coisa desnecessária na api mas eu não pretendo tirar pois sei exatamente o que farei com isso.

## sobre o projeto
eu basicamente comecei esse projeto com a decisão de aprender algo novo ou quase totalmente novo. o escopo da api foi decidido no meu tempo livre utilizando a linguagem java com o framework spring boot. 

## mudanças

agora a partir da rota post é possivel fazer uma busca na api externa da qual estou utilizando para obter o id e salvar apenas o id no banco de dados. correcao futura: o processo inverso da rota post a partir do id retornar o nome do jogo no encima do qual foi feito a review.
