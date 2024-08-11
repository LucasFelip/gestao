$(document).ready(function() {
    loadCategorias('LUCRO', 0, 5);  // Carrega a primeira página de categorias de Lucro
    loadCategorias('GASTO', 0, 5);  // Carrega a primeira página de categorias de Gasto
});

function loadCategorias(tipo, page, size) {
    $.ajax({
        url: `/categorias/tipo/paginado?tipo=${tipo}&page=${page}&size=${size}`,
        method: 'GET',
        success: function(data) {
            let list = tipo === 'LUCRO' ? $('#categoriasLucro') : $('#categoriasGasto');
            let pagination = tipo === 'LUCRO' ? $('#lucroPagination') : $('#gastoPagination');

            list.empty();  // Limpa a lista antes de carregar novos itens
            data.content.forEach(function(categoria) {
                list.append('<li class="list-group-item">' + categoria.nome + '</li>');
            });

            pagination.empty();  // Limpa a paginação antes de recarregar
            for (let i = 0; i < data.totalPages; i++) {
                pagination.append(`
                    <li class="page-item ${i === data.number ? 'active' : ''}">
                        <a class="page-link" href="#" onclick="return loadCategoriasPage('${tipo}', ${i}, ${size});">${i + 1}</a>
                    </li>
                `);
            }
        },
        error: function(xhr, status, error) {
            console.error(`Erro ao carregar categorias de ${tipo}:`, error);
        }
    });
}

function loadCategoriasPage(tipo, page, size) {
    event.preventDefault();  // Evita o comportamento padrão do link
    loadCategorias(tipo, page, size);  // Recarrega as categorias da página específica
    return false;  // Evita a propagação do evento e outros comportamentos padrão
}

function submitCategoriaForm() {
    const formData = {
        nome: document.getElementById('nome').value,
        tipoCategoria: document.getElementById('tipo').value
    };

    fetch('/categorias/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                alert('Categoria registrada com sucesso!');
                window.location.reload();
            } else {
                alert('Erro ao registrar categoria.');
            }
        });
    return false;
}