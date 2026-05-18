# tcp-mine-sim
# Avaliação de desempenho de comunicação cliente-servidor em jogos online utilizando TCP: simulação baseada no Minecraft

Este projeto simula a comunicação cliente-servidor de um jogo online (inspirado em Minecraft) utilizando sockets TCP em Python. O objetivo é analisar o desempenho da comunicação, medindo a latência e o tempo de resposta em diferentes cenários.

## Integrantes
- Adriam Machado de Souza (Líder)
- Francys Ribeiro dos Anjos
- Antone Bilheri Salbego

## Disciplina
Redes de Computadores  
Professor: Rodrigo Mansilha

## Tema
Avaliação de desempenho de comunicação cliente-servidor em jogos online utilizando TCP, com simulação baseada no Minecraft.

## Estrutura do Projeto

- `server.py`: Servidor TCP que gerencia múltiplas conexões de clientes e processa comandos simples.
- `client.py`: Cliente TCP que se conecta ao servidor, envia comandos (movimentação, ping, chat) e mede a latência de cada resposta.
- `performance_tests.py`: Script para automação de testes de desempenho com múltiplos clientes simultâneos.

## Como Executar

1. **Iniciar o Servidor:**
   ```bash
   python server.py
   ```

2. **Executar um Cliente Individual (Opcional):**
   ```bash
   python client.py
   ```

3. **Executar Testes de Desempenho:**
   ```bash
   python performance_tests.py
   ```

## Resultados Iniciais (Localhost)

Em testes realizados no ambiente local com 2 clientes simultâneos e 20 iterações cada, obtivemos os seguintes resultados:

- **Total de comandos enviados:** 40
- **Latência Mínima:** 0.15ms
- **Latência Máxima:** 2.38ms
- **Latência Média:** 0.45ms
- **Desvio Padrão:** 0.45ms

Estes resultados demonstram a eficiência do protocolo TCP em redes locais, mantendo a latência bem abaixo dos limites perceptíveis para jogadores (geralmente < 50ms).

## Próximas Etapas Sugeridas

- Simular latência artificial (atraso no servidor ou rede).
- Testar com um número maior de clientes simultâneos.
- Implementar comandos mais complexos e sincronização de estado.

## Links
- **GitHub:** [https://github.com/francysRDA/tcp-mine-sim](https://github.com/francysRDA/tcp-mine-sim)
