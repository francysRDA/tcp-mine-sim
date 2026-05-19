import pandas as pd

df = pd.read_csv("metricas_minecraft.csv")

# remove medições inválidas de ping
df = df[df["ping_ms"] > 0]

# converte timestamp
df["timestamp"] = pd.to_datetime(df["timestamp"])

print("Ping médio:", df["ping_ms"].mean())
print("Ping mínimo:", df["ping_ms"].min())
print("Ping máximo:", df["ping_ms"].max())

print("FPS médio:", df["fps"].mean())
print("Chunks médio:", df["chunks_carregados"].mean())
print("Entidades média:", df["entidades_carregadas"].mean())