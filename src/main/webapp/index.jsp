<html>
<head>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<section class="first">
    <div class="container">
        <form action="/new-match">
            <button class="first_btn_newgame">New Game</button>
        </form>
        <form th:method="GET" th:action="@{/matches}">
            <button type="submit" class="first_btn">View matches</button>
        </form>
    </div>
</section>
</html>
