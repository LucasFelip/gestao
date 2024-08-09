$(document).ready(function() {
    // Carregar categorias de Gasto
    $.ajax({
        url: '/categorias/tipo?tipo=GASTO',
        method: 'GET',
        success: function(data) {
            let categoriaSelect = $('#categoriaGastoCadastrar');
            data.forEach(function(categoria) {
                categoriaSelect.append('<option value="' + categoria.id + '">' + categoria.nome + '</option>');
            });
        },
        error: function(xhr, status, error) {
            console.error("Erro ao carregar categorias de Gasto:", error);
        }
    });
});

function submitGastoForm() {
    const formData = {
        descricao: document.getElementById('descricaoGasto').value,
        categoria: { id: document.getElementById('categoriaGastoCadastrar').value },
        valor: parseFloat(document.getElementById('valorGasto').value),
        data: document.getElementById('dataGasto').value
    };

    fetch('/gastos/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                alert('Gasto cadastrado com sucesso!');
                window.location.reload(); // Atualize a página para refletir as mudanças
            } else {
                alert('Erro ao cadastrar lucro.');
            }
        });
    return false;
}