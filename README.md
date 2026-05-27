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
