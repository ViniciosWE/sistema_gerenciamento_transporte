<?php

$servidor = 'localhost';
$banco      = 'finalpm';
$usuario  = 'root';
$senha    = '';

$conexao = mysqli_connect($servidor, $usuario, $senha, $banco);

$json = file_get_contents('php://input');
$obj = json_decode($json);

$texto1=$obj->codRastreio;	
$texto2=$obj->empresa;
$texto3=$obj->servico;
$texto4=$obj->estatus;
$texto5=$obj->origem;
$texto6=$obj->destino;
$texto7=$obj->dataPrevista;

$sql ="INSERT INTO produto(codigorastreio, empresa, servico, estatus, origem, destino, datadeprevistadeentraga) 
VALUES ('".$texto1."','".$texto2."','".$texto3."','".$texto4."','".$texto5."','".$texto6."','".$texto7."')";

if (mysqli_query($conexao, $sql)) {
    echo "sucesso";
} else {
    echo "erro";
}

?>

