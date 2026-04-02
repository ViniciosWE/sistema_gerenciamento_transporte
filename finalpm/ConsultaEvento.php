<?php
$servidor = 'localhost';
$banco = 'finalpm';
$usuario = 'root';
$senha = '';

$json = file_get_contents('php://input');
$obj = json_decode($json);

$id = $obj->id;

$conexao = mysqli_connect($servidor, $usuario, $senha, $banco);

$dados = mysqli_query($conexao,
    "SELECT data, local, status, detalhes 
     FROM evento 
     WHERE produto_id = '$id'
     ORDER BY data DESC");

$vetor = array();

while ($linha = mysqli_fetch_assoc($dados)) {
    $vetor['evento'][] = array_map('utf8_encode', $linha);
}

echo json_encode($vetor);
?>
