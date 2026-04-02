<?php

$servidor = 'localhost';
$banco      = 'finalpm';
$usuario  = 'root';
$senha    = '';

$conexao = mysqli_connect($servidor, $usuario, $senha, $banco);

$json = file_get_contents('php://input');
$obj = json_decode($json);

$texto1=$obj->nome;	
$texto2=$obj->cpf;
$texto3=$obj->senha;

$sql ="INSERT INTO usuario(nome, cpf, senha) VALUES ('".$texto1."','".$texto2."','".$texto3."')";

if (mysqli_query($conexao, $sql)) {
    echo "sucesso";
} else {
    echo "erro";
}

?>

