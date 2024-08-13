// modules/lucro.js
import { carregarDados, submitForm } from './utils.js';

export function carregarCategoriasLucro(tipo, selectId) {
    carregarDados(`/categorias/tipo?tipo=${tipo}`).then(data => {
        let categoriaSelect = document.getElementById(selectId);
        data.forEach(categoria => {
            categoriaSelect.append(new Option(categoria.nome, categoria.id));
        });
    }).catch(error => console.error(`Erro ao carregar categorias de ${tipo}:`, error));
}

export function submitLucroForm(event) {
    event.preventDefault(); // Previne o comportamento padrão de envio do formulário

    const formData = {
        descricao: document.getElementById('descricaoLucro').value,
        categoria: { id: document.getElementById('categoriaLucroCadastrar').value },
        valor: parseFloat(document.getElementById('valorLucro').value),
        data: document.getElementById('dataLucro').value
    };

    submitForm('/lucros/register', formData, 'Lucro cadastrado com sucesso!')
        .then(data => {
            alert('Lucro cadastrado com sucesso!');
        })
        .catch(error => {
            alert(`Erro ao cadastrar lucro: ${error.message}`);
        });
}