document.addEventListener('DOMContentLoaded', function () {
    // Função para carregar dados de orçamento, gasto e lucro
    function carregarInformacoesFinanceiras() {
        const dataInicio = '2024-01-01'; // Substitua com as datas desejadas
        const dataFim = '2024-12-31';

        // Carregar orçamento
        fetch('/orcamentos/pessoa')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na resposta ao carregar orçamento');
                }
                return response.json();
            })
            .then(data => {
                const orcamento = data[0] ? data[0].limite : 0;
                console.log('Orçamento carregado:', orcamento);
                document.getElementById('orcamentoValor').textContent = `Orçamento: R$ ${orcamento.toFixed(2)}`;
            })
            .catch(error => console.error('Erro ao carregar orçamento:', error));

        // Carregar gasto
        fetch(`/gastos/sum?inicio=${dataInicio}&fim=${dataFim}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na resposta ao carregar gastos');
                }
                return response.json();
            })
            .then(data => {
                console.log('Gastos carregados:', data);
                document.getElementById('gastoValor').textContent = `Gastos: R$ ${data ? data.toFixed(2) : '0.00'}`;
            })
            .catch(error => console.error('Erro ao carregar gastos:', error));

        // Carregar lucro
        fetch(`/lucros/sum?inicio=${dataInicio}&fim=${dataFim}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na resposta ao carregar lucros');
                }
                return response.json();
            })
            .then(data => {
                console.log('Lucros carregados:', data);
                document.getElementById('lucroValor').textContent = `Lucros: R$ ${data ? data.toFixed(2) : '0.00'}`;
            })
            .catch(error => console.error('Erro ao carregar lucros:', error));
    }

    carregarInformacoesFinanceiras();
});