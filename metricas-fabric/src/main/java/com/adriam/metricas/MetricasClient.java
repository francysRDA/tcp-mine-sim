package com.adriam.metricas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class MetricasClient implements ClientModInitializer {

    private File arquivoMonitoramento;
    private File arquivoExperimento;

    private boolean experimentoAtivo = false;

    private final String nomeSessao = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

    private int tickCounter = 0;
    private final int intervaloSegundos = 5;

    private double ultimoX = 0;
    private double ultimoY = 0;
    private double ultimoZ = 0;
    private boolean primeiraMedicao = true;

    private KeyBinding teclaExperimento;

    @Override
    public void onInitializeClient() {
        System.out.println("[MetricasFabric] Mod iniciado.");

        teclaExperimento = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.metricasfabric.experimento",
        GLFW.GLFW_KEY_F9,
        KeyBinding.Category.create(Identifier.of("metricasfabric", "categoria"))
));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) {
                return;
            }

            while (teclaExperimento.wasPressed()) {
                alternarExperimento(client);
            }

            tickCounter++;

            if (tickCounter >= intervaloSegundos * 20) {
                tickCounter = 0;

                DadosMetricas dados = coletarMetricas(client);

                salvarMetricas(client, dados, TipoColeta.MONITORAMENTO);

                if (experimentoAtivo) {
                    salvarMetricas(client, dados, TipoColeta.EXPERIMENTO);
                }
            }
        });
    }

    private void alternarExperimento(MinecraftClient client) {
        experimentoAtivo = !experimentoAtivo;

        if (experimentoAtivo) {
            arquivoExperimento = new File(
                    client.runDirectory,
                    "metricas_experimento_" + LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".csv"
            );

            primeiraMedicao = true;
            System.out.println("[MetricasFabric] Experimento iniciado.");
        } else {
            System.out.println("[MetricasFabric] Experimento finalizado.");
        }
    }

    private DadosMetricas coletarMetricas(MinecraftClient client) {
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

        return new DadosMetricas(
                LocalDateTime.now().toString(),
                ping,
                ping,
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
    }

    private void salvarMetricas(MinecraftClient client, DadosMetricas dados, TipoColeta tipo) {
        try {
            File arquivo;

            if (tipo == TipoColeta.MONITORAMENTO) {
                if (arquivoMonitoramento == null) {
                    arquivoMonitoramento = new File(
                            client.runDirectory,
                            "metricas_monitoramento_" + nomeSessao + ".csv"
                    );
                }

                arquivo = arquivoMonitoramento;
            } else {
                arquivo = arquivoExperimento;
            }

            if (arquivo == null) {
                return;
            }

            boolean novoArquivo = !arquivo.exists();

            FileWriter writer = new FileWriter(arquivo, true);

            if (novoArquivo) {
                writer.write("timestamp,tipo_coleta,ping_ms,rtt_estimado_ms,pos_x,pos_y,pos_z,fps,chunks_carregados,entidades_carregadas,velocidade_blocos_s,ram_usada_mb,ram_total_mb\n");
            }

            String linha = String.format(
                    Locale.US,
                    "%s,%s,%d,%d,%.2f,%.2f,%.2f,%d,%d,%d,%.2f,%d,%d%n",
                    dados.timestamp,
                    tipo.nomeCsv,
                    dados.pingMs,
                    dados.rttEstimadoMs,
                    dados.posX,
                    dados.posY,
                    dados.posZ,
                    dados.fps,
                    dados.chunksCarregados,
                    dados.entidadesCarregadas,
                    dados.velocidadeBlocosS,
                    dados.ramUsadaMb,
                    dados.ramTotalMb
            );

            writer.write(linha);
            writer.close();

            System.out.println("[MetricasFabric] " + linha);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private enum TipoColeta {
        MONITORAMENTO("monitoramento"),
        EXPERIMENTO("experimento");

        public final String nomeCsv;

        TipoColeta(String nomeCsv) {
            this.nomeCsv = nomeCsv;
        }
    }

    private static class DadosMetricas {
        String timestamp;
        int pingMs;
        int rttEstimadoMs;
        double posX;
        double posY;
        double posZ;
        int fps;
        int chunksCarregados;
        int entidadesCarregadas;
        double velocidadeBlocosS;
        long ramUsadaMb;
        long ramTotalMb;

        DadosMetricas(
                String timestamp,
                int pingMs,
                int rttEstimadoMs,
                double posX,
                double posY,
                double posZ,
                int fps,
                int chunksCarregados,
                int entidadesCarregadas,
                double velocidadeBlocosS,
                long ramUsadaMb,
                long ramTotalMb
        ) {
            this.timestamp = timestamp;
            this.pingMs = pingMs;
            this.rttEstimadoMs = rttEstimadoMs;
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.fps = fps;
            this.chunksCarregados = chunksCarregados;
            this.entidadesCarregadas = entidadesCarregadas;
            this.velocidadeBlocosS = velocidadeBlocosS;
            this.ramUsadaMb = ramUsadaMb;
            this.ramTotalMb = ramTotalMb;
        }
    }
}