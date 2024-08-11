document.addEventListener('DOMContentLoaded', function () {
    let chartGastos = null;
    let chartLucros = null;

    // Função para definir as datas padrão
    function definirDatasPadrao() {
        const hoje = new Date();
        const primeiroDiaAno = new Date(hoje.getFullYear(), 0, 1).toISOString().split('T')[0];
        const ultimoDiaAno = new Date(hoje.getFullYear(), 11, 31).toISOString().split('T')[0];

        return { primeiroDiaAno, ultimoDiaAno };
    }

    // Função para carregar dados de um endpoint e renderizar um gráfico
    function carregarGrafico(url, canvasId, label) {
        return fetch(url)
            .then(response => response.json())
            .then(data => {
                console.log(data); // Log para depuração

                const ctx = document.getElementById(canvasId).getContext('2d');

                // Se já existe um gráfico, destrua-o antes de criar um novo
                if (canvasId === 'graficoGastos' && chartGastos) {
                    chartGastos.destroy();
                } else if (canvasId === 'graficoLucros' && chartLucros) {
                    chartLucros.destroy();
                }

                // Crie o novo gráfico
                const chart = new Chart(ctx, {
                    type: 'bar', // ou 'line', 'pie', etc.
                    data: {
                        labels: data.map(d => d.descricao || 'Sem Descrição'),
                        datasets: [{
                            label: label,
                            data: data.map(d => d.valor || 0),
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            borderColor: 'rgba(75, 192, 192, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });

                // Atribua o novo gráfico à referência correta
                if (canvasId === 'graficoGastos') {
                    chartGastos = chart;
                } else if (canvasId === 'graficoLucros') {
                    chartLucros = chart;
                }
            })
            .catch(error => console.error('Erro ao carregar o gráfico:', error));
    }

    // Função para carregar os gráficos com base nas datas
    function carregarRelatorios() {
        const { primeiroDiaAno, ultimoDiaAno } = definirDatasPadrao();

        // Verificar se as datas estão vazias e usar valores padrão, se necessário
        let inicio = document.getElementById('inicio').value || primeiroDiaAno;
        let fim = document.getElementById('fim').value || ultimoDiaAno;

        if (inicio && fim) {
            carregarGrafico(`/reports/gastos?inicio=${inicio}&fim=${fim}`, 'graficoGastos', 'Gastos');
            carregarGrafico(`/reports/lucros?inicio=${inicio}&fim=${fim}`, 'graficoLucros', 'Lucros');
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
