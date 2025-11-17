document.addEventListener('DOMContentLoaded', function() {
    const dados = [
        { nome: 'João Silva', presencas: 18, faltas: 2, percentual: '90%' },
        { nome: 'Maria Olivera', presencas: 15, faltas: 5, percentual: '75%' },
        { nome: 'Pedro Costa', presencas: 20, faltas: 0, percentual: '100%' }
    ];

    const maxPresencas = Math.max(...dados.map(pessoa => pessoa.presencas));
    
    const dadosBody = document.getElementById('dados-body');
    const graficoBarras = document.getElementById('grafico-barras');

   
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

        const barra = document.createElement('div');
        barra.className = 'grafico-barra';
       
        const alturaPercentual = (pessoa.presencas / maxPresencas) * 100;
        barra.style.height = `${alturaPercentual}%`;

        graficoBarras.appendChild(barra);
    });
});