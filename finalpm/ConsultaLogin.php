<?php
$servidor = 'localhost';
$banco = 'finalpm';
$usuario = 'root';
$senha = '';

$json = file_get_contents('php://input');
$obj = json_decode($json);

$texto1 = $obj->cpf;
$texto2 = $obj->senha;

$conexao = mysqli_connect($servidor, $usuario, $senha, $banco);

$dados = mysqli_query($conexao,
    "SELECT cpf, senha FROM usuario 
     WHERE cpf = '$texto1' AND senha = '$texto2' LIMIT 1");

$vetor = array();
while ($func = mysqli_fetch_assoc($dados)) {
    $vetor['usuario'][] = array_map('utf8_encode', $func);
}

echo json_encode($vetor);

?>
