<?php
    $username="gamejam";
    $password="";
    $dbname = "gamejam2022";

    $conn = new mysqli("localhost", $username, $password, $dbname);
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $stmt = $conn->prepare("INSERT INTO scores (name, score) VALUES (?, ?)");
    if (!$_GET["name"] || !$_GET["score"] || !$_GET["hash"]) {
        die("Missing parameter name, score or hash");
    }

    if ($_GET["hash"] != hash("sha256", $_GET["name"].$_GET["score"]."manySecureMuchSafeSalt")){
        die("Hash mismatch! STOP CHEATING NOOB!");
    }

    $stmt->bind_param("si", $name, $score);
    $name = $_GET["name"];
    $score = $_GET["score"];
    $stmt->execute();

    $conn->close();
    echo "400 OK"
?>