package br.com.consultor.msg.dto;

import java.io.Serializable;

public record NotificacaoRecord(String destinatario, String mensagem) implements Serializable {}
