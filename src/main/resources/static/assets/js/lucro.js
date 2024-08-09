$(document).ready(function() {
    // Carregar categorias de Lucro
    $.ajax({
        url: '/categorias/tipo?tipo=LUCRO',
        method: 'GET',
        success: function(data) {
            let categoriaSelect = $('#categoriaLucroCadastrar');
            data.forEach(function(categoria) {
                categoriaSelect.append('<option value="' + categoria.id + '">' + categoria.nome + '</option>');
            });
        },
        error: function(xhr, status, error) {
            console.error("Erro ao carregar categorias de Lucro:", error);
        }
    });
});

function submitLucroForm() {
    const formData = {
        descricao: document.getElementById('descricaoLucro').value,
        categoria: { id: document.getElementById('categoriaLucroCadastrar').value },
        valor: parseFloat(document.getElementById('valorLucro').value),
        data: document.getElementById('dataLucro').value
    };

    fetch('/lucros/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                alert('Lucro cadastrado com sucesso!');
                window.location.reload(); // Atualize a página para refletir as mudanças
            } else {
                alert('Erro ao cadastrar lucro.');
            }
        });
    return false;
}