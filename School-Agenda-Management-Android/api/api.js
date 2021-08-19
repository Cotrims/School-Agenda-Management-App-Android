const express = require('express');
const app = express();
const cors = require('cors');
const bodyParser = require('body-parser');
const porta = 3000; //porta padrão
const sql = require('mssql');
const conexaoStr = "Server=regulus.cotuca.unicamp.br;Database=BD19186;User Id=BD19186;Password=pqpovisk123; ";

//conexao com BD
sql.connect(conexaoStr)
    .then(conexao => global.conexao = conexao)
    .catch(erro => console.log(erro));

// configurando o body parser para pegar POSTS mais tarde
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(cors());

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

function execSQL(sql, resposta) {
    global.conexao.request()
        .query(sql)
        .then(resultado => resposta.json(resultado.recordset))
        .catch(erro => resposta.json(erro));
}

app.get('/api/alunos/get/:ra', (requisicao, resposta) => { //PEGA ALUNO DE ACORDO COM RA
    global.conexao.query("SELECT * from Alunos WHERE RA='" + requisicao.params.ra + "'", (err, result) => {
        if (!result.recordset[0])
            resposta.send(null);
        else
            resposta.json(result.recordset[0]);
    });
});

app.get('/api/alunos/get', (requisicao, resposta) => { //PEGA TODOS OS ALUNOS
    execSQL("SELECT * from Alunos", resposta);
});

app.delete("/api/alunos/delete/:ra", (requisicao, resposta) => { //DELETA 
    global.conexao.query("SELECT * from Alunos WHERE RA='" + requisicao.params.ra + "'", (err, result) => {
        if (result.recordset[0] == undefined)
            resposta.sendStatus(204);
        else
            execSQL("DELETE Alunos WHERE ra= '" + requisicao.params.ra + "'", resposta);
    });
});

app.post('/api/alunos/post', (requisicao, resposta) => { //ADICIONA
    console.log(requisicao);
    const ra = requisicao.body.ra.substring(0, 5);
    const nome = requisicao.body.nome.substring(0, 50);
    const email = requisicao.body.email.substring(0, 50);

    global.conexao.query("SELECT * from Alunos WHERE RA='" + ra + "'", (err, result) => {
        if (result.recordset[0])
            resposta.send(result.recordset[0]);
        else
            execSQL(`INSERT INTO Alunos (ra, nome, email) VALUES(${ra},'${nome}','${email}')`, resposta);
    });
});

app.put('/api/alunos/put/:ra', (requisicao, resposta) => { //EDITAR
    const ra = requisicao.params.ra;
    const aluno = requisicao.body;
    const email = aluno.email;
    const nome = aluno.nome;

    global.conexao.query("SELECT * from Alunos WHERE RA='" + ra + "'", (err, result) => {
        if (result.recordset[0] == undefined)
            resposta.sendStatus(204);
        else
            execSQL("UPDATE Alunos SET NOME='" + nome + "', EMAIL= '" + email + "' WHERE RA='" + ra + "'", resposta);
    });
});

app.listen(3000);
console.log('A API está no ar');