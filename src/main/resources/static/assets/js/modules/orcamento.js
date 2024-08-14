import {carregarDados, definirDatasPadrao, formatarValor, submitForm} from './utils.js';

function carregarInformacoesFinanceiras(inicio, fim) {
    carregarDados(`/orcamentos/pessoa?inicio=${inicio}&fim=${fim}`).then(data => {
        const orcamento = data[0] ? data[0].limite : 0;
        document.getElementById('orcamentoValor').textContent = formatarValor(orcamento);
    }).catch(error => console.error('Erro ao carregar orçamento:', error));

    carregarDados(`/gastos/sum?inicio=${inicio}&fim=${fim}`).then(data => {
        document.getElementById('gastoValor').textContent = formatarValor(data || 0);
    }).catch(error => console.error('Erro ao carregar gastos:', error));

    carregarDados(`/lucros/sum?inicio=${inicio}&fim=${fim}`).then(data => {
        document.getElementById('lucroValor').textContent = formatarValor(data || 0);
    }).catch(error => console.error('Erro ao carregar lucros:', error));

    carregarDados(`/reports/saldo?inicio=${inicio}&fim=${fim}`).then(data => {
        document.getElementById('saldoValor').textContent = formatarValor(data || 0);
    }).catch(error => console.error('Erro ao carregar saldo:', error));
}

export function initOrcamentoRelatorio() {
    const { primeiroDiaAno, ultimoDiaAno } = definirDatasPadrao();

    document.getElementById('inicio').value = primeiroDiaAno;
    document.getElementById('fim').value = ultimoDiaAno;

    carregarInformacoesFinanceiras(primeiroDiaAno, ultimoDiaAno);

    document.getElementById('inicio').addEventListener('change', () => {
        const inicio = document.getElementById('inicio').value;
        const fim = document.getElementById('fim').value;
        carregarInformacoesFinanceiras(inicio, fim);
    });

    document.getElementById('fim').addEventListener('change', () => {
        const inicio = document.getElementById('inicio').value;
        const fim = document.getElementById('fim').value;
        carregarInformacoesFinanceiras(inicio, fim);
    });
}

export function submitOrcamentoForm(event) {
    event.preventDefault();

    const formData = {
        dataInicio: document.getElementById('dataInicioOrcamento').value,
        dataFim: document.getElementById('dataFimOrcamento').value,
        limite: parseFloat(document.getElementById('valorOrcamento').value)
    };

    submitForm('/orcamentos/register', formData, 'Orçamento cadastrado com sucesso!')
        .then(data => {
            alert('Orçamento cadastrado com sucesso!');
            location.reload();
        })
        .catch(error => {
            alert(`Erro ao cadastrar orçamento: ${error.message}`);
        });
}