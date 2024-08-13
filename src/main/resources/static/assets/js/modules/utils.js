export function definirDatasPadrao() {
    const hoje = new Date();
    const primeiroDiaAno = new Date(hoje.getFullYear(), 0, 1).toISOString().split('T')[0];
    const ultimoDiaAno = new Date(hoje.getFullYear(), 11, 31).toISOString().split('T')[0];

    return { primeiroDiaAno, ultimoDiaAno };
}

export function formatarValor(valor) {
    return valor.toLocaleString('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    });
}

export function carregarDados(url) {
    return fetch(url).then(response => {
        if (!response.ok) {
            throw new Error('Erro ao carregar dados da URL: ' + url);
        }
        return response.json();
    });
}

export function submitForm(url, formData, successMessage) {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                alert(successMessage);
                return response.json(); // Retorna a resposta para tratamento adicional
            } else {
                return response.text().then(text => {
                    throw new Error(`Erro ao enviar dados: ${text}`);
                });
            }
        });
}
