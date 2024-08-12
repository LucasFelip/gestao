document.addEventListener('DOMContentLoaded', function () {
    // Função para definir as datas padrão
    function definirDatasPadrao() {
        const hoje = new Date();
        const primeiroDiaAno = new Date(hoje.getFullYear(), 0, 1).toISOString().split('T')[0];
        const ultimoDiaAno = new Date(hoje.getFullYear(), 11, 31).toISOString().split('T')[0];

        return { primeiroDiaAno, ultimoDiaAno };
    }

    // Função para formatar valores monetários em reais
    function formatarValor(valor) {
        return valor.toLocaleString('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        });
    }

    // Função para carregar dados de orçamento, gasto e lucro
    function carregarInformacoesFinanceiras(inicio, fim) {
        // Carregar orçamento
        fetch(`/orcamentos/pessoa?inicio=${inicio}&fim=${fim}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na resposta ao carregar orçamento');
                }
                return response.json();
            })
            .then(data => {
                const orcamento = data[0] ? data[0].limite : 0;
                document.getElementById('orcamentoValor').textContent = formatarValor(orcamento);
            })
            .catch(error => console.error('Erro ao carregar orçamento:', error));

        // Carregar gasto
        fetch(`/gastos/sum?inicio=${inicio}&fim=${fim}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na resposta ao carregar gastos');
                }
                return response.json();
            })
            .then(data => {
                document.getElementById('gastoValor').textContent = formatarValor(data || 0);
            })
            .catch(error => console.error('Erro ao carregar gastos:', error));

        // Carregar lucro
        fetch(`/lucros/sum?inicio=${inicio}&fim=${fim}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na resposta ao carregar lucros');
                }
                return response.json();
            })
            .then(data => {
                document.getElementById('lucroValor').textContent = formatarValor(data || 0);
            })
            .catch(error => console.error('Erro ao carregar lucros:', error));
    }

    // Função para carregar as informações financeiras com base nas datas selecionadas
    function carregarOrcamentoRelatorio() {
        const inicio = document.getElementById('inicio').value;
        const fim = document.getElementById('fim').value;
        carregarInformacoesFinanceiras(inicio, fim);
    }

    // Definir datas padrão ao carregar a página
    const { primeiroDiaAno, ultimoDiaAno } = definirDatasPadrao();
    document.getElementById('inicio').value = primeiroDiaAno;
    document.getElementById('fim').value = ultimoDiaAno;

    // Carregar as informações financeiras com as datas padrão
    carregarInformacoesFinanceiras(primeiroDiaAno, ultimoDiaAno);

    // Atualizar as informações financeiras automaticamente ao mudar as datas
    document.getElementById('inicio').addEventListener('change', carregarOrcamentoRelatorio);
    document.getElementById('fim').addEventListener('change', carregarOrcamentoRelatorio);
});