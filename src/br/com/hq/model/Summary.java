package br.com.hq.model;

import br.com.hq.model.util.Category;

import java.time.Month;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Summary {
    private Float aux;
    private Month mesMaiorGasto;
    private Category categoriaMaiorGasto;
    private Map<Month, Float> gastosPorMes;
    private Map<Category, Float> gastosPorCategoria;
    private Float gastoTotal;
    private Float receitaTotal;
    private Float saldo;

    public Summary(List<Operation> list) {

        this.gastosPorMes = new HashMap<>();
        this.gastosPorCategoria = new HashMap<>();
        this.gastoTotal = Float.valueOf(0);
        this.receitaTotal = Float.valueOf(0);
        list.forEach(operation -> {
            if(operation.getValor() > Float.valueOf(0))
                this.receitaTotal += operation.getValor();
            else {
                this.gastoTotal += operation.getValor();
                this.gastosPorMes.put(operation.getData().getMonth(),
                        this.gastosPorMes.get(operation.getCategoria())
                                + operation.getValor());
                this.gastosPorCategoria.put(operation.getCategoria(),
                        this.gastosPorCategoria.get(operation.getCategoria())
                                + operation.getValor());
            }
        });

        this.aux = Float.valueOf(0);
        this.gastosPorCategoria.forEach((c, v) -> {
            if(v < this.aux) {
                this.aux = v;
                this.categoriaMaiorGasto = c;
            }
        });

        this.aux = Float.valueOf(0);
        this.gastosPorMes.forEach((m, v) -> {
            if(v < this.aux) {
                this.aux = v;
                this.mesMaiorGasto = m;
            }
        });

        this.saldo = this.receitaTotal + this.gastoTotal;
    }


    public Month getMesMaiorGasto() {
        return mesMaiorGasto;
    }

    public void setMesMaiorGasto(Month mesMaiorGasto) {
        this.mesMaiorGasto = mesMaiorGasto;
    }

    public Category getCategoriaMaiorGasto() { return categoriaMaiorGasto; }

    public void setCategoriaMaiorGasto(Category categoriaMaiorGasto) {
        this.categoriaMaiorGasto = categoriaMaiorGasto;
    }

    public Map<Month, Float> getGastosPorMes() {
        return gastosPorMes;
    }

    public void setGastosPorMes(Map<Month, Float> gastosPorMes) {
        this.gastosPorMes = gastosPorMes;
    }

    public Map<Category, Float> getGastosPorCategoria() {
        return gastosPorCategoria;
    }

    public void setGastosPorCategoria(Map<Category, Float> gastosPorCategoria) {
        this.gastosPorCategoria = gastosPorCategoria;
    }

    public Float getGastoTotal() {
        return gastoTotal;
    }

    public void setGastoTotal(Float gastoTotal) {
        this.gastoTotal = gastoTotal;
    }

    public Float getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(Float receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }
}
