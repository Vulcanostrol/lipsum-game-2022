<?php
    $username="gamejam";
    $password="";
    $dbname = "gamejam2022";

    $conn = new mysqli("localhost", $username, $password, $dbname);
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $sql = "SELECT name, score FROM scores ORDER BY score DESC";
    $result = $conn->query($sql);

?>
<html>
<style>
html {
	background-color: #C19272;
    }

h1, th, td {
	color: #010101;
	font-family: Helvetica, sans-serif;
	text-align:center
}

table {
	margin-left: auto;
	margin-right: auto;
	width: 100%
}

img {
	display: block;
	max-width:100%;
	max-height:100%;
	position: absolute;
	left: 0;
	right: 0;
	margin-left: auto;
	margin-right: auto;
	bottom: 0;
}

.wrapper {
	width: 100%;
	background-color: #B18262;
}

.col {
	float: left;
	width: 15%;
}

.col-scores {
	float: left;
	width: 70%
}

.spaceship {
	background-color: #B18262;
	height: 100%;
}

.shipcontainer {
	position: relative;
	height: 100%;
	margin-auto;
}

</style>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Chipsum Highscores</title>
</head>

<body>
<div class="wrapper">
	<div class="spaceship col">
		<div class="shipcontainer">
			<img src="drone.gif"></img>
		</div>
	</div>
	<div class="col-scores">

    <h1> Chipsum Highscores </h1>

    <table>
        <tr>
            <th> Name </th>
            <th> Score </th>
        </tr>

<?php

    if ($result->num_rows > 0) {
        // output data of each row
        while($row = $result->fetch_assoc()) {
            echo "<tr> <td>" . $row["name"]. "</td><td> " . $row["score"]. "</td></tr>";
        }
    } else {
        echo "0 results";
    }

?>

</table>
</div>
	<div class="spaceship col">
		<div class="shipcontainer">
			<img src="drone.gif"></img>
		</div>
	</div>
</div>

</body>

<script>
var ships = document.getElementsByTagName("IMG")
var pos = 0;

var id_1 = setInterval(function(){pos--; ships[0].style.top = pos + 'px'; if (pos < -1000){pos = 500}}, 10);
var id_2 = setInterval(function(){pos--; ships[1].style.top = pos + 'px'; if (pos < -1000){pos = 500}}, 10);
</script>

</html>
<?php


    $conn->close();
?>