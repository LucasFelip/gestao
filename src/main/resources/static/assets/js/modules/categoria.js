import { carregarDados, submitForm } from './utils.js';

export function loadCategorias(tipo, page, size) {
    const url = `/categorias/tipo/paginado?tipo=${tipo}&page=${page}&size=${size}`;

    carregarDados(url)
        .then(data => {
            const list = tipo === 'LUCRO' ? $('#categoriasLucro') : $('#categoriasGasto');
            const pagination = tipo === 'LUCRO' ? $('#lucroPagination') : $('#gastoPagination');

            list.empty();
            data.content.forEach(categoria => {
                list.append('<li class="list-group-item">' + categoria.nome + '</li>');
            });

            pagination.empty(); // Limpa a paginação antes de recarregar
            for (let i = 0; i < data.totalPages; i++) {
                pagination.append(`
                    <li class="page-item ${i === data.number ? 'active' : ''}">
                        <a class="page-link" href="#" onclick="return loadCategoriasPage('${tipo}', ${i}, ${size});">${i + 1}</a>
                    </li>
                `);
            }
        })
        .catch(error => {
            console.error(`Erro ao carregar categorias de ${tipo}:`, error.message);
        });
}

// Adicione a função global para ser chamada no HTML
window.loadCategoriasPage = function(tipo, page, size) {
    loadCategorias(tipo, page, size);
    return false;
};

export function submitCategoriaForm(event) {
    event.preventDefault();

    const formData = {
        nome: document.getElementById('nome').value,
        tipoCategoria: document.getElementById('tipo').value
    };

    submitForm('/categorias/register', formData, 'Categoria registrada com sucesso!')
        .then(data => {
            loadCategorias(formData.tipoCategoria, 0, 5);
            document.getElementById('registerFormCategory').reset();
        })
        .catch(error => {
            alert(error.message);
        });
}