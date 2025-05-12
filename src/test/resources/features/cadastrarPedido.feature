# language: pt
Funcionalidade: Cadastrar Pedido

  Cenario: Cadastrar um Pedido com Sucesso
    Dado que produto existe
    Dado o usuario preenche os dados do Pedido
    Quando o usuario envia o pedido
    Então o sistema cadastra o pedido