package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models;

public enum SemanaEnum {
    SEGUNDA(2), TERCA(3),
    QUARTA(4), QUINTA(5),
    SEXTA(6) , SABADO(7);

    private final int valor;
    SemanaEnum(int valorOpcao){
        valor = valorOpcao;
    }
    public int getValor(){
        return valor;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
