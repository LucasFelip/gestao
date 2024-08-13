import { initGraficosRelatorio } from './modules/report.js';
import {initOrcamentoRelatorio, submitOrcamentoForm} from './modules/orcamento.js';
import { initValorMinimoListeners } from './modules/valorMinimo.js';
import { carregarCategorias as carregarCategoriasGasto, submitGastoForm } from './modules/gasto.js';
import { carregarCategoriasLucro, submitLucroForm } from './modules/lucro.js';
import { loadCategorias, submitCategoriaForm } from './modules/categoria.js';  // Certifique-se de que submitCategoriaForm está sendo importado

document.addEventListener('DOMContentLoaded', function () {
    // Gráficos de Relatório
    if (document.getElementById('graficoFinanceiro') || document.getElementById('graficoComparacao')) {
        initGraficosRelatorio();
    }

    // Relatórios de Orçamento
    if (document.getElementById('orcamentoValor')) {
        initOrcamentoRelatorio();
    }

    if (document.getElementById('registerFormOrcamento')) {
        document.getElementById('registerFormOrcamento').onsubmit = submitOrcamentoForm;
    }

    // Valores Mínimos de Gasto e Lucro
    if (document.getElementById('valorMinimoGasto') || document.getElementById('valorMinimoLucro')) {
        initValorMinimoListeners();
    }

    // Formulário de Gasto
    if (document.getElementById('registerFormGasto')) {
        carregarCategoriasGasto('GASTO', 'categoriaGastoCadastrar');
        document.getElementById('registerFormGasto').onsubmit = submitGastoForm;
    }

    // Formulário de Lucro
    if (document.getElementById('registerFormLucro')) {
        carregarCategoriasLucro('LUCRO', 'categoriaLucroCadastrar');
        document.getElementById('registerFormLucro').onsubmit = submitLucroForm;
    }

    // Categorias
    if (document.getElementById('categoriasLucro') || document.getElementById('categoriasGasto')) {
        loadCategorias('LUCRO', 0, 5);
        loadCategorias('GASTO', 0, 5);
    }

    // Registro de nova categoria
    if (document.getElementById('registerFormCategory')) {
        document.getElementById('registerFormCategory').onsubmit = submitCategoriaForm;
    }
});
