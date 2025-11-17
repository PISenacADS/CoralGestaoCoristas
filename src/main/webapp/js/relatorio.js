document.addEventListener('DOMContentLoaded', function() {
    
    const dadosBody = document.getElementById('dados-body');
    const graficoBarras = document.getElementById('grafico-barras');
    const selectPeriodo = document.getElementById('periodo-select');

    window.carregarRelatorio = function() {
        const dias = selectPeriodo.value;
        
        fetch(`presencas?action=relatorio&dias=${dias}`)
            .then(response => response.json())
            .then(dados => {
               
                window.dadosRelatorio = dados; 
                renderizarTela(dados);
            })
            .catch(erro => console.error('Erro:', erro));
    }

    function renderizarTela(dados) {
        dadosBody.innerHTML = '';
        graficoBarras.innerHTML = '';

        if (dados.length === 0) {
            dadosBody.innerHTML = '<div style="padding:20px; color:white;">Nenhum dado neste período.</div>';
            return;
        }

        const maxPresencas = Math.max(...dados.map(p => p.presencas));

        dados.forEach(pessoa => {

            const linha = document.createElement('div');
            linha.className = 'tabela-linha';
            linha.innerHTML = `
                <div class="col-nome">${pessoa.nome}</div>
                <div class="col-num">${pessoa.presencas}</div>
                <div class="col-num">${pessoa.faltas}</div>
                <div class="col-num">${pessoa.percentual}</div>
            `;
            dadosBody.appendChild(linha);

            // Gráfico
            const barra = document.createElement('div');
            barra.className = 'grafico-barra';
            barra.title = `${pessoa.nome}: ${pessoa.presencas}`;
            
            let altura = 0;
            if (maxPresencas > 0) altura = (pessoa.presencas / maxPresencas) * 100;
            barra.style.height = Math.max(altura, 1) + '%';
            
            graficoBarras.appendChild(barra);
        });
    }

    window.filtrarTabela = function() {
        const termo = document.getElementById('input-busca').value.toLowerCase();
        
        const dadosFiltrados = window.dadosRelatorio.filter(pessoa => 
            pessoa.nome.toLowerCase().includes(termo)
        );
        
        renderizarTela(dadosFiltrados);
    }

    carregarRelatorio();
});