# :hamburger: Lanchonete PosFiap - Micro Pedido

![JAVA](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Banco](https://img.shields.io/badge/-MongoDB-13aa52?style=for-the-badge&logo=mongodb&logoColor=white)
![](https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Quarkus](https://img.shields.io/badge/QUARKUS-009CAB?style=for-the-badge&logo=quarkus&logoColor=white)
![Kakfa](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)

Este projeto usa Quarkus, o Supersonic Subatomic Java Framework.

Esse e um projeto do Tech Challenge fiap, sobre uma uma lanchonete de bairro que está expandindo devido seu grande
sucesso. Implementar um sistema de controle de pedidos, possa atender os clientes de maneira eficiente, gerenciando
seus pedidos e estoques de forma adequada

Esse parte se refere ao microserviçoes pedido, que cuida da parte de Pedidos e Pagamento.

## :arrow_forward: Rodando a Aplicação

Crie um .env com as seguintes informações:

```
MONGO_USER=
MONGO_PASS=
URL_MONGO=
DATABASE=
URL_BANCO=
KAFKA_URL=
```

> **NOTE**

>Para rodar **localmente** coloque .env na pasta inicial.
>
>Para rodar dentro do **container**  coloque .env na pasta *./composer*
> 
> ao rodar o script ele falara qual .env ta faltando

Para roda a aplicação localmente:

```shell script
chmod +x ./run.sh 
./run.sh
```

> **_NOTE:_**  O Quarkus agora vem com uma Dev UI, que está disponível no modo dev apenas
> em <http://localhost:8080/q/dev/>.


## :wrench: Infraestrutura

Links pra repositorios da infra do projeto (***Rodar o Projeto em Ordem***):

1. Infra Banco: https://github.com/Outrora/pos-lanchonete-banco
2. Infra do EKS: https://github.com/Outrora/pos-lanchonete-eks
3. Infra Lambda: https://github.com/Outrora/pos-lambda


Acesso ao Miro: <https://miro.com/app/board/uXjVLvULzeo=/?share_link_id=352623534822>

Link do Video : <https://youtu.be/ROI1oPu3-Go>

## :flags: Dominio 

Acesso ao Miro <https://miro.com/app/board/uXjVLcUH-oM=/?share_link_id=458579459325>

