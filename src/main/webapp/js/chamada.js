document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const idAgenda = params.get('id_agenda'); 

    if (!idAgenda) {
        alert("Evento não especificado!");
        window.location.href = 'agenda.html';
        return;
    }

    const listaDiv = document.getElementById('lista-coristas');
    const contadorSpan = document.getElementById('contador');
    const form = document.getElementById('form-chamada');
    const tituloH2 = document.getElementById('titulo-evento');

    fetch('coristas?action=listJson')
        .then(res => res.json())
        .then(coristas => {
            listaDiv.innerHTML = ''; 
            
            tituloH2.textContent = `Chamada - Evento #${idAgenda}`;

            coristas.forEach(c => {
                const div = document.createElement('div');
                div.className = 'item-corista';
                div.innerHTML = `
                    <span class="nome-corista">${c.nome}</span>
                    <label class="switch">
                        <input type="checkbox" name="presenca" value="${c.id}" class="check-presenca">
                        <span class="slider"></span>
                    </label>
                `;
                listaDiv.appendChild(div);
            });

            const checks = document.querySelectorAll('.check-presenca');
            checks.forEach(ch => ch.addEventListener('change', atualizarContador));
        })
        .catch(err => console.error(err));

    function atualizarContador() {
        const total = document.querySelectorAll('.check-presenca:checked').length;
        contadorSpan.textContent = total;
    }

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const marcados = Array.from(document.querySelectorAll('.check-presenca:checked'))
                              .map(input => input.value);
        
        const idsString = marcados.join(','); 

        const params = new URLSearchParams();
        params.append('acao', 'chamada_lote');
        params.append('idAgenda', idAgenda);
        params.append('idsPresentes', idsString);

        fetch('presencas', {
            method: 'POST',
            body: params
        })
        .then(res => res.json())
        .then(data => {
            if(data.status === 'ok') {
                alert("Chamada salva com sucesso!");
                window.location.href = 'agenda.html';
            } else {
                alert("Erro ao salvar.");
            }
        });
    });
});