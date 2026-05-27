# Avaliação de desempenho cliente-servidor em Minecraft

Este repositório contém os arquivos do projeto **Avaliação de desempenho de comunicação cliente-servidor em jogos online utilizando TCP: estudo baseado no Minecraft**.

O objetivo do projeto é analisar métricas relacionadas à comunicação cliente-servidor em um ambiente multiplayer real baseado em Minecraft, utilizando um servidor com Fabric e conexão via Radmin VPN.

---

# Integrantes

- Adriam Machado de Souza (Líder)
- Francys Ribeiro dos Anjos
- Antone Bilheri Salbego

---

# Objetivo

Inicialmente, o projeto foi pensado como uma simulação simples utilizando sockets TCP em Python. Porém, durante o desenvolvimento, percebeu-se que uma simulação desse tipo não representaria adequadamente o funcionamento real de uma sessão multiplayer de Minecraft, principalmente em servidores modificados utilizando Fabric e diversos mods.

Por isso, a proposta evoluiu para uma abordagem mais próxima de um ambiente real de jogo: utilizar o próprio cliente Minecraft com Fabric e desenvolver um mod client-side responsável pela coleta automática de métricas durante a jogatina.

Essa abordagem permite observar comportamentos relacionados à comunicação cliente-servidor em condições mais próximas às encontradas em sessões multiplayer reais.

---

# Tecnologias utilizadas

- Minecraft Java Edition 1.21.11
- Fabric Loader
- Fabric API
- Fabric Loom
- Gradle
- Java JDK 25.0.2
- Radmin VPN
- Python
- CSV para armazenamento das métricas

---

# Estrutura do projeto

```txt
.
├── metricas-fabric/
│   ├── src/
│   ├── gradle/
│   ├── build.gradle
│   ├── gradle.properties
│   ├── settings.gradle
│   └── README.md
├── README.md
└── .gitignore
```

---

# Funcionalidades do mod

O mod desenvolvido realiza coleta automática de métricas relacionadas ao desempenho do cliente e da comunicação com o servidor.

Atualmente o sistema coleta:

- Ping;
- RTT estimado;
- Coordenadas do jogador;
- FPS;
- Quantidade de chunks carregados;
- Quantidade de entidades carregadas;
- Velocidade de movimentação;
- Uso de memória RAM.

Os dados são armazenados automaticamente em arquivos CSV.

---

# Modos de coleta

O mod possui dois modos de funcionamento.

## 1. Monitoramento automático

O monitoramento é iniciado automaticamente ao entrar em um mundo ou servidor.

Os dados são armazenados em arquivos como:

```txt
metricas_monitoramento_2026-05-19_20-10-00.csv
```

Esse modo é utilizado para registrar toda a sessão de jogo.

---

## 2. Modo experimental

O modo experimental pode ser ativado manualmente utilizando a tecla:

```txt
F9
```

Funcionamento:

```txt
F9 → iniciar experimento
F9 → finalizar experimento
```

Quando ativo, os dados são armazenados separadamente em arquivos como:

```txt
metricas_experimento_2026-05-19_20-15-30.csv
```

Esse modo foi implementado para permitir experimentos mais controlados, reduzindo interferências causadas pelo carregamento inicial do mundo e possíveis efeitos de cache.

---

# Formato dos dados

Exemplo de cabeçalho dos arquivos CSV:

```csv
timestamp,tipo_coleta,ping_ms,rtt_estimado_ms,pos_x,pos_y,pos_z,fps,chunks_carregados,entidades_carregadas,velocidade_blocos_s,ram_usada_mb,ram_total_mb
```

Exemplo de linha:

```csv
2026-05-19T20:15:30,experimento,18,18,-938.14,63.00,-339.07,145,190,246,4.42,2713,3808
```

---

# Cenários de teste planejados

| Cenário | Objetivo |
|----------|-----------|
| Jogador parado | Observar comportamento em baixa atividade |
| Exploração de área já carregada | Avaliar pequenas variações |
| Exploração de novos chunks | Avaliar aumento no tráfego de dados |
| Múltiplos jogadores simultâneos | Verificar aumento da carga |
| Regiões com muitas entidades | Avaliar sincronização entre cliente e servidor |

---

# Requisitos

Para compilar o projeto é necessário possuir:

- Java JDK 25.0.2
- Minecraft 1.21.11
- Fabric Loader
- Fabric API

Verifique a instalação do Java:

```bash
java -version
javac -version
```

Saída esperada:

```txt
java version "25.0.2"
javac 25.0.2
```

---

# Dependências utilizadas

## Minecraft

```properties
minecraft_version=1.21.11
```

## Yarn Mappings

```properties
yarn_mappings=1.21.11+build.1
```

## Fabric Loader

```properties
loader_version=0.19.2
```

## Fabric API

```properties
fabric_api_version=0.140.0+1.21.11
```

## Fabric Loom

```properties
loom_version=1.16.2
```

---

# Como compilar o mod

Entre na pasta do projeto:

```bash
cd metricas-fabric
```

No Windows PowerShell:

```powershell
.\gradlew build
```

Ou para rebuild completo:

```powershell
.\gradlew clean build
```

O arquivo `.jar` será gerado em:

```txt
build/libs/
```

Exemplo:

```txt
metricas-fabric-1.0.1.jar
```

---

# Como instalar o mod

Copie o `.jar` gerado para:

```txt
.minecraft/mods/
```

O Minecraft deve estar utilizando:

- Fabric Loader;
- Minecraft 1.21.11;
- Java JDK 25.0.2.

---

# Como utilizar

## Monitoramento automático

Basta entrar no servidor.

O arquivo CSV será criado automaticamente.

---

## Modo experimental

1. Entrar no servidor;
2. Esperar o ambiente estabilizar;
3. Pressionar `F9`;
4. Executar o cenário experimental;
5. Pressionar `F9` novamente para finalizar.

---

# Análise dos dados

Os arquivos CSV serão analisados posteriormente utilizando Python.

As análises planejadas incluem:

- média de ping;
- máximo e mínimo de latência;
- variação de FPS;
- comparação entre cenários;
- geração automática de gráficos.

Bibliotecas Python planejadas:

```bash
pip install pandas matplotlib numpy
```

---

# Créditos

Este projeto foi desenvolvido utilizando como base a estrutura do:

- Fabric Example Mod  
https://github.com/FabricMC/fabric-example-mod

Ferramentas utilizadas:

- Fabric Loader
- Fabric API
- Fabric Loom
- Gradle

O código foi adaptado para fins acadêmicos com foco na coleta de métricas em um ambiente multiplayer baseado em Minecraft.