package com.adriam.metricas;

import java.util.Locale;
import java.time.format.DateTimeFormatter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class MetricasClient implements ClientModInitializer {

    private File arquivoSessao;

    private final String nomeSessao = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    private int tickCounter = 0;
    private final int intervaloSegundos = 5;

    private double ultimoX = 0;
    private double ultimoY = 0;
    private double ultimoZ = 0;
    private boolean primeiraMedicao = true;

    @Override
    public void onInitializeClient() {
        System.out.println("[MetricasFabric] Mod iniciado.");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) {
                return;
            }

            tickCounter++;

            if (tickCounter >= intervaloSegundos * 20) {
                tickCounter = 0;
                salvarMetricas(client);
            }
        });
    }

    private void salvarMetricas(MinecraftClient client) {
        try {
           if (arquivoSessao == null) {
    arquivoSessao = new File(
            client.runDirectory,
            "metricas_minecraft_" + nomeSessao + ".csv"
    );
}

    boolean novoArquivo = !arquivoSessao.exists();

    FileWriter writer = new FileWriter(arquivoSessao, true);

            if (novoArquivo) {
                writer.write("timestamp,ping_ms,rtt_estimado_ms,pos_x,pos_y,pos_z,fps,chunks_carregados,entidades_carregadas,velocidade_blocos_s,ram_usada_mb,ram_total_mb\n");
            }

            int ping = 0;

        if (client.player != null && client.getNetworkHandler() != null) {
            String nomeJogador = client.player.getName().getString();

            for (PlayerListEntry entry : client.getNetworkHandler().getPlayerList()) {
        String nomeEntry = entry.getProfile().name();

        if (nomeEntry.equals(nomeJogador)) {
            ping = entry.getLatency();
            break;
        }
    }
}

            int rttEstimado = ping;

            double x = client.player.getX();
            double y = client.player.getY();
            double z = client.player.getZ();

            int fps = client.getCurrentFps();

            int chunksCarregados = client.worldRenderer.getCompletedChunkCount();

            int entidadesCarregadas = 0;
            for (Entity entidade : client.world.getEntities()) {
                entidadesCarregadas++;
            }

            double velocidade = 0;

            if (!primeiraMedicao) {
                double dx = x - ultimoX;
                double dy = y - ultimoY;
                double dz = z - ultimoZ;

                double distancia = Math.sqrt(dx * dx + dy * dy + dz * dz);
                velocidade = distancia / intervaloSegundos;
            }

            primeiraMedicao = false;
            ultimoX = x;
            ultimoY = y;
            ultimoZ = z;

            Runtime runtime = Runtime.getRuntime();

            long memoriaTotal = runtime.totalMemory();
            long memoriaLivre = runtime.freeMemory();
            long memoriaUsada = memoriaTotal - memoriaLivre;

            long ramUsadaMb = memoriaUsada / (1024 * 1024);
            long ramTotalMb = memoriaTotal / (1024 * 1024);

            String linha = String.format(
            Locale.US,
            "%s,%d,%d,%.2f,%.2f,%.2f,%d,%d,%d,%.2f,%d,%d%n",
            LocalDateTime.now(),
            ping,
            rttEstimado,
            x,
            y,
            z,
            fps,
            chunksCarregados,
            entidadesCarregadas,
            velocidade,
            ramUsadaMb,
            ramTotalMb
                );

            writer.write(linha);
            writer.close();

            System.out.println("[MetricasFabric] " + linha);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}