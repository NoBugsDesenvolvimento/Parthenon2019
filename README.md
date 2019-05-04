# Parthenon

App para gerenciamento de Eventos em geral. Divido em
módulos que podem ser contratados pelos clientes.

## Descrição detalhada

Descrições das funcionalidades, propriedades da aplicação,
e especificações para facilitar a compreensão e desenvolvimento.

### Funcionalidades

#### App

Especificações do app que será utilizado pelos participantes.
Estão listadas abaixo as funcionalidades primárias e secundárias
encontradas durante o processo de análise.

##### Primárias

* Registro de usuário : Devem ser fornecidos CPF(chave primária),
Nome completo, Email, Data de nascimento, Foto de perfil(não obrigatório)
e telefone(não obrigatório). Os dados devem ser enviados para o sistema,
que irá registrar os dados. 
* Login : Todos os níveis de usuários podem se conectar ao app.
O login pode ser feito via email ou CPF. A validação dos dados será
feito pelo sistema de gerenciamento.
* Exibição de programação : Pode ser filtrada por dia, horário,
palestrante e/ou favoritas.
* Perguntas frequentes(FAQ): Informações importantes sobre o
evento que os participantes podem precisar, a serem definidas
pelo cliente.
* Registro de Presença : O registro de presença de participantes
pode ser feito por CPF ou pelo QR Code.
* Mapa do evento : Exibição do local do evento e das programações, caso
hajam mais de um local. É possível exibir mudanças de local e notificar
os participantes.
* Feed de postagens : Os gerenciadores do evento podem realizar postagens
com informações, dicas, promoções e avisos sobre mudanças. As postagens
podem estar relacionadas com tópicos para notificar os participantes.
* Notificações : Os participantes devem ser notificados caso haja alguma
alteração nas programações ou nova postagem em algum tópico seguido. Os
gerenciadores do evento também podem emitir notificações especificas
com um link externo ou para um link dentro do próprio app.

##### Secundárias

* Enviar perguntas para uma palestra : Os participantes podem enviar
perguntas para uma determinada programação. Apenas participantes que 
tiverem sua presença registrada em determinada programação poderão 
enviar uma pergunta. Os admin's e staff's poderão excluir uma pergunta
caso seja necessário.
* Chat : O chat possibilita a comunicação entre participantes. Qualquer
participante pode enviar e receber mensagens.
* Chat em grupo : Possibilidade de criação de um chat em grupo. O ingresso
em um grupo pode exigir autenticação por senha.

#### Sistema de gerenciamento

O sistema de gerenciamento irá controlar todo o fluxo de dados usuário-usuário,
administrador-usuário e servidor-usuário. Deve possuir uma API e também interface visual para facilitar o controle. Somente os administradores e staff's poderão
ter acesso ao sistema.

Deve haver um sistema de verificação por token, para facilitar as requisições e
aumentar o nível de segurança. O token é gerado através dos dados do usuário, em
cada requisição, e fornecido ao app.

##### Primárias

* Registro de usuário : Devem ser fornecidos pelo app o CPF(chave primária),
Nome completo, Email, Data de nascimento, Foto de perfil(não obrigatório)
e telefone(não obrigatório). O CPF será validado dentro do próprio app, então
não há necessidade de validação. O sistema gerá um token por usuário(em tempo
de execução).
* Login : Os dados de login serão fornecidos pelo app através de requisição.
Caso as informações estejam corretas, o sistema retorna o nome, token, e-mail
e foto de perfil.
* Geração de certificado : O sistema de gerenciamento poderá gerar
um certificado de participação, contendo as assinaturas necessárias,
plano de fundo fornecido pelo cliente e nome do participante. O certificado
pode ser encaminhado automaticamente para o email do congressista, individualmente
ou selecionando determinados congressistas, podendo ser enviado para todos.**OBS:**
Podem ser feitas modificações em caso de necessidade.
* Registro de Presença : O registro de presença de participantes será feito
pela leitura do QR Code no App e enviado para o sistema, que irá registrar a presença.
* Notificações : As notificações poderão ser enviadas pelo sistema, para determinado
tópico, postagen ou de maneira geral.

##### Secundárias

* Chat: O chat pode ser administrado pelo sistema de gerenciamento, podendo
visualizar a quantidade de mensagens, data de criação e quantidade de usuários
registrados.