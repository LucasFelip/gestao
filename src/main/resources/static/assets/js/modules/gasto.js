import {carregarDados, submitForm} from './utils.js';

export function carregarCategorias(tipo, selectId) {
    carregarDados(`/categorias/tipo?tipo=${tipo}`).then(data => {
        let categoriaSelect = document.getElementById(selectId);
        data.forEach(categoria => {
            categoriaSelect.append(new Option(categoria.nome, categoria.id));
        });
    }).catch(error => console.error(`Erro ao carregar categorias de ${tipo}:`, error));
}

export function submitGastoForm(event) {
    event.preventDefault();

    const formData = {
        descricao: document.getElementById('descricaoGasto').value,
        categoria: { id: document.getElementById('categoriaGastoCadastrar').value },
        valor: parseFloat(document.getElementById('valorGasto').value),
        data: document.getElementById('dataGasto').value
    };

    submitForm('/gastos/register', formData, 'Gasto cadastrado com sucesso!')
        .then(data => {
            alert('Gasto cadastrado com sucesso!');
        })
        .catch(error => {
            alert(`Erro ao cadastrar gasto: ${error.message}`);
        });
}
