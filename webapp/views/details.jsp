<%@ page import="at.ac.tuwien.big.we16.ue2.Product" %>
<%@ page import="java.time.LocalDateTime" %>
<jsp:useBean id="user" class="at.ac.tuwien.big.we16.ue2.User" scope="session"/>
<jsp:useBean id="product" class="at.ac.tuwien.big.we16.ue2.Product" scope="session"/>
<!doctype html>
<html lang="de">
<head>
    <%
    %>
    <meta charset="utf-8">
    <title>BIG Bid - <%= product.getName() %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../styles/style.css">
</head>
<body data-decimal-separator="," data-grouping-separator=".">

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
                <dd class="user-name">John Doe</dd>
                <dt>Kontostand:</dt>
                <dd>
                    <span class="balance">1.500,00 &#8364</span>
                </dd>
                <dt>Laufend:</dt>
                <dd>
                    <span class="running-auctions-count">0</span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
                <dt>Gewonnen:</dt>
                <dd>
                    <span class="won-auctions-count">0</span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
                <dt>Verloren:</dt>
                <dd>
                    <span class="lost-auctions-count">0</span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
            </dl>
        </div>
        <div class="recently-viewed-container">
            <h3 class="recently-viewed-headline">Zuletzt angesehen</h3>
            <ul class="recently-viewed-list"></ul>
        </div>
    </aside>
    <main aria-labelledby="productheadline" class="details-container">
        <div class="details-image-container">
            <img class="details-image" src="../images/<%= product.getImg()%>" alt=""> <!--geandert-->
        </div>
        <div data-product-id="<%= product.getId()%>" class="details-data"> <!--geandert-->
            <h2 class="main-headline" id="productheadline"><%= product.getName()%> (<%= product.getCategory()%>)</h2>
            <% if (!product.getExpiredTime().isAfter(LocalDateTime.now())) { %>
                <div class="auction-expired-text">
                    <p>
                        Diese Auktion ist bereits abgelaufen.
                        Das Produkt wurde um
                        <span class="highest-bid"><%=product.getPrice() %> &#8364</span> an
                        <span class="highest-bidder"><%=product.getMaxBidUser() %></span> verkauft.
                    </p>
                </div>
            <% }else { %>


                <p class="detail-time">Restzeit: <span data-end-time="<%=product.getFormattedEndtime()%>"
                                                       class="detail-rest-time js-time-left"></span>
                </p>
                <form class="bid-form" method="post" action="">
                    <label class="bid-form-field" id="highest-price">
                        <span class="highest-bid"><%=product.getPrice() %> &#8364</span>
                        <span class="highest-bidder"><%=product.getMaxBidUser() %></span>
                    </label>
                    <label class="accessibility" for="new-price"></label>
                    <input type="number" step="0.01" min="0" id="new-price" class="bid-form-field form-input"
                           name="new-price" required>
                    <p class="bid-error">Es gibt bereits ein höheres Gebot oder der Kontostand ist zu niedrig.</p>
                    <input type="submit" id="submit-price" class="bid-form-field button" name="submit-price" value="Bieten">
                </form>
            <% }%>
        </div>
    </main>
</div>
<footer>
    © 2016 BIG Bid
</footer>
<script src="/scripts/jquery.js"></script>
<script src="/scripts/framework.js"></script>
</body>
</html>