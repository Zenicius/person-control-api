# person-control-api
API simples para gerenciar pessoas

### Endpoints
| Method | Url | Descrição |
| ------ | --- | ---------- |
| POST    |/persons  | Cria uma nova pessoa |
| POST    |/persons/{id}/addresses| Cria um novo endereço para determinada pessoa |
| GET    |/persons   | Obtem todas as pessoas |
| GET    |/persons/{id}| Obtem determinada pessoa por id |
| GET    |/persons/{id}/addresses| Obtem todos os endereços de uma determinada pesssoa |
| GET    |/persons/{id}/addresses/{addressId}   | Obtem um determinado endereço por id|
| PUT    |/persons/{id}     | Atualiza determinada pessoa por id|
| PUT    |/persons/{id}/addresses/{addressId}     | Atualiza determinado endereço por id|
| PUT    |/persons/{id}/addresses/{addressId}/main     | Atualiza endereço principal por id|
| DELETE    |/persons/{id}     | Deleta determinada pessoa por id|
| DELETE    |/persons/{id}/addresses/{addressId}     | Deleta determinado endereço por id|
