<?php
$servidor = 'localhost';
$banco = 'finalpm';
$usuario = 'root';
$senha = '';

$conexao = mysqli_connect($servidor, $usuario, $senha, $banco);

$dados = mysqli_query($conexao,
    "SELECT id, codigorastreio FROM produto ORDER BY codigorastreio ASC");

$vetor = array();
$vetor['produto'] = array();

while ($func = mysqli_fetch_assoc($dados)) {
    $vetor['produto'][] = $func;
}

echo json_encode($vetor);
?>
