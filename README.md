🎵 Coral Gestão de Coristas - Guia de Instalação
Este guia contém o passo a passo para configurar o ambiente e rodar o projeto localmente.

📋 1. Pré-requisitos
Antes de começar, certifique-se de ter instalado:

Java JDK 17+: Baixar aqui.

VS Code: Com o "Extension Pack for Java" instalado.

MySQL Server: [link suspeito removido].

Apache Maven: (Instruções abaixo).

Apache Tomcat 9: (Instruções abaixo).

⚙️ 2. Configurando o Apache Maven (Essencial)
O Maven serve para "empacotar" nosso projeto.

Baixe o "Binary zip archive" do Maven: Site Oficial.

Extraia a pasta em um local fixo (Ex: C:\Servidores\apache-maven-3.x.x).

Configurar Variável de Ambiente (Windows):

Pesquise no Windows por "Editar as variáveis de ambiente do sistema".

Clique em "Variáveis de Ambiente...".

Na lista de baixo (Variáveis do sistema), encontre a variável Path e clique em "Editar".

Clique em "Novo" e cole o caminho da pasta bin do Maven.

Exemplo: C:\Servidores\apache-maven-3.9.6\bin

Dê OK em tudo.

Reinicie o VS Code e teste no terminal digitando: mvn -version.

🗄️ 3. Configurando o Banco de Dados
Abra seu gerenciador de banco (MySQL Workbench, DBeaver, etc).

Execute o script abaixo para criar o banco e o usuário padrão:

SQL

CREATE DATABASE coraldb;
USE coraldb;

CREATE TABLE coristas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    tipo_voz VARCHAR(50),
    telefone VARCHAR(20),
    email VARCHAR(150),
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE agenda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    data_evento DATE NOT NULL,
    horario TIME NOT NULL,
    local_evento VARCHAR(200),
    tipo VARCHAR(50) NOT NULL
);

CREATE TABLE presencas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_agenda INT NOT NULL,
    id_corista INT NOT NULL,
    presente BOOLEAN NOT NULL,
    FOREIGN KEY (id_agenda) REFERENCES agenda(id) ON DELETE CASCADE,
    FOREIGN KEY (id_corista) REFERENCES coristas(id) ON DELETE CASCADE
);

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Usuário Padrão
INSERT INTO usuarios (usuario, senha) VALUES ('admin', '123');
🚀 4. Configurando o Servidor (Tomcat 9) no VS Code
⚠️ IMPORTANTE: Utilize o Tomcat 9. O Tomcat 10 não funcionará devido à versão do Java Servlet (javax vs jakarta).

Baixe o Tomcat 9 (64-bit Windows zip): Site Oficial.

Extraia em uma pasta fixa (Ex: C:\Servidores\apache-tomcat-9.0.xx).

No VS Code, instale a extensão "Community Server Connectors".

Vá na aba SERVERS (geralmente no canto inferior esquerdo).

Clique em + (Create New Server).

Escolha: No, use server on disk -> Apache Tomcat 9.x.

Aponte para a pasta onde você extraiu o Tomcat.

▶️ 5. Como Rodar o Projeto
Sempre que você baixar atualizações (git pull) ou fizer alterações no código Java:

Gerar o Pacote (.war):

Abra o terminal no VS Code.

Rode o comando:

Bash

mvn package
Aguarde aparecer "BUILD SUCCESS".

Publicar no Servidor:

Na aba SERVERS, clique com o botão direito no seu Tomcat 9.

Escolha Add Deployment.

Selecione File -> Vá na pasta target do projeto -> Selecione o arquivo .war (ex: coralgestaocoristas.war).

Iniciar:

Clique com o botão direito no Tomcat -> Start Server.

Acessar:

Abra o navegador em: http://localhost:8080/coralgestaocoristas/

🔑 Login de Acesso
Usuário: admin

Senha: 123

💡 Dicas de Desenvolvimento
Se mexer em HTML/CSS: Apenas salve e dê Refresh no navegador (às vezes precisa de Ctrl+F5).

Se mexer em Java (.java): Pare o servidor, rode mvn package e inicie novamente.

Erro de Porta: Se a porta 8080 estiver ocupada, altere no arquivo conf/server.xml dentro da pasta do Tomcat.
