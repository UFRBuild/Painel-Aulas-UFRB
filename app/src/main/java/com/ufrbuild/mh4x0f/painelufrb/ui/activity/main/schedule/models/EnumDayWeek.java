package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models;

public enum EnumDayWeek {
    SEGUNDA("SEG"), TERCA("TER"),
    QUARTA("QUA"), QUINTA("QUI"),
    SEXTA("SEX") , SABADO("SÁB");

    private final String valor;
    EnumDayWeek(String valorOpcao){
        valor = valorOpcao;
    }
    public String getValor(){
        return valor;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
