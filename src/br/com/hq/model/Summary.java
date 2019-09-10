package br.com.hq.model;

import br.com.hq.model.util.Category;

import java.time.Month;
import java.util.*;

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

        this.gastosPorMes = new LinkedHashMap<>();
        this.gastosPorCategoria = new LinkedHashMap<>();
        this.gastoTotal = Float.valueOf(0);
        this.receitaTotal = Float.valueOf(0);
        list.forEach(operation -> {
            Float valor = operation.getValor();
            if(valor > Float.valueOf(0))  // It's income?
                this.receitaTotal += valor;
            else { // No it's spent.
                this.gastoTotal += valor;
                this.addEmGastosPorMesECategoria(operation);
            }
        });
        this.gastosPorCategoria
                = this.sortCategoryByValue(this.gastosPorCategoria);

        this.gastosPorMes
                = this.sortMesByValue(this.gastosPorMes);

        this.saldo = this.receitaTotal + this.gastoTotal;
    }

    private void addEmGastosPorMesECategoria(Operation operation) {

        Month month = operation.getData().getMonth();
        Category categoria = operation.getCategoria();
        Float valor = operation.getValor();

        this.gastosPorMes.put(month,
                (this.gastosPorMes.containsKey(month)) ?
                        (this.gastosPorMes.get(month) + valor)
                        : valor);
        this.gastosPorCategoria.put(categoria,
                (this.gastosPorCategoria.containsKey(categoria)) ?
                        (this.gastosPorCategoria.get(categoria) + valor)
                        : valor);
    }

    private void setMaxValueInCategoria(Map<Category, Float> map) {
        this.aux = Float.valueOf(0);
        map.forEach((c, v) -> {
            if(v < this.aux) {
                this.aux = v;
                this.categoriaMaiorGasto = c;
            }
        });
    }

    private void setMaxValueInMes(Map<Month, Float> map) {
        this.aux = Float.valueOf(0);
        map.forEach((m, v) -> {
            if(v < this.aux) {
                this.aux = v;
                this.mesMaiorGasto = m;
            }
        });
    }

    private Map<Category, Float> sortCategoryByValue(Map<Category, Float> map) {

        Map<Category, Float> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        this.categoriaMaiorGasto = (Category) result.keySet().toArray()[0];
        return result;
    }

    private Map<Month, Float> sortMesByValue(Map<Month, Float> map) {

        Map<Month, Float> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        this.mesMaiorGasto = (Month) result.keySet().toArray()[0];
        return result;
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

    public String getGastosPorMesToString() {

        StringBuffer buffer = new StringBuffer();
        gastosPorMes.forEach((c, v) ->
                buffer.append(" - " + c + ": " + v + "\n"));
        return buffer.toString();
    }

    public void setGastosPorMes(Map<Month, Float> gastosPorMes) {
        this.gastosPorMes = gastosPorMes;
    }

    public Map<Category, Float> getGastosPorCategoria() {
        return gastosPorCategoria;
    }

    public String getGastosPorCategoriaToString() {

        StringBuffer buffer = new StringBuffer();
        gastosPorCategoria.forEach((c, v) ->
                buffer.append(" - " + c.getName() + ": " + v + "\n"));
        return buffer.toString();
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
