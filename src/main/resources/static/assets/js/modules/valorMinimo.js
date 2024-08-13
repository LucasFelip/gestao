import { carregarDados, formatarValor } from './utils.js';

function carregarAcimaDe(tipo, valor, displayId) {
    carregarDados(`/${tipo}/valor?valor=${valor}`).then(data => {
        const display = document.getElementById(displayId);

        if (data.length === 0) {
            display.textContent = `Nenhum ${tipo === 'gastos' ? 'gasto' : 'lucro'} encontrado acima de R$ ${valor.toLocaleString('pt-BR')}`;
        } else {
            const total = data.reduce((sum, item) => sum + item.valor, 0);
            display.textContent = `Acima de R$ ${valor.toLocaleString('pt-BR')}: ${formatarValor(total)}`;
        }
    }).catch(error => console.error(`Erro ao carregar ${tipo}:`, error));
}

export function initValorMinimoListeners() {
    document.getElementById('valorMinimoGasto').addEventListener('input', function () {
        const valor = this.value || 0;
        carregarAcimaDe('gastos', valor, 'valorGastosDisplay');
    });

    document.getElementById('valorMinimoLucro').addEventListener('input', function () {
        const valor = this.value || 0;
        carregarAcimaDe('lucros', valor, 'valorLucrosDisplay');
    });

    // Carregar inicialmente com valor 0
    carregarAcimaDe('gastos', 0, 'valorGastosDisplay');
    carregarAcimaDe('lucros', 0, 'valorLucrosDisplay');
}
