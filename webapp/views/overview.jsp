<%@ page import="at.ac.tuwien.big.we16.ue2.Product, at.ac.tuwien.big.we16.ue2.ProductPool, java.time.LocalDateTime" %>
<%@ page contentType="text/html" %>

<jsp:useBean id="user" class="at.ac.tuwien.big.we16.ue2.User" scope="session"/>

<!doctype html>
<% System.out.println(ProductPool.getInstance().getProducts()); %>
<html lang="de">
<head>

    <meta charset="utf-8">
    <meta name="cache-control" content="no-cache" />
    <meta name="expires" content="0" />
    <meta name="pragma" content="no-cache" />

    <title>BIG Bid - Produkte</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../styles/style.css">

</head>

<input type="hidden" id="refreshed" value="no">
<script>
    onload=function(){
        var e=document.getElementById("refreshed");
        if(e.value=="no")e.value="yes";
        else{e.value="no";location.reload();}
    }

</script>
<body data-decimal-separator="," data-grouping-separator="." onload="printProductOfStorageOverview()">

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
                    <span id="overviewBudget" class="balance"><%= user.getBudget() %></span> &#8364
                </dd>
                <dt>Laufend:</dt>
                <dd>
                    <span class="running-auctions-count"><%= user.getRunningAuctions() %></span>
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
            <h3 id="lastSeenHeadlineOverview">Zuletzt angesehen</h3>
            <ul id="lastSeenListOverview">
            </ul>

        </div>
    </aside>

    <!-- The following part shows products dynamically, it iterates through all products and creates a field for them -->
    <main aria-labelledby="productsheadline">
        <h2 class="main-headline" id="productsheadline">Produkte</h2>
        <div class="products">
            <% for(Product p : ProductPool.getInstance().getProducts()) { %>

                <div class="product-outer" data-product-id=<%= p.getId() %>>
                    <form id="form" class="form" action="DetailServlet?product=<% p.getId(); %>" method="get">
                        <input type="hidden" name="product" value=<%= p.getId()%>/>

                        <a href="DetailServlet?product=<%= p.getId()%>"

                            <% if (!p.getExpiredTime().isAfter(LocalDateTime.now())) { %>
                                class="product expired"
                            <% } else if(p.getMaxBidUser().equals(user.getEmail())){ %>
                                class="product highlight"
                            <% } %>

                            title="Mehr Informationen zu <%= p.getName() %>">

                            <img class="product-image" src="../images/<%= p.getImg() %>" alt="<%= p.getName() %>">
                            <dl class="product-properties properties">
                                <dt>Bezeichnung</dt>
                                    <dd class="product-name"><%= p.getName() %></dd>
                                <dt>Preis</dt>
                                    <!--<% //if(p.getPrice() != 0){ %> -->
                                        <dd id="priceOver<%= p.getId() %>" class="product-price">
                                            <%= p.getPrice() %> &#8364
                                        </dd>
                                    <%// } else { %>
                                        <!--<dd style="text-align: center; flex-basis: 100%;">
                                            Noch keine Gebote
                                        </dd>-->
                                    <%// } %>
                                <dt>Verbleibende Zeit</dt>
                                <dd data-end-time="<%= p.getFormattedEndtime() %>"
                                        <% if (!p.getExpiredTime().isAfter(LocalDateTime.now())) { %>
                                            data-end-text="abgelaufen"
                                        <% } else { %>
                                            data-end-text=""
                                        <% } %>
                                    class="product-time js-time-left"></dd>
                                <dt>Höchstbietende /r</dt>
                                <dd id="maxbidderOver<%= p.getId() %>" class="product-highest"><%= p.getMaxBidUser() %></dd>
                            </dl>
                        </a>
                    </form>
                </div>
            <% } %>
        </div>
    </main>
</div>
<footer>
    © 2016 BIG Bid
</footer>

<script src="/scripts/jquery.js"></script>
<script src="/scripts/framework.js"></script>
<script src="/scripts/WAScript.js"></script>
<script>
    $(document).ready(function(){
        if(!supportsLocalStorage() || sessionStorage.length == 0){
            console.log("hidden");
            document.getElementById("lastSeenHeadlineOverview").className = "recently-viewed-headline";
        } else{
            console.log("show");
            document.getElementById("lastSeenHeadlineOverview").className = "";
        }
    })
</script>

</body>
</html>