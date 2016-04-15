<%@ page import="java.util.*" %>
<%@ page import="at.ac.tuwien.big.we16.ue2.Product" %>
<%@ page import="at.ac.tuwien.big.we16.ue2.ProductPool" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html" %>
<jsp:useBean id="user" class="at.ac.tuwien.big.we16.ue2.User" scope="session"/>
<% ProductPool pool = new ProductPool(); %>
!doctype html>
<html lang="de">
<head>
    <meta charset="utf-8">
    <title>BIG Bid - Produkte</title>
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
                <dd class="user-name"><%= user.getUsername() %></dd>
                <dt>Kontostand:</dt>
                <dd>
                    <span class="balance"><%= user.getBudget() %></span>
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
            <h3 class="recently-viewed-headline">Zuletzt angesehen</h3>
            <ul class="recently-viewed-list">
                <% for (Product p : user.getLastSeen()) { %>
                <li><%= p.getName() %></li>
                <% } %>
            </ul>
        </div>
    </aside>

    <!-- The following part shows products dynamically, it iterates through all products and creates a field for them -->

    <main aria-labelledby="productsheadline">
        <h2 class="main-headline" id="productsheadline">Produkte</h2>
        <div class="products">
            <% for(Product p : pool.getProducts()) { %>
        <div class="product-outer" data-product-id=<%= p.getId() %>>
                <a href="" <% if (!p.getExpiredTime().isAfter(LocalDateTime.now())) { %>
                   class="product expired "
                   <% } %>
                   title="Mehr Informationen zu <%= p.getName() %>">
                    <img class="product-image" src="../images/<%= p.getImg() %>" alt="<%= p.getName() %>">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name"><%= p.getName() %></dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            <%= p.getPrice() %>
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="<%= p.getFormattedEndtime() %>"
                                <% if (!p.getExpiredTime().isAfter(LocalDateTime.now())) { %>
                            data-end-text="abgelaufen"
                            <% } else { %>
                                data-end-text=""
                            <% } %>
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest"><%= p.getMaxBidUser() %></dd>
                    </dl>
                </a>
            </div>
            <% } %>
            <!--
            <div class="product-outer" data-product-id="fdf093a5-6ee2-48a0-b10f-21ec2735abe0">
                <a href="" class="product expired "
                   title="Mehr Informationen zu The Martian">
                    <img class="product-image" src="../images/the_martian.png" alt="">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name">The_Martian</dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            340,72 €
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="2016,03,14,14,30,56,292" data-end-text="abgelaufen"
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest">Jane Doe</dd>
                    </dl>
                </a>
            </div>
            <div class="product-outer" data-product-id="714fbe93-b14d-4fbd-83d7-97a78b2549b8">
                <a href="" class="product expired "
                   title="Mehr Informationen zu The Godfather">
                    <img class="product-image" src="../images/the_godfather.png" alt="">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name">Der Pate (Film)</dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            118,93 €
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="2016,03,14,14,31,23,291" data-end-text="abgelaufen"
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest">Jane Doe</dd>
                    </dl>
                </a>
            </div>
            <div class="product-outer" data-product-id="b4597af2-6fdd-4dac-a94d-a6b2a634512a">
                <a href="" class="product expired "
                   title="Mehr Informationen zu Pride and Prejudice">
                    <img class="product-image" src="../images/pride_and_prejudice.png" alt="">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name">Stolz und Vorurteil</dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            154,63 €
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="2016,03,14,14,31,35,295" data-end-text="abgelaufen"
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest">Jane Doe</dd>
                    </dl>
                </a>
            </div>
            <div class="product-outer" data-product-id="ab85b749-fb7a-4e03-9483-11e42ef091cf">
                <a href="" class="product expired "
                   title="Mehr Informationen zu Californication">
                    <img class="product-image" src="../images/red_hot_chili_peppers.png" alt="">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name">Californication</dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            119,30 €
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="2016,03,14,14,31,37,265" data-end-text="abgelaufen"
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest">Jane Doe</dd>
                    </dl>
                </a>
            </div>
            <div class="product-outer" data-product-id="a5d68110-562b-437f-992a-8b6735f9d251">
                <a href="" class="product expired "
                   title="Mehr Informationen zu The Wizard of Oz">
                    <img class="product-image" src="../images/the_wizard_of_oz.png" alt="">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name">Wizard of Oz</dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            108,83 €
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="2016,03,14,14,32,22,294" data-end-text="abgelaufen"
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest">Jane Doe</dd>
                    </dl>
                </a>
            </div>
            <div class="product-outer" data-product-id="2797123c-4d71-4dc2-9658-9c24a294f424">
                <a href="" class="product expired "
                   title="Mehr Informationen zu The Great Gatsby">
                    <img class="product-image" src="../images/the_great_gatsby.png" alt="">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name">Der große Gatsby</dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            241,63 €
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="2016,03,14,14,34,12,297" data-end-text="abgelaufen"
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest">Jane Doe</dd>
                    </dl>
                </a>
            </div>
            <div class="product-outer" data-product-id="ef4c3945-7e34-4676-bb61-94ee7866fd84">
                <a href="" class="product expired "
                   title="Mehr Informationen zu Reload">
                    <img class="product-image" src="../images/metallica.png" alt="">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name">Reload</dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            83,46 €
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="2016,03,14,14,34,26,289" data-end-text="abgelaufen"
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest">Jane Doe</dd>
                    </dl>
                </a>
            </div>
            <div class="product-outer" data-product-id="8505b7f2-7271-4d97-8343-971b6b912016">
                <a href="" class="product expired "
                   title="Mehr Informationen zu 1984">
                    <img class="product-image" src="../images/1984.png" alt="">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name">1984</dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            202,91 €
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="2016,03,14,14,34,46,296" data-end-text="abgelaufen"
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest">Jane Doe</dd>
                    </dl>
                </a>
            </div> -->

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