README - Home Broker

## Introdução

Este é um projeto de um Home Broker desenvolvido em Spring Boot. O objetivo do sistema é permitir que os clientes possam realizar transações de compra e venda de ações e outros ativos financeiros de forma online.

## Funcionalidades

A seguir, estão listadas as principais funcionalidades do sistema:

- Autenticação e autorização: O sistema possui um sistema de login que verifica as permissões dos usuários. As permissões são gerenciadas de forma granular, permitindo diferentes níveis de acesso às funcionalidades do Home Broker, baseados em papéis e permissões específicas.

- Compra e venda de ações: Foi implementada a lógica para que os usuários possam comprar e vender ações. Isso inclui cálculos de valores, comissões e atualizações de saldo. Também é realizada uma verificação adequada para garantir que o saldo do usuário seja suficiente para realizar a transação, considerando todas as taxas aplicáveis.

- Consulta de cotações: O sistema foi integrado com uma API de cotações, permitindo que os usuários possam consultar os preços das ações em tempo real.

- Acompanhamento de carteira: Os usuários podem visualizar suas ações, saldos, histórico de transações e informações relacionadas às suas posições. Além das informações básicas das ações e saldos, são fornecidas informações mais detalhadas, como valor total da carteira, variação percentual do lucro ou prejuízo, gráficos de desempenho, histórico completo de transações e a possibilidade de exportar relatórios. Essas informações permitem que os usuários tenham uma visão completa e clara de suas posições e tomem decisões informadas sobre seus investimentos.

## Entidades

O sistema possui as seguintes entidades:

- Usuário: Representa os clientes do Home Broker. Possui informações pessoais, como nome, email, CPF, telefone, endereço e senha.

- Ação: Representa uma na qual os usuários podem investir. Possui informações como nome, setor de atuação, histórico de preços, código e preço atual.

- Transação: Representa compra ou venda de ação. Contém informações como quantidade, tipo de transação (compra ou venda), status da transação, preço, data e hora.

- Saldo: Representa o saldo disponível do usuário para investir. Pode ser atualizado de acordo com as transações, depósitos e retiradas. Possui informações como data de última atualização e depósitos efetuados.

## Relacionamentos no Banco de Dados

- O Usuário possui uma relação de 1 para muitos com Transação. Isso significa que um usuário pode realizar várias transações de compra ou venda de ações.

- A Ação possui uma relação de 1 para muitos com Transação. Ou seja, uma ação pode estar envolvida em várias transações diferentes.

- O Usuário possui uma relação de 1 para 1 com Saldo. Cada usuário tem seu próprio saldo disponível para investir.

- A Transação possui uma relação de muitos para 1 com Saldo. Uma transação pode afetar o saldo disponível do usuário.

## Como executar o projeto?

1. Faça o clone do repositório para a sua máquina.

2. Certifique-se de ter o Spring Boot instalado.

3. Importe o projeto em sua IDE favorita.

4. Configure as dependências necessárias, como banco de dados e API de cotações.

5. Execute o projeto e acesse a aplicação pelo seu navegador.

## Conclusão

Esse é um projeto básico de um Home Broker com funcionalidades essenciais. Porém, é importante ressaltar que, dependendo dos requisitos do seu projeto e das necessidades específicas, podem ser adicionadas outras regras de negócios e entidades.