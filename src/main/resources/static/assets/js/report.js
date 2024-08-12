document.addEventListener('DOMContentLoaded', function () {
    let chartFinanceiro = null;

    // Função para definir as datas padrão
    function definirDatasPadrao() {
        const hoje = new Date();
        const primeiroDiaAno = new Date(hoje.getFullYear(), 0, 1).toISOString().split('T')[0];
        const ultimoDiaAno = new Date(hoje.getFullYear(), 11, 31).toISOString().split('T')[0];

        return { primeiroDiaAno, ultimoDiaAno };
    }

    // Função para carregar dados de lucros e gastos e renderizar um gráfico combinado
    function carregarGraficoFinanceiro(inicio, fim) {
        const urlGastos = `/reports/gastos?inicio=${inicio}&fim=${fim}`;
        const urlLucros = `/reports/lucros?inicio=${inicio}&fim=${fim}`;

        Promise.all([
            fetch(urlGastos).then(response => response.json()),
            fetch(urlLucros).then(response => response.json())
        ])
            .then(([dataGastos, dataLucros]) => {
                const ctx = document.getElementById('graficoFinanceiro').getContext('2d');

                // Se já existe um gráfico, destrua-o antes de criar um novo
                if (chartFinanceiro) {
                    chartFinanceiro.destroy();
                }

                // Preparar os dados para o gráfico
                const labels = [...new Set([...dataGastos.map(d => d.descricao), ...dataLucros.map(d => d.descricao)])];
                const gastosMap = Object.fromEntries(dataGastos.map(d => [d.descricao, d.valor]));
                const lucrosMap = Object.fromEntries(dataLucros.map(d => [d.descricao, d.valor]));

                const gastosData = labels.map(label => gastosMap[label] || 0);
                const lucrosData = labels.map(label => lucrosMap[label] || 0);

                // Criar o gráfico combinado
                chartFinanceiro = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [
                            {
                                label: 'Gastos',
                                data: gastosData,
                                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                borderColor: 'rgba(255, 99, 132, 1)',
                                borderWidth: 1
                            },
                            {
                                label: 'Lucros',
                                data: lucrosData,
                                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                                borderColor: 'rgba(75, 192, 192, 1)',
                                borderWidth: 1
                            }
                        ]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    callback: function(value) {
                                        return 'R$ ' + value.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
                                    }
                                }
                            }
                        },
                        tooltips: {
                            callbacks: {
                                label: function(tooltipItem) {
                                    return tooltipItem.dataset.label + ': R$ ' + tooltipItem.yLabel.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
                                }
                            }
                        }
                    }
                });
            })
            .catch(error => console.error('Erro ao carregar os dados:', error));
    }

    // Função para carregar os gráficos com base nas datas
    function carregarRelatorios() {
        const { primeiroDiaAno, ultimoDiaAno } = definirDatasPadrao();

        // Verificar se as datas estão vazias e usar valores padrão, se necessário
        let inicio = document.getElementById('inicio').value || primeiroDiaAno;
        let fim = document.getElementById('fim').value || ultimoDiaAno;

        if (inicio && fim) {
            carregarGraficoFinanceiro(inicio, fim);
        }
    }

    // Definir datas padrão ao carregar a página
    const { primeiroDiaAno, ultimoDiaAno } = definirDatasPadrao();
    document.getElementById('inicio').value = primeiroDiaAno;
    document.getElementById('fim').value = ultimoDiaAno;

    // Carregar os gráficos com as datas padrão
    carregarRelatorios();

    // Atualizar os gráficos automaticamente ao mudar as datas
    document.getElementById('inicio').addEventListener('change', carregarRelatorios);
    document.getElementById('fim').addEventListener('change', carregarRelatorios);
});
