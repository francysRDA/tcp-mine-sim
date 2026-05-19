# Avaliação de desempenho cliente-servidor em Minecraft

Este repositório contém os arquivos do projeto **Avaliação de desempenho de comunicação cliente-servidor em jogos online utilizando TCP: estudo baseado no Minecraft**.

O objetivo do projeto é analisar métricas relacionadas à comunicação cliente-servidor em um ambiente multiplayer real baseado em Minecraft, utilizando um servidor com Fabric e conexão via Radmin VPN.

## Integrantes

- Adriam Machado de Souza (Líder)
- Francys Ribeiro dos Anjos
- Antone Bilheri Salbego

## Objetivo

Inicialmente, o projeto foi pensado como uma simulação simples utilizando sockets TCP em Python. Porém, durante o desenvolvimento, percebeu-se que uma simulação desse tipo não representaria adequadamente o funcionamento real de uma sessão multiplayer de Minecraft, principalmente em um servidor com mods.

Por isso, a proposta evoluiu para uma abordagem mais próxima do uso real: utilizar o próprio cliente Minecraft com Fabric e desenvolver um mod client-side para coletar métricas durante a jogatina.

## Tecnologias utilizadas

- Minecraft Java Edition
- Fabric Loader
- Fabric API
- Fabric Loom
- Gradle
- Java
- Radmin VPN
- Python para análise posterior dos dados
- CSV para armazenamento das métricas

## Estrutura do projeto

```txt
.
├── bot-metricas/
│   └── metricas-fabric/
│       ├── src/
│       ├── build.gradle
│       ├── gradle.properties
│       └── README.md
├── README.md
└── .gitignore