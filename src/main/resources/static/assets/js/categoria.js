$(document).ready(function() {
    // Carregar categorias de Lucro
    $.ajax({
        url: '/categorias/tipo?tipo=LUCRO',
        method: 'GET',
        success: function(data) {
            let lucroList = $('#categoriasLucro');
            data.forEach(function(categoria) {
                lucroList.append('<li class="list-group-item">' + categoria.nome + '</li>');
            });
        },
        error: function(xhr, status, error) {
            console.error("Erro ao carregar categorias de Lucro:", error);
        }
    });

    // Carregar categorias de Gasto
    $.ajax({
        url: '/categorias/tipo?tipo=GASTO',
        method: 'GET',
        success: function(data) {
            let gastoList = $('#categoriasGasto');
            data.forEach(function(categoria) {
                gastoList.append('<li class="list-group-item">' + categoria.nome + '</li>');
            });
        },
        error: function(xhr, status, error) {
            console.error("Erro ao carregar categorias de Gasto:", error);
        }
    });
});

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