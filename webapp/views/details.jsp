<%@ page import="java.time.LocalDateTime" %>
<jsp:useBean id="user" class="at.ac.tuwien.big.we16.ue2.User" scope="session"/>
<jsp:useBean id="product" class="at.ac.tuwien.big.we16.ue2.Product" scope="request"/>
<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="utf-8">
    <title>BIG Bid - <%= product.getName() %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../styles/style.css">

    <script src="/scripts/jquery.js"></script>
    <script src="/scripts/framework.js"></script>
    <script src="/scripts/WAScript.js"></script>

    <script>
        $(document).ready(function(){
            var id = "<%= product.getId()%>";
            var name = "<%= product.getName() %>";
            window.sessionStorage.setItem(id, name);
        });

        $(document).ready(function(){
            if(!supportsLocalStorage() || sessionStorage.length == 0){
                document.getElementById("lastSeenHeadlineDetails").className = "recently-viewed-headline";
            } else{
                document.getElementById("lastSeenHeadlineDetails").className = "";
            }
        })
    </script>

</head>

<body data-decimal-separator="," data-grouping-separator="." onload="printProductOfStorageDetails()">

<a href="#productsheadline" class="accessibility">Zum Inhalt springen</a>

<header aria-labelledby="bannerheadline">
    <img class="title-image" src="../images/big-logo-small.png" alt="BIG Bid logo">

    <h1 class="header-title" id="bannerheadline">
        BIG Bid
    </h1>
    <nav aria-labelledby="navigationheadline">
        <h2 class="accessibility" id="navigationheadline">Navigation</h2>
        <ul class="navigation-list">
            <li>
                <a href="" class="button" accesskey="l">Abmelden</a>
            </li>
        </ul>
    </nav>
</header>
<div class="main-container">
    <aside class="sidebar" aria-labelledby="userinfoheadline">
        <div class="user-info-container">
            <h2 class="accessibility" id="userinfoheadline">Benutzerdaten</h2>
            <dl class="user-data properties">
                <dt class="accessibility">Name:</dt>
                <dd id="user-name" class="user-name"><%= user.getEmail() %></dd>
                <dt>Kontostand:</dt>
                <dd>
                    <span class="balance"><%= user.getBudget() %> </span> &#8364
                </dd>
                <dt>Laufend:</dt>
                <dd>
                    <span class="running-auctions-count"><%= user.getRunningAuctions()%></span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
                <dt>Gewonnen:</dt>
                <dd>
                    <span class="won-auctions-count"><%= user.getWonAuctions() %></span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
                <dt>Verloren:</dt>
                <dd>
                    <span class="lost-auctions-count"><%= user.getLostAuctions() %></span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
            </dl>
        </div>
        <div class="recently-viewed-container">
            <h3 id="lastSeenHeadlineDetails">Zuletzt angesehen</h3>
            <ul id="lastSeenListDetails">
            </ul>
        </div>
    </aside>
    <main aria-labelledby="productheadline" class="details-container">
        <div class="details-image-container">
            <img class="details-image" src="../images/<%= product.getImg()%>" alt="">
        </div>
        <div data-product-id="<%= product.getId()%>" class="details-data">
            <h2 class="main-headline" id="productheadline"><%= product.getName()%> (<%= product.getCategory()%>)</h2>
            <% if (!product.getExpiredTime().isAfter(LocalDateTime.now())) { %>
                <div class="auction-expired-text">
                    <p>
                        Diese Auktion ist bereits abgelaufen.
                        Das Produkt wurde um
                        <span class="highest-bid"><%=product.getPrice() %> </span> &#8364 an
                        <span class="highest-bidder">
                            <%=product.getMaxBidUser() %>
                        </span> verkauft.
                    </p>
                </div>
            <% }else { %>


                <p class="detail-time">Restzeit: <span data-end-time="<%=product.getFormattedEndtime()%>"
                                                       class="detail-rest-time js-time-left"></span>
                </p>
                <form id="bid-form" class="bid-form" action="">
                    <label class="bid-form-field" id="highest-price">
                        <span class="highest-bid"><%=product.getPrice() %> </span> &#8364
                        <span class="highest-bidder"><%=product.getMaxBidUser() %></span>
                    </label>
                    <label class="accessibility" for="new-price"></label>
                    <input type="number" step="0.01" min="0" id="new-price" class="bid-form-field form-input"
                           name="new-price" required>
                    <p class="bid-error">Es gibt bereits ein h&oumlheres Gebot oder der Kontostand ist zu niedrig.</p>
                    <input type="submit" id="submit-price" class="bid-form-field button" name="submit-price" value="Bieten">
                </form>
            <% }%>

        </div>
    </main>
</div>
<footer>
    © 2016 BIG Bid
</footer>



<script type="text/javascript">
    $("#bid-form").submit(function (event) {
        event.preventDefault();
        var newPrice = $("#new-price").val();
        var pid = '${product.id}';
        $.post("BidServlet", {newP: newPrice, prodId: pid})

                .done(function (data) {
                    var json = $.parseJSON(data);
                    runningAuctions = $(".running-auctions-count");
                    for (var i = 0; i < runningAuctions.length; i++) {
                        runningAuctions[i].innerHTML = json.runningAuctions;
                    }
                    balance = $(".balance");
                    for (var i = 0; i < runningAuctions.length; i++) {
                        balance[i].innerHTML = json.newBudget;
                    }
                    highestbids = $(".highest-bid");
                    for (var i = 0; i < highestbids.length; i++) {
                        highestbids[i].innerHTML = json.newProductPrice;
                    }
                    highestbidder = $(".highest-bidder");
                    for (var i = 0; i < highestbidder.length; i++) {
                        highestbidder[i].innerHTML = '${user.email}';
                    }
                    error = $(".bid-error");
                    for(var i = 0; i < error.length; i++){
                        error[i].style.display = "none";
                    }
                })

                .fail(function () {
                    error = $(".bid-error");
                    for(var i = 0; i < error.length; i++){
                        error[i].style.display = "block";
                    }
                })
    });
</script>
</body>
</html>