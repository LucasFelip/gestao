import { carregarDados, definirDatasPadrao } from './utils.js';

let chartFinanceiro = null;
let chartComparacao = null;

function carregarGraficoFinanceiro(inicio, fim) {
    const urlGastos = `/reports/gastos?inicio=${inicio}&fim=${fim}`;
    const urlLucros = `/reports/lucros?inicio=${inicio}&fim=${fim}`;

    Promise.all([carregarDados(urlGastos), carregarDados(urlLucros)])
        .then(([dataGastos, dataLucros]) => {
            const ctx = document.getElementById('graficoFinanceiro').getContext('2d');

            if (chartFinanceiro) chartFinanceiro.destroy(); // Destruir gráfico existente

            const labels = [...new Set([...dataGastos.map(d => d.descricao), ...dataLucros.map(d => d.descricao)])];
            const gastosMap = Object.fromEntries(dataGastos.map(d => [d.descricao, d.valor]));
            const lucrosMap = Object.fromEntries(dataLucros.map(d => [d.descricao, d.valor]));

            const gastosData = labels.map(label => gastosMap[label] || 0);
            const lucrosData = labels.map(label => lucrosMap[label] || 0);

            chartFinanceiro = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels,
                    datasets: [
                        { label: 'Gastos', data: gastosData, backgroundColor: 'rgba(255, 99, 132, 0.2)', borderColor: 'rgba(255, 99, 132, 1)', borderWidth: 1 },
                        { label: 'Lucros', data: lucrosData, backgroundColor: 'rgba(75, 192, 192, 0.2)', borderColor: 'rgba(75, 192, 192, 1)', borderWidth: 1 }
                    ]
                },
                options: {
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });
        })
        .catch(error => console.error('Erro ao carregar os dados:', error));
}

function carregarGraficoComparacao(inicio, fim) {
    const urlGastos = `/gastos/sum?inicio=${inicio}&fim=${fim}`;
    const urlLucros = `/lucros/sum?inicio=${inicio}&fim=${fim}`;

    Promise.all([carregarDados(urlGastos), carregarDados(urlLucros)])
        .then(([totalGastos, totalLucros]) => {
            const ctx = document.getElementById('graficoComparacao').getContext('2d');

            if (chartComparacao) chartComparacao.destroy(); // Destruir gráfico existente

            chartComparacao = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: ['Gastos', 'Lucros'],
                    datasets: [{ data: [totalGastos, totalLucros], backgroundColor: ['#FF6384', '#36A2EB'] }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: { position: 'top' }
                    }
                }
            });
        })
        .catch(error => console.error('Erro ao carregar os dados de comparação:', error));
}

export function initGraficosRelatorio() {
    const { primeiroDiaAno, ultimoDiaAno } = definirDatasPadrao();

    // Carregar gráficos ao carregar a página
    carregarGraficoFinanceiro(primeiroDiaAno, ultimoDiaAno);
    carregarGraficoComparacao(primeiroDiaAno, ultimoDiaAno);

    document.getElementById('inicio').addEventListener('change', () => {
        const inicio = document.getElementById('inicio').value;
        const fim = document.getElementById('fim').value;
        carregarGraficoFinanceiro(inicio, fim);
        carregarGraficoComparacao(inicio, fim);
    });

    document.getElementById('fim').addEventListener('change', () => {
        const inicio = document.getElementById('inicio').value;
        const fim = document.getElementById('fim').value;
        carregarGraficoFinanceiro(inicio, fim);
        carregarGraficoComparacao(inicio, fim);
    });
}
