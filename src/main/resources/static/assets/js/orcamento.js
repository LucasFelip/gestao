function submitOrcamentoForm() {
    const formData = {
        dataInicio: document.getElementById('dataInicioOrcamento').value,
        dataFim: document.getElementById('dataFimOrcamento').value,
        limite: document.getElementById('valorOrcamento').value
    };

    fetch('/orcamentos/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                alert('Orçamento cadastrado com sucesso!');
                window.location.reload(); // Atualize a página para refletir as mudanças
            } else {
                alert('Erro ao cadastrar orçamento.');
            }
        });
    return false;
}