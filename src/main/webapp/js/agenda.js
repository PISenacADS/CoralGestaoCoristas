document.addEventListener('DOMContentLoaded', () => {
    
    const calendarioDias = document.getElementById('calendario-dias');
    const tituloMesAno = document.getElementById('mes-ano-titulo');
    const btnAnterior = document.getElementById('btn-anterior');
    const btnProximo = document.getElementById('btn-proximo');
    
    const modal = document.getElementById('modal-evento');
    const btnNovoEvento = document.getElementById('btn-novo-evento');
    const btnCancelarModal = document.getElementById('btn-cancelar-modal');
    const formAgenda = document.getElementById('form-agenda');
    
    const selectMusico = document.getElementById('select-musico');

    let dataAtual = new Date();
    let mesAtual = dataAtual.getMonth();
    let anoAtual = dataAtual.getFullYear();

    const nomesDosMeses = ["JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"];

    function carregarMusicos() {
        
        if (!selectMusico) return;

        fetch('musicos?action=listJson') 
            .then(res => res.json())
            .then(musicos => {
                
                selectMusico.innerHTML = '<option value="0">-- Nenhum / A definir --</option>';
                
                musicos.forEach(m => {
                    const option = document.createElement('option');
                    option.value = m.id;
                    
                    option.textContent = m.nome; 
                    selectMusico.appendChild(option);
                });
            })
            .catch(erro => console.error('Erro ao carregar músicos:', erro));
    }

    function carregarCalendario() {
        fetch('agenda') 
            .then(response => response.json())
            .then(eventos => {
                renderizarDias(eventos);
            })
            .catch(erro => {
                console.error('Erro ao buscar eventos:', erro);
                renderizarDias([]); 
            });
    }

    function renderizarDias(listaEventos) {
        calendarioDias.innerHTML = '';
        tituloMesAno.textContent = `${nomesDosMeses[mesAtual]} ${anoAtual}`;

        const primeiroDiaDoMes = new Date(anoAtual, mesAtual, 1).getDay();
        const diasNoMes = new Date(anoAtual, mesAtual + 1, 0).getDate();

        for (let i = 0; i < primeiroDiaDoMes; i++) {
            const vazio = document.createElement('div');
            vazio.classList.add('dia-vazio');
            calendarioDias.appendChild(vazio);
        }

        for (let dia = 1; dia <= diasNoMes; dia++) {
            const celula = document.createElement('div');
            celula.textContent = dia;

            const mesFormatado = String(mesAtual + 1).padStart(2, '0');
            const diaFormatado = String(dia).padStart(2, '0');
            const dataString = `${anoAtual}-${mesFormatado}-${diaFormatado}`;

            const eventosDoDia = listaEventos.filter(e => e.data_evento === dataString);

            eventosDoDia.forEach(evento => {
                const divEvento = document.createElement('div');
                divEvento.classList.add('evento');
                
                if (evento.tipo === 'ensaio') divEvento.classList.add('evento-ensaio');
                else divEvento.classList.add('evento-concerto');

                divEvento.title = `${evento.horario} - ${evento.local_evento}`;
                
                let conteudoHtml = `<div class="horario">${evento.horario.slice(0, 5)}</div><strong>${evento.titulo}</strong>`;
                
                if (evento.nome_musico) {
                    conteudoHtml += `<div style="font-size:0.8em; color:#ddd; margin-top:2px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">🎹 ${evento.nome_musico}</div>`;
                }
                
                divEvento.innerHTML = conteudoHtml;
                
                divEvento.style.cursor = 'pointer';
                divEvento.addEventListener('click', (e) => {
                    e.stopPropagation(); 
                    window.location.href = `chamada.html?id_agenda=${evento.id}`;
                });
                
                celula.appendChild(divEvento);
            });

            calendarioDias.appendChild(celula);
        }
    }

    btnAnterior.addEventListener('click', () => {
        mesAtual--;
        if (mesAtual < 0) {
            mesAtual = 11;
            anoAtual--;
        }
        carregarCalendario();
    });

    btnProximo.addEventListener('click', () => {
        mesAtual++;
        if (mesAtual > 11) {
            mesAtual = 0;
            anoAtual++;
        }
        carregarCalendario();
    });

    btnNovoEvento.addEventListener('click', () => modal.classList.add('active'));
    btnCancelarModal.addEventListener('click', () => modal.classList.remove('active'));

    formAgenda.addEventListener('submit', (e) => {
        e.preventDefault(); 

        const formData = new URLSearchParams(new FormData(formAgenda));

        fetch('agenda', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'ok') {
                alert('Evento salvo com sucesso!');
                modal.classList.remove('active');
                formAgenda.reset(); 
                carregarCalendario();
            } else {
                alert('Erro ao salvar: ' + data.erro);
            }
        })
        .catch(erro => console.error('Erro:', erro));
    });

    carregarMusicos();
    carregarCalendario(); 
});