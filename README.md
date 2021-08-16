# dio-micro-servicos

Neste projeto, implementei o conteúdo aprendido até o módulo de Arquitetura Baseada em Micro Serviços do Santander Bootcamp Fullstack Developer, na forma de um breve catálogo de produtos e um carrinho de compras.

![chrome_80DJEq1GNr](https://i.imgur.com/QZ3FGz4.png)

> Imagem de uma simples implementação funcional em HTML/JS, presente no arquivo [index.html](https://github.com/arj-mat/dio-micro-servicos/blob/main/gateway/src/main/resources/static/index.html) dentro da pasta resources/static do projeto do Gateway.

## Alguns pontos importantes que segui implementar diferentemente do que foi ensinado na mentoria

### Bancos de dados diferentes

Diferente dos mecanismos ElasticSearch e Redis usados na mentoria, optei por implementar no projeto um banco de dados local PostgreSQL, para o catálogo de produtos, e um banco in-memory H2 para o carrinho.

### Recursos 

Implementei alguns conceitos vistos ao longo do curso, como Lists, Collections, HttpRequest, Jackson, JPA, MapStruct e Lombok, em especial no [CartService](https://github.com/arj-mat/dio-micro-servicos/blob/main/shopcart/src/main/java/com/santander/microsservicos/shopcart/data/service/CartService.java).

### Endpoints

<img src="https://i.imgur.com/oHRyDGU.png" alt="chrome_0XFQ2iLsw6" style="zoom:67%;" />

O endpoint `cart/{cart}` retorna o carrinho especificado. Caso ele não exista, um carrinho novo é criado e retornado. Os carrinhos possuem uma validade de 24 horas antes de serem [considerados](https://github.com/arj-mat/dio-micro-servicos/blob/main/shopcart/src/main/java/com/santander/microsservicos/shopcart/data/service/CartService.java#L51) obsoletos.

O endpoint `cart/{cart}/update?product_id={id}&amount={amount}` atualiza a quantidade do produto especificado no carrinho, utilizando a função *[stream().peek](https://github.com/arj-mat/dio-micro-servicos/blob/main/shopcart/src/main/java/com/santander/microsservicos/shopcart/data/service/CartService.java#L157)* na lista de produtos.

O endpoint `cart/{cart}/checkout` soma o valor total dos produtos no carrinhos utilizando as funções [*stream().map* e *reduce*](https://github.com/arj-mat/dio-micro-servicos/blob/main/shopcart/src/main/java/com/santander/microsservicos/shopcart/data/service/CartService.java#L188) na lista de produtos. 

Já o endpoint `cart/{cart}/insert?product_id={id}` insere o produto especificado no carrinho, [obtendo](https://github.com/arj-mat/dio-micro-servicos/blob/main/shopcart/src/main/java/com/santander/microsservicos/shopcart/data/service/CartService.java#L86) as informações do produto com o [ShopCatalog](https://github.com/arj-mat/dio-micro-servicos/blob/main/shopcatalog/src/main/java/com/santander/microsservicos/shopcatalog/controller/CatalogController.java#L27), utilizando uma [classe especial](https://github.com/arj-mat/dio-micro-servicos/blob/main/shopcart/src/main/java/com/santander/microsservicos/shopcart/external/ExternalRequest.java) para requisições JSON com modelagem que consegui implementar após algumas pesquisas. 

Ao adicionar um produto, [caso ele já esteja no carrinho, então o produto tem a sua quantidade acrescida](https://github.com/arj-mat/dio-micro-servicos/blob/main/shopcart/src/main/java/com/santander/microsservicos/shopcart/data/service/CartService.java#L110) ao invés de ser duplicado.

